package org.tango.framework.domain;// default package

import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;

/**
 * 用户角色映射
 * User: tAngo
 * Date: 13-1-13
 * Time: 上午10:30
 */
@Entity
@Table(name = "SYS_ROLE_PRIVILEGE_MAP")
public class RolePrivilegeMap extends AbstractHibernateModel {

    private Role role = new Role();

    private Privilege privilege = new Privilege();

    public RolePrivilegeMap() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "R_ID", nullable = false)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_ID", nullable = false)
    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
}