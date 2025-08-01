package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeeUpPassworldDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Param;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    // 新增员工
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, long id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee getById(@Param("id") long id);

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    void updateById(EmployeeDTO employeeDTO);

    /**
     * 修改密码
     */
    void updatePassword(EmployeeUpPassworldDTO employeeUpPassworldDTO);
}
