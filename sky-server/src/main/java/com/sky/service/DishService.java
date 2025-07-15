package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult selectDishByPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishByIds(String ids);

    void updateDish(DishDTO dishDTO);

    DishDTO queryDishById(String id);

    List<Dish> queryDishByCategoryId(String categoryId);

    void startOrStopDishStatus(String status, String id);
}
