package com.adrainty.module.file;

import lombok.Data;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 23:28
 */

@Data
public class MemFileVo {

    private String fileName;

    private byte[] stream;

}
