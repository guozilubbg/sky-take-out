package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类相关接口
 */
@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增分类")
    @PostMapping
    public Result insertCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类参数为:{}",categoryDTO);
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }
}
