/*
 * <!--
 * Created by CodeBuilder.
 * @Author: tAngo,
 * @QQ:278826466
 * @Email:org.java.tango@gmail.com,ym_tango_m1crosoft@live.com
 * -->
 */

package org.tango.framework.domain;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.tango.core.constant.Constant;
import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单
 * User: tAngo
 * Date: 13-1-13
 * Time: 上午10:30
 */
@Table(name = "SYS_MENU")
@Entity
public class Menu extends AbstractHibernateModel {

    //用户列表
    private Set<PrivilegeMenuMap> privileges = new HashSet<PrivilegeMenuMap>(0);

    public Menu() {
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

    @JSON(deserialize = false, serialize = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    public Set<PrivilegeMenuMap> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<PrivilegeMenuMap> privileges) {
        this.privileges = privileges;
    }

    //名称
    private String name;
    //菜单地址
    private String link;
    //菜单图标
    private String icon;
    //是否系统菜单
    private Integer system;
    //上级菜单
    private Integer parentMenu;
    //菜单类型
    private Integer type;
    //排序编号
    private Integer orderNum;
    //描述
    private String description;
    //创建日期
    private Date createDate;
    //修改日期
    private Date updateDate;
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
    //状态
    private Integer stated;
    //删除标记
    private Integer deleteSign;

    //权限列表

    /**
     * 获取名称
     */
    @Column(name = "NAME", length = 255)
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单地址
     */
    @Column(name = "LINK", length = 255)
    public String getLink() {
        return link;
    }

    /**
     * 设置菜单地址
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 获取菜单图标
     */
    @Column(name = "ICON", length = 255)
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * 获取父菜单
     *
     * @return 父菜单
     */

    @Column(name = "PARENT_MENU")
    public Integer getParentMenu() {
        return parentMenu;
    }

    /**
     * 设置父菜单
     *
     * @param parentMenu 父菜单
     */
    public void setParentMenu(Integer parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * 获取是否系统菜单
     *
     * @return 是否系统菜单
     */
    @Column(name = "IS_SYSTEM")
    public Integer getSystem() {
        return system;
    }

    /**
     * 设置是否系统菜单
     *
     * @param system 设置是否系统菜单
     */
    public void setSystem(Integer system) {
        this.system = system;
    }

    /**
     * 获取菜单类型
     *
     * @return 菜单类型
     */
    @Column(name = "MENU_TYPE", nullable = false)
    public Integer getType() {
        return type;
    }

    /**
     * 设置菜单类型
     *
     * @param type 菜单类型
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取删除标记
     *
     * @return 删除标记
     */
    @Column(name = "DELETE_SIGN", nullable = false)
    public Integer getDeleteSign() {
        return deleteSign;
    }

    /**
     * 设置删除标记(1:删除,0:未删除)
     *
     * @param deleteSign 删除标记
     */
    public void setDeleteSign(Integer deleteSign) {
        this.deleteSign = deleteSign;
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Column(name = "REMARK")
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    @Column(name = "CREATE_DATE")
    @JSON(format = Constant.DEFAULT_TIME_FORMAT)
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    @Column(name = "UPDATE_DATE")
    @JSON(format = Constant.DEFAULT_TIME_FORMAT)
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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