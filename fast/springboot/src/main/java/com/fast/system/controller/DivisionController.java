package com.fast.system.controller;

import com.fast.system.domain.AjaxResult;
import com.fast.system.domain.Division;
import com.fast.system.mapper.DivisionMapper;
import com.github.pagehelper.PageHelper; // 必须导入
import com.github.pagehelper.PageInfo;   // 必须导入
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/division")
public class DivisionController {

    @Autowired
    private DivisionMapper divisionMapper;

    /**
     * 1. 列表查询 (增加了分页参数)
     * @param pageNum  第几页 (默认1)
     * @param pageSize 每页几条 (默认10)
     */
    @GetMapping("/list")
    public AjaxResult list(Division division,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize) {

        // 1. 开启分页 (这一句必须写在查询数据库之前)
        PageHelper.startPage(pageNum, pageSize);

        // 2. 查询数据 (此时查出来的 list 已经是分页后的数据了)
        List<Division> list = divisionMapper.selectDivisionList(division);

        // 3. 获取分页信息 (包含总条数 total 等)
        PageInfo<Division> pageInfo = new PageInfo<>(list);

        // 4. 封装返回格式 { total: 100, rows: [...] }
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal()); // 总条数
        data.put("rows", list);                 // 当前页数据

        return AjaxResult.success(data);
    }

    // --- 下面的代码保持不变 ---

    @PostMapping
    public AjaxResult add(@RequestBody Division division) {
        division.setCreateBy("admin");
        return AjaxResult.success(divisionMapper.insertDivision(division));
    }

    @PutMapping
    public AjaxResult edit(@RequestBody Division division) {
        division.setUpdateBy("admin");
        return AjaxResult.success(divisionMapper.updateDivision(division));
    }

    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return AjaxResult.success(divisionMapper.deleteDivisionById(id));
    }
}