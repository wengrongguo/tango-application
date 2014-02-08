package org.tango.framework.action.vo;

import org.springframework.beans.BeanUtils;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.Privilege;

import java.util.*;

/**
 * User: tango
 * Date: 13-11-24
 * Time: 下午10:51
 */
public class PrivilegeVo extends Privilege {

    private Integer _parentId;

    private String text;

    public static List<PrivilegeVo> parse(List<Privilege> list) {
        List<PrivilegeVo> privilegeVos = new ArrayList<PrivilegeVo>();
        for (Iterator<Privilege> iterator = list.iterator(); iterator.hasNext(); ) {
            privilegeVos.add(parse(iterator.next()));
        }
        return privilegeVos;
    }

    public static final PrivilegeVo parse(Privilege privilege) {
        PrivilegeVo privilegeVo = new PrivilegeVo();
        privilegeVo.text = privilege.getName();
        privilegeVo.set_parentId(privilege.getParent());
        BeanUtils.copyProperties(privilege, privilegeVo);
        return privilegeVo;
    }

    public Integer get_parentId() {
        return _parentId;
    }

    public void set_parentId(Integer _parentId) {
        this._parentId = _parentId;
    }


    public String getText() {
        return text;
    }

    public static List<Map<String, Object>> parseList(List<Privilege> list) {
        return getPrivilegeByParent(list, 0);
    }

    private static List<Map<String, Object>> getPrivilegeByParent(List<Privilege> list, Integer parent) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Iterator<Privilege> iterator = list.iterator(); iterator.hasNext(); ) {
            Privilege next = iterator.next();
            if (next.getParent() != null && next.getParent().intValue() == parent.intValue()) {
                HashMap<String, Object> stringObjectHashMap = new HashMap<String, Object>();
                stringObjectHashMap.put("id", next.getId());
                stringObjectHashMap.put("name", next.getName());
                stringObjectHashMap.put("text", next.getName());
                stringObjectHashMap.put("children", getPrivilegeByParent(list, next.getId()));
                mapList.add(stringObjectHashMap);
            }
        }
        return mapList;
    }
}

