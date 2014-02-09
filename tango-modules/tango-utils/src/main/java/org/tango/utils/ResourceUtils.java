package org.tango.utils;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 资源工具类
 *
 * @author tAngo
 */
public final class ResourceUtils {

    /**
     * 读取资源
     *
     * @param resource 资源名
     * @return inputStream 输入流
     */
    public static InputStream getResourceAsStream(String resource) {
        String resStr = resource.startsWith("/") ? resource.substring(1) : resource;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resStr);
        if (inputStream == null) {
            throw new RuntimeException("resource : " + resource + " is not found");
        }
        return inputStream;
    }
}
