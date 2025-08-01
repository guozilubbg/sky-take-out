package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.EmployeeUpPassworldDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端的代码进行md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        System.out.println(password);
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
//        System.out.println("当前线程ID："+Thread.currentThread().getId());
        Employee employee = new Employee();
//        employee.setName(employeeDTO.getName());

        //对象属性拷贝 从源到目标，前提属性名字一致
        BeanUtils.copyProperties(employeeDTO,employee);

        //账号状态 常量类
        employee.setStatus(StatusConstant.ENABLE);

        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //创建人id 修改人id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);

    }
    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //分页查询就是sql select * from employee limit 0,10;pagehelper 不用写limit 自动分页
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        //page是pagehelpr的，返回的实体类
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        //page.getTotal().var
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total,result);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, long id) {
        //update em set status = ? where id = ?

        //创建一个实体对象并设置属性值  两种写法，这是传统写法，下面是builder写法
//        Employee employee = new Employee();
//        employee.setStatus(status);
//        employee.setId(id);

        Employee employee = Employee.builder().status(status).id(id).build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */

    public Employee getById(long id) {
        //select * from xx where id = ？
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    public void updateById(EmployeeDTO employeeDTO) {
        //update
        Employee employee = new Employee();
        //复制属性
        BeanUtils.copyProperties(employeeDTO,employee);

        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.updateById(employee);

    }

    /**
     * 修改密码
     */
    public void updatePassword(EmployeeUpPassworldDTO employeeUpPassworldDTO) {
        //update em set status = ? where id = ?
        System.out.println(employeeUpPassworldDTO.getId());
        System.out.println(employeeUpPassworldDTO.getOldPassworld().getBytes());
        employeeUpPassworldDTO.setNewPassword(DigestUtils.md5DigestAsHex(employeeUpPassworldDTO.getNewPassword().getBytes()));
        employeeUpPassworldDTO.setOldPassworld(DigestUtils.md5DigestAsHex(employeeUpPassworldDTO.getOldPassworld().getBytes()));
        employeeMapper.updatePassword(employeeUpPassworldDTO);
    }

}
