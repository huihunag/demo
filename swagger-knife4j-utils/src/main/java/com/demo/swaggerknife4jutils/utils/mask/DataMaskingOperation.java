package com.demo.swaggerknife4jutils.utils.mask;

public interface DataMaskingOperation {
    String MASK_CHAR = "*";
    String mask(String content, String maskChar);
}
