package org.tango.utils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 文件工具类
 *
 * @author tango
 */
public final class FileUtils {

    /**
     * 根据完整文件名获取简单文件名
     *
     * @param fileName 完整文件名
     * @return simpleFileName 简单文件名
     */
    public static String getSimpleFileName(String fileName) {
        int beginIndex = fileName.lastIndexOf("\\");
        String simpleFileName = fileName.substring(beginIndex + 1);
        return simpleFileName;
    }

    /**
     * 读取文本
     *
     * @param fileName 文件绝对路径+文件名
     * @return 文本内容
     * @throws java.io.FileNotFoundException
     */
    public final static String getText(String fileName) throws IOException {
        return getText(new FileInputStream(fileName));
    }

    /**
     * 创建文件
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param cacheSize    缓存大小
     * @throws java.io.IOException
     */
    public final static void builderFile(InputStream inputStream, OutputStream outputStream, double cacheSize) throws IOException {
        byte[] cacheByte = new byte[(int) cacheSize];
        int length = -1;
        while ((length = inputStream.read(cacheByte)) != -1) {
            outputStream.write(cacheByte, 0, length);
        }
        outputStream.close();
        inputStream.close();
    }

    /**
     * 创建文件
     *
     * @param bytes      内容
     * @param dictionary 目录
     * @throws java.io.IOException
     */
    public final static void builderFile(byte[] bytes, File dictionary) throws IOException {
        OutputStream outputStream = null;
        try {
            if (!dictionary.exists())
                dictionary.mkdirs();
            if (!dictionary.isDirectory()) {
                throw new IOException("file must is dictionary!");
            }
            outputStream = new FileOutputStream(dictionary);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }

    /**
     * 创建文件
     *
     * @param bytes            内容
     * @param fileOutputStream 文件流
     * @throws java.io.IOException
     */
    public final static void builderFile(byte[] bytes, FileOutputStream fileOutputStream) throws IOException {
        try {
            fileOutputStream.write(bytes);
        } finally {
            if (fileOutputStream != null)
                fileOutputStream.close();
        }
    }

    /**
     * 读取文本字符流
     *
     * @param inputStream 字符输入流
     * @return 文本内容
     * @throws java.io.IOException
     */
    public final static String getText(InputStream inputStream) throws IOException {
        return getText(inputStream, Encode.CharacterSet.UTF8);
    }

    /**
     * 读取文本字符流
     *
     * @param inputStream 字符输入流
     * @param charset     字符编码 (Encode.CharacterSet.UTF8)
     * @return 文本内容
     * @throws java.io.IOException
     */
    public final static String getText(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream, charset.name()));
            String line = null;
            while ((line = read.readLine()) != null) {
                text.append(line);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return text.toString();
    }

    /**
     * 批量重命名,拷贝文件
     *
     * @param directoryPath    目录路径
     * @param fileName         文件名
     * @param fileTypeFilter   文件类型
     * @param copyDirctoryPath 文件拷贝路径
     */
    public static void fileBatReName(String directoryPath, String fileName, final String fileTypeFilter, String copyDirctoryPath) {
        try {
            File file = new File(directoryPath);
            File[] files = file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    if (file.isDirectory() || !file.isFile() || file.getName().indexOf(".") == -1)
                        return false;
                    return file.getName().indexOf(fileTypeFilter) > 1 ? true : false;
                }
            });
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                FileOutputStream outputStream = new FileOutputStream(copyDirctoryPath + fileName + (i + 1) + fileTypeFilter);
                FileInputStream fileInputStream = new FileInputStream(f);
                byte[] bytes = new byte[1024];
                while (fileInputStream.read(bytes) > 0) {
                    outputStream.write(bytes, 0, bytes.length);
                }
                outputStream.close();
                fileInputStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 　文件名
     * @return 文件扩展名
     */
    public final static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 是否绝对路径
     *
     * @param filePath
     * @return
     */
    public final static boolean isAbstractPath(String filePath) {
        return filePath.indexOf("\\") > 0;
    }

    /**
     * 生成新的上传文件名
     *
     * @param fileName 上传文件名
     * @return 新的上传文件名
     */
    public static String getFileUploadNameByTime(String filePath, String fileName) {
        return filePath + File.separator + System.currentTimeMillis() + getFileExt(getSimpleFileName(fileName));
    }

    /**
     * 生成目录
     *
     * @return
     */
    public static String getFileUploadName(String path, String fileName) {
        int dir1 = fileName.hashCode() & 0xf;
        int dir2 = ((fileName.hashCode() & 0xf) >>> 4) & 0xf;
        String savePath = path + "\\" + dir1 + "\\" + dir2;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    /**
     * 输出流
     *
     * @param fileContent 文本内容
     * @throws java.io.IOException
     */
    public static void builderFile(StringBuilder fileContent, OutputStream outputStream) throws IOException {
        try {
            outputStream.write(fileContent.toString().getBytes());
        } finally {
            outputStream.close();
        }
    }

    public static long sizeOf(File file) {
        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }
        return file.isDirectory() ? sizeOfDirectory(file) : file.length();
    }

    public static long sizeOfDirectory(File directory) {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        long size = 0;
        File[] files = directory.listFiles();
        if (files == null)  // null if security restricted
            return 0L;
        for (File file : files) {
            size += sizeOf(file);
        }
        return size;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && parent.exists() == false) {
                if (parent.mkdirs() == false) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }


    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
}