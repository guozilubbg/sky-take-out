package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void insertCategory(Category category);

    Page<Category> queryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void startOrStopCategory(String status, String id);

    List<Category> queryCategoryByType(String type);

    void deleteCategoryById(String id);
}
