/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.framework.service.impl;

import org.springframework.stereotype.Service;
import org.tango.core.bean.QueryMapping;
import org.tango.core.constant.Constant;
import org.tango.framework.domain.Menu;
import org.tango.framework.service.MenuService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 菜单业务实现类
 * User: tAngo
 * Date: 12-11-11
 * Time: 下午4:47
 */
@Service
public class MenuServiceImpl extends FrameworkServiceSupport<Menu> implements MenuService {

    @Override
    public boolean save(Menu menu) {
        menu.setCreateDate(new Date());
        menu.setDeleteSign(Constant.DELETE_SIGN_FALSE);
        return super.save(menu);
    }

    @Override
    public boolean updateNotNull(Menu menu) {
        menu.setUpdateDate(new Date());
        return super.updateNotNull(menu);
    }

    @Override
    public boolean delete(Integer id) {
        List<Menu> children = super.getList(new QueryMapping().addEquals("parentMenu", id));
        List<Integer> childrenIds = this.getIdList(children);
        boolean deleteFlag = true;
        for (Iterator<Integer> iterator = childrenIds.iterator(); iterator.hasNext(); ) {
            Integer childrenId = iterator.next();
            deleteFlag = deleteFlag && this.delete(childrenId);
        }
        return super.delete(id) && deleteFlag;
    }
}
