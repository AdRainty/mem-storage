package com.adrainty.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 23:41
 */

@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    FOLDER(0, "文件夹"),
    DOCUMENT(1, "文档"),
    VIDEO(2, "视频"),
    MUSIC(3, "音频"),
    ZIPPED(4, "压缩包"),
    PICTURE(5, "图片"),
    OTHER(9, "其他");

    private final Integer code;

    private final String comment;

    public static String getCommentByCode(Integer code) {
        for (FileTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getComment();
            }
        }
        return null;
    }
    
}
