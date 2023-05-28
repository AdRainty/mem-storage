package com.adraitny.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 17:22
 */
public class StringTest {

    @Test
    public void getString() {
        System.out.println(RandomStringUtils.randomAlphanumeric(30));
        assert true;
    }

}
