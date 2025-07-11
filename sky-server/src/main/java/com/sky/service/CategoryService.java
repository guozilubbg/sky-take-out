package com.sky.service;

import com.sky.dto.CategoryDTO;

public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     */
    void insertCategory(CategoryDTO categoryDTO);
}
