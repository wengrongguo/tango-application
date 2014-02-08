package org.tango.framework.domain;

import org.tango.hibernate.abs.AbstractHibernateModel;

import javax.persistence.*;

/**
 * User: tango
 * Date: 13-12-6
 * Time: 下午11:38
 */
@Entity
@Table(name = "SYS_PRIVILEGE_MENU_MAP")
public class PrivilegeMenuMap extends AbstractHibernateModel {

    private Privilege privilege = new Privilege();

    private Menu menu = new Menu();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_ID", nullable = false)
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
