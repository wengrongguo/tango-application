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
@Table(name = "SYS_USER_ROLE_MAP")
public class UserRoleMap extends AbstractHibernateModel {

    private Role role = new Role();

    private User user = new User();

    public UserRoleMap() {
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
    @JoinColumn(name = "U_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}