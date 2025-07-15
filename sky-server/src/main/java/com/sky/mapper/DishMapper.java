package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    @Insert("insert into dish (name,category_id,price,image,description,status,create_time,update_time,create_user,update_user)" +
            "values (#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void addDish(Dish dish);


    void addDishFlavors(DishFlavor dishFlavor);

    Long selectDishId();

    Page<Dish> selectDishByPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishByIds(String s);

    void updateDish(Dish dish);

    void updateDishFlavors(DishFlavor dishFlavor);

    List<Integer> selectId(String id);

    void deleteDishFlavorByIds(String s);

    Dish queryDishById(String id);

    DishFlavor queryDishFlavorById(int i);

    List<Dish> queryDishByCategoryId(String categoryId);

    void startOrStopDishStatus(String status, String id);
}
