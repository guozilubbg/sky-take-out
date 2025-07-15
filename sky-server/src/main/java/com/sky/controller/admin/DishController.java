package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @ApiOperation("新增菜品")
    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("新增菜品的参数为：{}",dishDTO);
        dishService.addDish(dishDTO);
        return Result.success();
    }
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult>  selectDishByPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询参数为：{}",dishPageQueryDTO);
        PageResult pageResult = dishService.selectDishByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteDishByIds(String ids){
        log.info("批量删除菜品参数为：{}",ids);
        dishService.deleteDishByIds(ids);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish (@RequestBody DishDTO dishDTO){
        log.info("修改菜品参数为：{}",dishDTO);
        dishService.updateDish(dishDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishDTO> queryDishById(@PathVariable String id){
        log.info("根据id查询菜品参数为：{}",id);
        DishDTO dishDTO = dishService.queryDishById(id);
        return Result.success(dishDTO);
    }
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> queryDishByCategoryId(String categoryId){
        log.info("根据分类id查询菜品:{}",categoryId);
        List<Dish> dish =dishService.queryDishByCategoryId(categoryId);
        return Result.success(dish);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    public Result startOrStopDishStatus(@PathVariable String status,String id){
        log.info("菜品起售、停售:{},{}",status,id);
        dishService.startOrStopDishStatus(status,id);
        return Result.success();
    }
}
