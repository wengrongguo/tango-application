package org.tango.framework.domain;

import org.apache.struts2.json.annotations.JSON;
import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: tango
 * Date: 13-11-23
 * Time: 下午6:13
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role extends AbstractHibernateModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Id
    @Override
    public Integer getId() {
        return super.getId();
    }

    //角色权限
    private Set<RolePrivilegeMap> privileges = new HashSet<RolePrivilegeMap>(0);
    //用户列表
    private Set<UserRoleMap> users = new HashSet<UserRoleMap>(0);

    //名称
    private String name;
    //描述
    private String remark;
    //排序编号
    private Integer orderNum;
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

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<RolePrivilegeMap> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<RolePrivilegeMap> privileges) {
        this.privileges = privileges;
    }

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<UserRoleMap> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRoleMap> users) {
        this.users = users;
    }

    /**
     * 获取名称
     */
    @Column(name = "NAME", length = 255)
    public String getName() {
        return name;
    }

    /**
     * 获取描述
     */
    @Column(name = "REMARK", length = 255)
    public String getRemark() {
        return remark;
    }

    /**
     * 获取排序编号
     */
    @Column(name = "ORDER_NUM")
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    @Column(name = "STATED", nullable = false)
    public Integer getStated() {
        return stated;
    }

    /**
     * 设置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置描述
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 设置排序编号
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 设置状态
     *
     * @param stated 状态
     */
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
}
