package com.adrainty.module.form;

import lombok.Data;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 15:33
 */
@Data
public class FileShareForm {

    private Long sender;

    private Long receiver;

    private Long fileId;

}
