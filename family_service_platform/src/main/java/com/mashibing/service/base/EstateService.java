package com.mashibing.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.bean.*;
import com.mashibing.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstateService {
    @Autowired
    private TblCompanyMapper tblCompanyMapper;
    @Autowired
    private FcEstateMapper fcEstateMapper;
    @Autowired
    private FcBuildingMapper fcBuildingMapper;
    @Autowired
    private FcUnitMapper fcUnitMapper;
    @Autowired
    private FcCellMapper fcCellMapper;

    public List<TblCompany> selectCompany() {
        List<TblCompany> companies = tblCompanyMapper.selectCompany();
        return companies;
    }

    /**
     * @param
     * @return 在插入数据之前，最好对当前信息做判断，判断住宅编码是否存在，如果存在则不允许插入，如果存在则允许插入
     */
    public Integer insertEstate(FcEstate fcEstate) {
        //定义查询包装类
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("estate_code", fcEstate.getEstateCode());
        /*
         * estate_code:数据库字段名
         * */
        FcEstate findResult = fcEstateMapper.selectOne(queryWrapper);
        //定义返回结果
        int result = 0;
        if (findResult != null) {
            return result;
        } else {
            result = fcEstateMapper.insert(fcEstate);
        }
        return result;
    }

    /**
     * @param
     * @return
     */
    public List<FcBuilding> selectBuilding(Integer buildingNumber, String estateCode) {
        List<FcBuilding> fcBuildings = new ArrayList<>();
        for (int i = 0; i < buildingNumber; i++) {
            FcBuilding fcBuilding = new FcBuilding();
            fcBuilding.setBuildingCode(estateCode+"B" + (i + 1));
            fcBuilding.setBuildingName("第" + (i + 1) + "号楼");
            fcBuilding.setEstateCode((estateCode));
            fcBuildingMapper.insert(fcBuilding);
            fcBuildings.add(fcBuilding);
            System.out.println("fcBuildings: " + fcBuildings);
        }
        return fcBuildings;
    }

    /**
     * @param
     * @return 更新楼宇
     */
    public Integer updateBuilding(FcBuilding fcBuilding) {
        int result = fcBuildingMapper.updateById(fcBuilding);
        return result;
    }

    public List<FcUnit> selectUnit(UnitMessage unitMessage) {
        //定义返回结果集
        List<FcUnit> fcUnitList = new ArrayList<>();
        for (int i = 0; i < unitMessage.getUnitCount(); i++) {
            FcUnit fcUnit = new FcUnit();
            fcUnit.setBuildingCode(unitMessage.getBuildingCode());
            fcUnit.setUnitCode(unitMessage.getBuildingCode()+"U" + (i + 1));
            fcUnit.setUnitName("第" + (i + 1) + "单元");
            fcUnitMapper.insert(fcUnit);
            fcUnitList.add(fcUnit);
        }
        return fcUnitList;
    }

    public Integer updateUnit(FcUnit fcUnit) {
        //维护单元信息
        Integer result = fcUnitMapper.updateById(fcUnit);
        return result;
    }

    public List<FcCell> insertCell(CellMessage[] message) {
        List<FcCell> lists = new ArrayList<>();
        for (CellMessage cellMessage : message) {
            //楼层
            for (int i = 1; i <= cellMessage.getStopFloor(); i++) {
                //房间号
                for (int j = cellMessage.getStartCellId(); j <= cellMessage.getStopCellId(); j++) {
                    FcCell fcCell = new FcCell();
                    fcCell.setUnitCode(cellMessage.getUnitCode());
                    fcCell.setCellName(i + "0" + j);
                    fcCell.setCellCode(cellMessage.getUnitCode()+"C" + i + j);
                    fcCell.setFloorNumber(i);
                    fcCellMapper.insert(fcCell);
                    lists.add(fcCell);
                }
            }

        }
        return lists;
    }
    public List<FcBuilding> selectBuildingByEstate(String estateCode){
        QueryWrapper <FcBuilding> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("estate_code",estateCode);
        queryWrapper.select("building_name","building_code");
        List<FcBuilding> fcBuildings = fcBuildingMapper.selectList(queryWrapper);
        return  fcBuildings;
    }
    public List<FcUnit> selectUnitByBuildingCode(String buildingCode){
        QueryWrapper <FcUnit> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("building_code",buildingCode);
        queryWrapper.select("unit_name", "unit_code");
        List<FcUnit> fcUnits = fcUnitMapper.selectList(queryWrapper);
        return fcUnits;
    }
    public List<FcEstate> selectEstate(String company){
        QueryWrapper <FcEstate> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("company",company);
        List<FcEstate> fcEstates = fcEstateMapper.selectList(queryWrapper);
        return fcEstates;
    }
}
