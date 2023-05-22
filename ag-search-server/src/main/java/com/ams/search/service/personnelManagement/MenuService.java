package com.ams.search.service.personnelManagement;

import com.ams.search.model.personnelManagement.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
//import com.ams.search.model.personnelManagement.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
public interface MenuService extends IService<Menu> {

    /**
     * 删除菜单及子菜单
     * @param menuId
     * @return
     */
    Boolean deleteById(String menuId);

    /**
     * 获取当前登录人所拥有的菜单权限
     * @param userId 用户id
     * @return
     */
    List<Menu> getUserMenuList(String userId);
}
