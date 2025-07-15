package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiveImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    /*
    新增菜品
     */
    @Override
    public void addDish(DishDTO dishDTO) {
        //插入到dish表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.addDish(dish);

        DishFlavor dishFlavor = new DishFlavor();
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();
        // 查询id
        Long dishId = dishMapper.selectDishId();
        dishFlavor.setDishId(dishId);
        //插入dish_flavor
        for (int i = 0; i < dishFlavorList.size(); i++) {
            dishFlavor.setName(String.valueOf(dishFlavorList.get(i).getName()));
            dishFlavor.setValue(String.valueOf(dishFlavorList.get(i).getValue()));
            dishMapper.addDishFlavors(dishFlavor);
        }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult selectDishByPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.selectDishByPage(dishPageQueryDTO);
        long total = page.getTotal();
        List<Dish> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public void deleteDishByIds(String ids) {
        String[] idsList = ids.split(",");
        for (int i = 0; i < idsList.length; i++) {
            dishMapper.deleteDishByIds(idsList[i]);
            //todo dish_flavor 也需要删掉
            dishMapper.deleteDishFlavorByIds(idsList[i]);
        }
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
//        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
//        dish.setCreateUser(BaseContext.getCurrentId());
        dish.setUpdateUser(BaseContext.getCurrentId());
        dishMapper.updateDish(dish);

        DishFlavor dishFlavor = new DishFlavor();
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();
        // 查询id
        Long dishId = dishMapper.selectDishId();
        dishFlavor.setDishId(dishId);

        //和菜品关联的口味表也需要都修改
//        int num = dishMapper.selectId(dishId);
//        if (num >= dishFlavorList.size()) {
//        }
        //插入dish_flavor
        for (int i = 0; i < dishFlavorList.size(); i++) {
            dishFlavor.setId(dishFlavorList.get(i).getId());
            dishFlavor.setDishId(dishFlavorList.get(i).getDishId());
            dishFlavor.setName(String.valueOf(dishFlavorList.get(i).getName()));
            dishFlavor.setValue(String.valueOf(dishFlavorList.get(i).getValue()));
            dishMapper.updateDishFlavors(dishFlavor);
        }
    }

    @Override
    public DishDTO queryDishById(String id) {
        DishDTO dishDTO = new DishDTO();
        Dish dish = dishMapper.queryDishById(id);
        BeanUtils.copyProperties(dish,dishDTO);
        List<Integer> dishFlavorList = dishMapper.selectId(id);

        List<DishFlavor> dishFlavorList1 = new ArrayList<>();
        for (int i = 0; i < dishFlavorList.size(); i++) {
            DishFlavor dishFlavor = dishMapper.queryDishFlavorById(dishFlavorList.get(i));
            dishFlavorList1.add(dishFlavor);
        }
        dishDTO.setFlavors(dishFlavorList1);
        return dishDTO;
    }

    @Override
    public List<Dish> queryDishByCategoryId(String categoryId) {
        List<Dish> dish = dishMapper.queryDishByCategoryId(categoryId);
        return dish;
    }

    @Override
    public void startOrStopDishStatus(String status, String id) {
        dishMapper.startOrStopDishStatus(status,id);
    }
}
