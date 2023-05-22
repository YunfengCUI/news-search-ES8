package com.ams.search.impl.personnelManagement;

import com.ams.search.dao.personnelManagement.MenuMapper;
import com.ams.search.service.personnelManagement.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
//import com.ams.search.dao.personnelManagement.MenuMapper;
import com.ams.search.model.personnelManagement.Menu;
//import com.ams.search.service.personnelManagement.MenuService;
import com.ams.search.utils.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 菜单dao接口
     */
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 删除菜单及子菜单
     * @param menuId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(String menuId) {
        // 先查询出菜单list
        QueryWrapper<Menu> q = new QueryWrapper<>();
        q.eq("IS_DELETE", 0);
        List<Menu> menuList = menuMapper.selectList(q);
        // 递归查询所有子菜单
        List<Menu> childMenuList = findChildren(menuId, menuList);
        if (childMenuList.size() > 0) {
            menuMapper.batchDelete(childMenuList);
        }
        Menu menu = new Menu();
        menu.setUpdateUserId(LoginUserInfo.getLoginUserId());
        menu.setUpdateUserName(LoginUserInfo.getLoginUserName());
        menu.setUpdateTime(DateUtil.getCurrentTime());
        menu.setMenuId(menuId);
        menu.setIsDelete(1);
        return SqlHelper.retBool(menuMapper.updateById(menu));
    }

    /**
     * 获取当前登录人所拥有的菜单权限
     * @param userId 用户编号
     * @return 返回菜单列表
     */
    @Override
    public List<Menu> getUserMenuList(String userId) {
        return menuMapper.getCurrentUserMenuList(userId);
    }

    /**
     * 递归查询子菜单列表
     * @param menuId
     * @param menuList
     * @return
     */
    private static List<Menu> findChildren(String menuId, List<Menu> menuList) {
        List<Menu> childMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menuId.equals(menu.getMenuParentId())) {
                childMenuList.add(menu);
                findChildren(menu.getMenuId(), menuList);
            }
        }
        return childMenuList;
    }

}
