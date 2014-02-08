/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.core.constant;

/**
 * User: tAngo
 * Date: 12-11-11
 * Time: 下午4:36
 */
public class Constant {

    public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 制表符
     */
    public final static String TABLE = "\t";
    /**
     * 换行
     */
    public final static String LINE = "\r\n";

    /**
     * 单行注释
     */
    public static final String SINGLETON_COMMENT = "//";
    /**
     * 空
     */
    public final static String NULL = "null";
    /**
     * 点
     */
    public final static String DOT = ".";
    /**
     * 等号
     */
    public final static String EQUAL = "=";
    /**
     * 问号
     */
    public final static String QUESTION = "?";
    /**
     * 非等号
     */
    public final static String NOT_EQUAL = "!=";
    /**
     * 空格
     */
    public final static String BLANK = " ";


    public static final String IF = "if";

    public static final String APPEND = "append";

    public final static String PARAM = "param";

    public final static String THIS = "this";

    public static final String SERIALIZABLE = "Serializable";

    public final static String IMPORT = "import";

    public final static String IMPLEMENTS = "implements";

    public final static String CLASS = "class";

    public final static String INTERFACE = "interface";

    public final static String RETURN = "return";

    public static final String INT = "int";

    public final static String LIMIT = "LIMIT";

    public final static String LIMIT_L = "limit";

    public static final String PRIMARY_KEY = "PrimaryKey";

    public static final String MAPPER = "mapper";

    public final static String DELETE = "delete";

    public final static String UPDATE = "update";

    public final static String INSERT = "insert";

    public final static String DROP = "drop";

    public final static String SELECT = "select";

    public static final String SQL = "sql";

    public static final String PAGE = "page";

    public static final String ROWS = "rows";

    public static final String PAGER_L = "pager";

    public static final String PAGER = "Pager";

    public static final String PAGE_SIZE = "pageSize";

    public static final String PAGE_NO = "pageNo";

    public static final String PAGE_COUNT = "pageCount";

    public static final String ROW_COUNT = "rowCount";

    public static final String SINGLETON = "singleton";

    public static final String PROTOTYPE = "prototype";

    public static final String BY = "By";

    public static final String BY_L = "by";

    public static final String ID = "Id";

    public static final String ID_L = "id";

    public static final String BY_ID = BY + ID;

    public final static String ADD_U = "AND";

    public final static String AND = "and";

    public final static String ADD = "add";

    public final static String DEL = "del";

    public static final String XML = "xml";

    public static final String BEGIN = "begin";

    public static final String END = "end";

    public final static String RESULT = "result";

    public static final String LIST = "list";

    public static final String MESSAGE = "message";

    /**
     * 状态-无效
     */
    public static final Integer STATE_INVALID = 0;
    /**
     * 状态-有效
     */
    public static final Integer STATE_EFFECTIVE = 1;

    /**
     * 删除标记
     */
    public static final Integer DELETE_SIGN_TRUE = 1;

    /**
     * 未删除标记
     */
    public static final Integer DELETE_SIGN_FALSE = 0;

    /**
     * 登陆用户(用于存储Session)
     */
    public static final String KEY_USER = "users";
    /**
     * 角色列表(用于存储Session)
     */
    public static final String KEY_ROLE = "roles";

    /**
     * 权限列表(用于存储Session)
     */
    public static final String KEY_PRIVILEGE = "privileges";
    /**
     * 菜单列表(用于存储Session)
     */
    public static final String KEY_MENU = "menus";

    public static final String ERROR = "error";

    public static final String SUCCESS = "success";

    public static final String TEXT = "text";

    public static final String ATTRIBUTES = "attributes";

    public static final String CHILDREN = "children";

    public static final String EMPTY = "";
}
