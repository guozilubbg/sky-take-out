package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     */
    void insertCategory(CategoryDTO categoryDTO);

    PageResult queryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void startOrStopCategory(String status, String id);

    List<Category> queryCategoryByType(String type);

    void deleteCategoryById(String id);
}
