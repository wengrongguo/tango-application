/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

/**
 * 系统工具类.
 * User: tAngo
 * Date: 12-12-10
 * Time: 下午10:53
 */
public class SystemUtils {
    /**
     * 获取Java 运行时环境版本
     *
     * @return System.getProperty("java.version")
     */
    public final static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * 获取Java 运行时环境供应商
     *
     * @return System.getProperty("java.vendor")
     */
    public final static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * 获取Java 供应商的 URL
     *
     * @return System.getProperty("java.vendor.url")
     */
    public final static String getJavaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    /**
     * 获取Java 安装目录
     *
     * @return System.getProperty("java.home")
     */
    public final static String getJavaHome() {
        return System.getProperty("java.home");
    }

    /**
     * 获取Java 虚拟机规范版本
     *
     * @return System.getProperty("java.vm.specification.version")
     */
    public final static String getJavaVmSpecificationVersion() {
        return System.getProperty("java.vm.specification.version");
    }

    /**
     * 获取Java 虚拟机规范供应商
     *
     * @return System.getProperty("java.vm.specification.vendor")
     */
    public final static String getJavaVmSpecificationVendor() {
        return System.getProperty("java.vm.specification.vendor");
    }

    /**
     * 获取Java 虚拟机规范名称
     *
     * @return System.getProperty("java.vm.specification.name")
     */
    public final static String getJavaVmSpecificationName() {
        return System.getProperty("java.vm.specification.name");
    }

    /**
     * 获取Java 虚拟机实现版本
     *
     * @return System.getProperty("java.vm.version")
     */
    public final static String getJavaVmVersion() {
        return System.getProperty("java.vm.version");
    }

    /**
     * 获取Java 虚拟机实现供应商
     *
     * @return System.getProperty("java.vm.vendor")
     */
    public final static String getJavaVmVendor() {
        return System.getProperty("java.vm.vendor");
    }

    /**
     * 获取Java 虚拟机实现名称
     *
     * @return System.getProperty("java.vm.name")
     */
    public final static String getJavaVmName() {
        return System.getProperty("java.vm.name");
    }

    /**
     * 获取Java 运行时环境规范版本
     *
     * @return System.getProperty("java.specification.version")
     */
    public final static String getJavaSpecificationVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * 获取Java 运行时环境规范供应商
     *
     * @return System.getProperty("java.specification.vendor")
     */
    public final static String getJavaSpecificationVendor() {
        return System.getProperty("java.specification.vendor");
    }

    /**
     * 获取Java 运行时环境规范名称
     *
     * @return System.getProperty("java.specification.vendor")
     */
    public final static String getJavaSpecificationName() {
        return System.getProperty("java.specification.vendor");
    }

    /**
     * 获取Java 类格式版本号
     *
     * @return System.getProperty("java.class.version")
     */
    public final static String getJavaClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * 获取Java 类路径
     *
     * @return System.getProperty("java.class.path")
     */
    public final static String getJavaClassPath() {
        return System.getProperty("java.class.path");
    }

    /**
     * 获取加载库时搜索的路径列表
     *
     * @return System.getProperty("java.library.path")
     */
    public final static String getJavaLibraryPath() {
        return System.getProperty("java.library.path");
    }

    /**
     * 获取默认的临时文件路径
     *
     * @return System.getProperty("java.io.tmpdir")
     */
    public final static String getJavaIoTempDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取要使用的 JIT 编译器的名称
     *
     * @return System.getProperty("java.compiler")
     */
    public final static String getJavaCompiler() {
        return System.getProperty("java.compiler");
    }

    /**
     * 获取一个或多个扩展目录的路径
     *
     * @return System.getProperty("java.ext.dirs")
     */
    public final static String getJavaExtDirs() {
        return System.getProperty("java.ext.dirs");
    }

    /**
     * 获取操作系统的名称
     *
     * @return System.getProperty("os.name")
     */
    public final static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * 获取操作系统的架构
     *
     * @return System.getProperty("os.arch")
     */
    public final static String getOsArch() {
        return System.getProperty("os.arch");
    }

}
