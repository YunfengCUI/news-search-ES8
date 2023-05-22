package com.ams.search.controller.personnelManagement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ams.search.model.personnelManagement.UserToken;
import com.ams.search.model.vo.Result;
import com.ams.search.service.personnelManagement.UserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author niqingjie
 * @since 2022-02-25
 */

@Api(tags = "TOKEN服务")
@RequestMapping("/userToken")
@RestController
public class UserTokenController {

    @Autowired
    private UserTokenService userTokenService;

    @ApiOperation("创建单个UserToken")
    @PostMapping("/add")
    public Result<Boolean> insert(@RequestBody UserToken userToken) {
        return Result.OK(userTokenService.save(userToken));
    }


    @ApiOperation("删除单个UserToken")
    @PostMapping("/delete/{uuid}")
    public Result<Boolean> deleteById(@PathVariable("uuid") String uuid) {
        return Result.OK(userTokenService.removeById(uuid));
    }

    @ApiOperation("编辑单个UserToken")
    @PostMapping("/updateByUuId")
    public Result<Boolean> updateByUuId(@RequestBody UserToken userToken) {
        return Result.OK(userTokenService.updateById(userToken));
    }

    @ApiOperation("查询单个UserToken")
    @GetMapping("/selectByUuid/{uuid}")
    public Result<UserToken> selectByUuid(@PathVariable("uuid") String uuid) {
        return Result.OK(userTokenService.getById(uuid));
    }

    @ApiOperation("查询分页UserToken")
    @GetMapping("/selectPage")
    public Result<IPage<UserToken>> selectPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               UserToken userToken) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper();
        IPage<UserToken> page = userTokenService.page(new Page<>(pageNo,pageSize), queryWrapper);
        return Result.OK(page);
    }
}
