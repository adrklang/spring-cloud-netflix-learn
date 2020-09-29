package com.lhstack.microservice.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lh
 * @date 2020/9/29
 * @description
 * @name microservice
 * @sine 11
 */
public class IOUtils {

    public static byte[] ioToBytes(InputStream in) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        in.transferTo(bo);
        return bo.toByteArray();
    }
}
