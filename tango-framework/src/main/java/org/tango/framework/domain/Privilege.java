package org.tango.framework.domain;

import org.apache.struts2.json.annotations.JSON;
import org.tango.core.abs.AbstractModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: tango
 * Date: 13-11-23
 * Time: 下午9:42
 */
@Table(name = "SYS_PRIVILEGE")
@Entity
public class Privilege extends AbstractModel {

    //权限菜单
    private Set<PrivilegeMenuMap> menus = new HashSet<PrivilegeMenuMap>(0);
    //角色
    private Set<RolePrivilegeMap> roles = new HashSet<RolePrivilegeMap>(0);


    //权限名称
    private String name;
    //上级权限
    private Integer parent;
    //是否去除
    private Integer isRemove;
    //排序编号
    private Integer orderNum;
    //权限备注
    private String remark;
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

    @Id
    @GeneratedValue
    @Column(name = "P_ID", unique = true, nullable = false)
    public Integer getId() {
        return super.getId();
    }

    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取父权限
     *
     * @return 父权限
     */
    @Column(name = "PARENT", length = 255)
    public Integer getParent() {
        return parent;
    }

    /**
     * 设置父权限
     *
     * @param parent 父权限
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    /**
     * 获取排序编号
     */
    @Column(name = "ORDER_NUM", nullable = false)
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序编号
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取状态
     *
     * @return 获取状态
     */
    @Column(name = "STATE", nullable = false)
    public Integer getStated() {
        return stated;
    }

    /**
     * 设置状态(1:有效,0:无效)
     *
     * @param stated 设置状态
     */
    public void setStated(Integer stated) {
        this.stated = stated;
    }

    /**
     * 获取是否删除
     *
     * @return 是否删除权限
     */
    @Column(name = "IS_REMOVE", nullable = false)
    public Integer getRemove() {
        return isRemove;
    }

    /**
     * 设置是否删除
     *
     * @param remove 是否删除
     */
    public void setRemove(Integer remove) {
        isRemove = remove;
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

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "privilege")
    public Set<PrivilegeMenuMap> getMenus() {
        return menus;
    }

    public void setMenus(Set<PrivilegeMenuMap> menus) {
        this.menus = menus;
    }

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "privilege")
    public Set<RolePrivilegeMap> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolePrivilegeMap> roles) {
        this.roles = roles;
    }
}
