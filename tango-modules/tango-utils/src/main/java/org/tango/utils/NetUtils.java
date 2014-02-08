package org.tango.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * 网络工具类
 *
 * @author tAngo
 */
public final class NetUtils {

    /**
     * 根据Url获得输入流
     *
     * @param url 地址
     * @return 输入流
     */
    public final static InputStream getInputStream(String url) {
        InputStream inputStream = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return inputStream;
    }

    /**
     * 读取文本
     *
     * @param url
     * @return 文本内容
     */
    public final static String getText(String url) {
        String readText = StringUtils.EMPTY_STRING;
        try {
            readText = FileUtils.getText(NetUtils.getInputStream(url));
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return readText;
    }

    /**
     * 获取本机IP
     *
     * @return 本机IP
     * @throws UnknownHostException
     */
    public final String getLocalHost() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress().toString();
    }

    /**
     * 获取本机名
     *
     * @return 本机名
     * @throws UnknownHostException
     */
    public final String getLocalHostName() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostName();
    }
}
