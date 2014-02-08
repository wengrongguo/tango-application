import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tango.core.constant.Constant;
import org.tango.framework.domain.Menu;
import org.tango.framework.service.MenuService;

/**
 * User: tAngo
 * Date: 12-12-22
 * Time: 下午7:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-framework.xml")
public class BaseTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void testInit() {
        Menu rootMenu = getMenu("系统管理", "/admin/system/index.action", "system", 0, 0, Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(rootMenu);
        Menu menuMenu = getMenu("菜单管理", "/admin/menu/index.action", "menu", 0, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(menuMenu);
        Menu userMenu = getMenu("用户管理", "/admin/user/index.action", "user", 10, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(userMenu);
        Menu roleMenu = getMenu("角色管理", "/admin/role/index.action", "role", 20, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(roleMenu);
        Menu privilegeMenu = getMenu("权限管理", "/admin/privilege/index.action", "privilege", 30, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(privilegeMenu);
        Menu operationMenu = getMenu("功能管理", "/admin/operation/index.action", "operation", 40, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(operationMenu);
        Menu dictionaryMenu = getMenu("字典管理", "/admin/dictionary/index.action", "operation", 50, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(dictionaryMenu);
        Menu dictionaryTypeMenu = getMenu("字典分类管理", "/admin/dictionary_type/index.action", "operation", 60, rootMenu.getId(), Constant.STATE_EFFECTIVE, Constant.DELETE_SIGN_FALSE);
        menuService.save(dictionaryTypeMenu);
    }

    private Menu getMenu(String name, String link, String icon, Integer orderNum, Integer parentMenu, Integer state, Integer deleteSign) {
        Menu rootMenu = new Menu();
        rootMenu.setName(name);
        rootMenu.setLink(link);
        rootMenu.setIcon(icon);
        rootMenu.setOrderNum(orderNum);
        rootMenu.setStated(state);
        rootMenu.setDeleteSign(deleteSign);
        return rootMenu;
    }
}
