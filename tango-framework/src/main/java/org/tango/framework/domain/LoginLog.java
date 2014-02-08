package org.tango.framework.domain;

import org.apache.struts2.json.annotations.JSON;
import org.tango.core.constant.Constant;
import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;
import java.util.Date;

/**
 * User: tango
 * Date: 13-12-7
 * Time: 下午6:56
 */
@Table(name = "SYS_LOGIN_LOG")
@Entity
public class LoginLog extends AbstractHibernateModel {

    public static enum LoginType {
        LOGIN(1), LOGOUT(2);

        private int type;

        private LoginType(int type) {
            this.type = type;
        }

        public int getValue() {
            return this.type;
        }
    }

    /**
     * 获取编号
     *
     * @return 编号
     */
    @Id
    @Column(name = "M_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Integer getId() {
        return (Integer) super.getId();
    }

    private String userId;

    private String account;

    private Date date;

    private String ip;

    private Integer type;

    private String stated;

    private String remark;

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    @Column(name = "USER_ID", length = 255)
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户帐号
     *
     * @return 用户帐号
     */
    @Column(name = "USER_ACCOUNT", length = 255)
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户帐号
     *
     * @param account 用户帐号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取操作时间
     *
     * @return 操作时间
     */
    @Column(name = "LOG_DATE")
    @JSON(format = Constant.DEFAULT_TIME_FORMAT)
    public Date getDate() {
        return date;
    }

    /**
     * 设置操作时间
     *
     * @param date 操作时间
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取操作IP
     *
     * @return 操作IP
     */
    @Column(name = "IP")
    public String getIp() {
        return ip;
    }

    /**
     * 设置操作IP
     *
     * @param ip 操作IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取操作类型
     *
     * @return 操作类型
     */
    @Column(name = "LOG_TYPE")
    public Integer getType() {
        return type;
    }

    /**
     * 设置操作类型
     *
     * @param type 操作类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取操作状态
     *
     * @return 操作状态
     */
    @Column(name = "STATED")
    public String getStated() {
        return stated;
    }

    /**
     * 设置操作状态
     *
     * @param stated 操作状态
     */
    public void setStated(String stated) {
        this.stated = stated;
    }

    /**
     * 获取备注
     *
     * @return 获取备注
     */
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
