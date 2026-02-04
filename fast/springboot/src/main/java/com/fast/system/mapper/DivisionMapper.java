package com.fast.system.mapper;

import com.fast.system.domain.Division;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DivisionMapper {
    // 查询列表
    List<Division> selectDivisionList(Division division);

    // 新增
    int insertDivision(Division division);

    // 修改
    int updateDivision(Division division);

    // 删除
    int deleteDivisionById(Long id);
}