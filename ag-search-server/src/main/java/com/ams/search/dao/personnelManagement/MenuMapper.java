package com.ams.search.dao.personnelManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ams.search.model.personnelManagement.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-23
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 批量删除菜单
     * @param menuList
     * @return
     */
    Integer batchDelete(@Param("menuList") List<Menu> menuList);

    /**
     * 获取当前登录人所拥有的菜单权限
     * @param userId 用户编号
     * @return 返回当前登录人菜单列表
     */
    List<Menu> getCurrentUserMenuList(String userId);
}
