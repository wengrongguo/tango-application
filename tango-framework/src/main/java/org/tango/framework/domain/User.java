package org.tango.framework.domain;

import org.apache.struts2.json.annotations.JSON;
import org.tango.core.constant.Constant;
import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * User: tAngo
 * Date: 13-1-13
 * Time: 上午10:30
 */
@Entity
@Table(name = "SYS_USER")
public class User extends AbstractHibernateModel {

    //用户角色
    private Set<UserRoleMap> roles = new HashSet<UserRoleMap>(0);
    //姓名
    private String name;
    //帐号
    private String account;
    //密码
    private String password;
    //性别
    private Integer gender;
    //手机号
    private String phone;
    //QQ
    private String qq;
    //邮箱
    private String email;
    //生日
    private Date hireDate;
    //登陆次数
    private Integer loginCount;
    //最后登陆时间
    private Date lastTime;
    //状态
    private Integer stated;
    //扩展字段(整形)1
    private Integer extInteger1;
    //扩展字段(整形)2
    private Integer extInteger2;
    //扩展字段(整形)3
    private Integer extInteger3;
    //扩展字段(字符串)1
    private String extString1;
    //扩展字段(字符串)2
    private String extString2;
    //扩展字段(字符串)3
    private String extString3;
    //扩展字段(字符串)4
    private String extString4;
    //扩展字段(字符串)5
    private String extString5;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    @Override
    public Integer getId() {
        return super.getId();
    }

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<UserRoleMap> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleMap> roles) {
        this.roles = roles;
    }


    @Column(name = "ACCOUNT", unique = true, nullable = false, length = 16)
    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "EMAIL", nullable = true, length = 100)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "HIRE_DATE")
    @JSON(format = Constant.DEFAULT_DATE_FORMAT)
    public Date getHireDate() {
        return this.hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Column(name = "LAST_TIME", length = 0)
    @JSON(format = Constant.DEFAULT_TIME_FORMAT)
    public Date getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PASSWORD", nullable = false, length = 32)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "GENDER")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Column(name = "PHONE", nullable = true, length = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "QQ", nullable = true, length = 32)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(name = "LOGIN_COUNT", nullable = true)
    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Column(name = "STATED", nullable = false)
    public Integer getStated() {
        return stated;
    }

    public void setStated(Integer stated) {
        this.stated = stated;
    }


    /**
     * 获取扩展字段(整形)1
     *
     * @return 扩展字段(整形)1
     */
    @Column(name = "EXT_INTEGER1")
    public Integer getExtInteger1() {
        return extInteger1;
    }

    /**
     * 设置扩展字段(整形)1
     *
     * @param extInteger1 扩展字段(整形)1
     */
    public void setExtInteger1(Integer extInteger1) {
        this.extInteger1 = extInteger1;
    }

    /**
     * 获取扩展字段(整形)2
     *
     * @return 扩展字段(整形)2
     */
    @Column(name = "EXT_INTEGER2")
    public Integer getExtInteger2() {
        return extInteger2;
    }

    /**
     * 设置扩展字段(整形)2
     *
     * @param extInteger2 扩展字段(整形)2
     */
    public void setExtInteger2(Integer extInteger2) {
        this.extInteger2 = extInteger2;
    }

    /**
     * 获取扩展字段(整形)3
     *
     * @return 扩展字段(整形)3
     */
    @Column(name = "EXT_INTEGER3")
    public Integer getExtInteger3() {
        return extInteger3;
    }

    /**
     * 设置扩展字段(整形)3
     *
     * @param extInteger3 扩展字段(整形)3
     */
    public void setExtInteger3(Integer extInteger3) {
        this.extInteger3 = extInteger3;
    }

    /**
     * 获取扩展字段(字符串)1
     *
     * @return 扩展字段(字符串)1
     */
    @Column(name = "EXT_STRING1")
    public String getExtString1() {
        return extString1;
    }

    /**
     * 设置扩展字段(字符串)1
     *
     * @param extString1 扩展字段(字符串)1
     */
    public void setExtString1(String extString1) {
        this.extString1 = extString1;
    }

    /**
     * 获取扩展字段(字符串)2
     *
     * @return 扩展字段(字符串)2
     */
    @Column(name = "EXT_STRING2")
    public String getExtString2() {
        return extString2;
    }

    /**
     * 设置扩展字段(字符串)2
     *
     * @param extString2 扩展字段(字符串)2
     */
    public void setExtString2(String extString2) {
        this.extString2 = extString2;
    }

    /**
     * 获取扩展字段(字符串)1
     *
     * @return 扩展字段(字符串)1
     */
    @Column(name = "EXT_STRING3")
    public String getExtString3() {
        return extString3;
    }

    /**
     * 设置扩展字段(字符串)3
     *
     * @param extString3 扩展字段(字符串)3
     */
    public void setExtString3(String extString3) {
        this.extString3 = extString3;
    }

    /**
     * 获取扩展字段(字符串)1
     *
     * @return 扩展字段(字符串)1
     */
    @Column(name = "EXT_STRING4")
    public String getExtString4() {
        return extString4;
    }

    /**
     * 设置扩展字段(字符串)4
     *
     * @param extString4 扩展字段(字符串)4
     */
    public void setExtString4(String extString4) {
        this.extString4 = extString4;
    }

    /**
     * 获取扩展字段(字符串)1
     *
     * @return 扩展字段(字符串)1
     */
    @Column(name = "EXT_STRING5")
    public String getExtString5() {
        return extString5;
    }

    /**
     * 设置扩展字段(字符串)5
     *
     * @param extString5 扩展字段(字符串)5
     */
    public void setExtString5(String extString5) {
        this.extString5 = extString5;
    }
}