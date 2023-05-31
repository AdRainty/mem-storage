package com.adrainty.file.utils;

import com.adrainty.module.file.MemFile;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 22:08
 */

@Component
public class MinioUtils {

    @Value("${minio.bucketName}")
    private String bucketName;

    private final MinioClient minioClient;

    @Autowired
    public MinioUtils(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * 判断Bucket是否存在, 不存在创建Bucket
     * @param name Bucket名称
     */
    public void existBucket(String name) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除Bucket
     * @param bucketName Bucket名称
     * @return 是否删除成功
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     * @param file 文件
     * @return 上传后的文件名
     */
    public String upload(String path, MultipartFile file) {
        existBucket(bucketName);
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String[] split = fileName.split("\\.");
        if (split.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                sb.append(split[i]).append(".");
            }
            sb.setCharAt(sb.length() - 1, '_');
            sb.append(System.currentTimeMillis()).append(".").append(split[split.length - 1]);
            fileName = sb.toString();
        } else {
            fileName = fileName + "_" + System.currentTimeMillis();
        }
        InputStream in;
        try {
            in = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path + "/" + fileName)
                    .stream(in, in.available(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 下载文件
     * @param fileName 文件名
     * @return 文件流
     */
    public byte[] download(String fileName) {
        InputStream in;
        byte[] bytes = null;
        ByteArrayOutputStream out;
        try {
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            bytes = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 获取bucket文件列表
     * @return 文件列表
     */
    public List<MemFile> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
        List<MemFile> objectItems = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                Item item = result.get();
                MemFile memFile = new MemFile();
                memFile.setRealName(item.objectName());
                memFile.setSize(item.size());
                objectItems.add(memFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return objectItems;
    }

    /**
     * 批量删除文件对象
     *
     * @param objects    对象名称集合
     */
    public Iterable<Result<DeleteError>> removeObjects(List<String> objects) {
        List<DeleteObject> dos = objects.stream().map(DeleteObject::new).toList();
        return minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(dos).build());
    }
}
