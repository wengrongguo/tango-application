package org.tango.framework.action.vo;

import org.springframework.beans.BeanUtils;
import org.tango.framework.domain.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: tango
 * Date: 13-11-18
 * Time: 下午11:16
 */
public class MenuVo extends Menu {

    private String text;

    public static final MenuVo parse(Menu menu, int total) {
        MenuVo menuVo = new MenuVo();
        menuVo.total = total;
        menuVo.text = menu.getName();
        BeanUtils.copyProperties(menu, menuVo);
        return menuVo;
    }

    private String state = "open";

    private int total = 0;

    public String getState() {
        return total > 0 ? "closed" : "open";
    }

    public String getText() {
        return text;
    }
}
