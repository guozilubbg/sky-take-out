package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> queryCategory( CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类查询参数：{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.queryCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PutMapping
    @ApiOperation("修改分类")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类参数为：{}",categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
    @ApiOperation("启用禁用分类")
    @PostMapping("/status/{status}")
    public Result startOrStopCategory(@PathVariable String status,String id){
        log.info("启用禁用分类参数：status:{},id:{}",status,id);
        categoryService.startOrStopCategory(status,id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>>  queryCategoryByType(String type){
        log.info("根据类型查询分类参数为:{}",type);
        List<Category> category = categoryService.queryCategoryByType(type);
        return Result.success(category);
    }
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteCategoryById(String id){
        log.info("根据id删除分类参数为：{}",id);
        categoryService.deleteCategoryById(id);
        return Result.success();
    }
}
