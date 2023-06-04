package com.adrainty.file.controller;

import com.adrainty.common.constants.BizCodeEnum;
import com.adrainty.common.response.R;
import com.adrainty.common.utils.JwtUtils;
import com.adrainty.file.service.IMemFileService;
import com.adrainty.module.file.MemFile;
import com.adrainty.module.file.MemFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 22:49
 */

@RestController
@RequestMapping("/file")
@Api(tags = "文件模块")
public class MemFileController {

    private final IMemFileService iMemFileService;

    @Autowired
    public MemFileController(IMemFileService iMemFileService) {
        this.iMemFileService = iMemFileService;
    }

    @ApiOperation(value = "列出文件列表")
    @GetMapping("/list")
    public R list(@RequestHeader("token") String token, @RequestParam("path") String path
            ,@RequestParam("name") String name, @RequestParam("updateTime") String updateTime
            ,@RequestParam("size") String size) {

        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        List<MemFile> memFiles = iMemFileService.listFiles(userId, path, name, updateTime, size);
        return R.ok().put("list", memFiles);
    }

    @ApiOperation(value = "根据关键词查找文件")
    @GetMapping("/keyword")
    public R keyword(@RequestHeader("token") String token, @RequestParam("keyword") String keyword, @RequestParam("path") String path) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        List<MemFile> memFiles;
        if (!StringUtils.hasLength(keyword)) memFiles = iMemFileService.listFiles(userId, path, null, null, null);
        else memFiles = iMemFileService.searchKeyWord(userId, keyword);
        return R.ok().put("list", memFiles);
    }

    @ApiOperation(value = "上传")
    @PostMapping("/upload")
    public R upload(@RequestHeader("token") String token, @RequestParam(value = "files") MultipartFile file, @RequestParam("path") String path) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        boolean result = iMemFileService.uploadFile(userId, path, file);
        return result? R.ok(): R.error();
    }

    @ApiOperation(value = "下载")
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("fileId") Long fileId) {
        MemFileVo memFile = iMemFileService.downloadFile(fileId);
        ByteArrayInputStream stream = new ByteArrayInputStream(memFile.getStream());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", memFile.getFileName()));
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(stream));
    }

    @ApiOperation(value = "新建文件夹")
    @GetMapping("/newFolder")
    public R newFolder(@RequestHeader("token") String token, @RequestParam("folderName") String folderName,
                       @RequestParam("path") String path) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        iMemFileService.createNewFolder(userId, folderName, path);
        return R.ok();
    }

    @ApiOperation(value = "删除文件")
    @GetMapping("/delete")
    public R delete(@RequestHeader("token") String token, @RequestParam("fileId") Long fileId) {
        Long userId = JwtUtils.getUserId(token);
        if (userId < 0) return R.error(BizCodeEnum.UN_AUTHORITY);
        iMemFileService.deleteFile(userId, fileId);
        return R.ok();
    }

}
