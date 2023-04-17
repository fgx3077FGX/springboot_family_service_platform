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
     * @return 在插入数据之前，最好对当前信息做判断，判断住宅编码是否存在，如果存在则不允许插入，如果不存在则允许插入
     */
    public Integer insertEstate(FcEstate fcEstate) {
        //定义查询包装类
        QueryWrapper queryWrapper = new QueryWrapper();
        //查询后台数据库是否存在estate_code,如果不存在则允许插入
        queryWrapper.eq("estate_code", fcEstate.getEstateCode());
        /**
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
     * @param维护楼宇
     * @return 此处应该完成的是楼宇的查询功能，但是现在数据表中没有任何楼宇的数据，因此在辨析的时候需要进行插入且返回插入的数据
     *
     */
    public List<FcBuilding> selectBuilding(Integer buildingNumber, String estateCode) {
        //插入楼宇表单
        List<FcBuilding> fcBuildingList = new ArrayList<>();
        for (int i = 0; i < buildingNumber; i++) {
            FcBuilding fcBuilding = new FcBuilding();
            fcBuilding.setBuildingCode(estateCode + "B" + (i + 1));
            fcBuilding.setBuildingName("第" + (i + 1) + "号楼");
            //estateCode 设置唯一标识码
            fcBuilding.setEstateCode(estateCode);
            System.out.println("楼宇: " + fcBuilding);
            //插入操作
            fcBuildingMapper.insert(fcBuilding);
            fcBuildingList.add(fcBuilding);
        }
        return fcBuildingList;
    }

   /**
   @param
   @return
    更新楼宇
   */
    public Integer updateBuilding(FcBuilding fcBuilding) {
        int result = fcBuildingMapper.updateById(fcBuilding);
        return result;
    }

    public List<FcUnit> selectUnit(UnitMessage unitMessage) {
        //定义返回结果集
        //插入楼宇单元
        List<FcUnit> fcUnitList = new ArrayList<>();
        for (int i = 0; i < unitMessage.getUnitCount(); i++) {
            FcUnit fcUnit = new FcUnit();
            fcUnit.setBuildingCode(unitMessage.getBuildingCode());
            //设置唯一标识码 unitCode
            fcUnit.setUnitCode(unitMessage.getBuildingCode()+ "U" + (i + 1));
            fcUnit.setUnitName("第" + (i + 1) + "单元");
            fcUnitMapper.insert(fcUnit);
            fcUnitList.add(fcUnit);
        }

//        List<FcUnit> fcUnitList = new ArrayList<>();
//        for (int i = 0; i < unitMessage.getUnitCount(); i++) {
//            FcUnit fcUnit = new FcUnit();
//            fcUnit.setBuildingCode(unitMessage.getBuildingCode());
//            fcUnit.setUnitCode(unitMessage.getBuildingCode()+"U" + (i + 1));
//            fcUnit.setUnitName("第" + (i + 1) + "单元");
//            fcUnitMapper.insert(fcUnit);
//            fcUnitList.add(fcUnit);
//        }
        return fcUnitList;
    }

    /**
     * @更新单元
     */
    public Integer updateUnit(FcUnit fcUnit) {
        //维护单元信息
        Integer result = fcUnitMapper.updateById(fcUnit);
        return result;
    }

    /**
     * @更新房间号
     */
    public Integer updateCell(FcCell fcCell) {
        Integer result = fcCellMapper.updateById(fcCell);
        return result;
    }

    /**
     * @维护房间信息
     */

    public List<FcCell> insertCell(CellMessage[] message) {
        List<FcCell> lists = new ArrayList<>();
        for (CellMessage cellMessage : message) {
            //楼层
            for (int i = 1; i <= cellMessage.getStopFloor(); i++) {
                //房间号
                for (int j = cellMessage.getStartCellId(); j <= cellMessage.getStopCellId(); j++) {
                    FcCell fcCell = new FcCell();
                    fcCell.setFloorNumber(i);
                    fcCell.setUnitCode(cellMessage.getUnitCode());
                    fcCell.setCellCode(cellMessage.getUnitCode() + (i + 1) + "C" + (j + 1));
                    fcCell.setCellName((i + 1) + "0" + (j + 1));
                    fcCellMapper.insert(fcCell);
                    lists.add(fcCell);
                }
            }

        }
        return lists;

    }

    public List<FcCell> selectCell(String unitCode) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List<FcCell> fcCells;
        if(queryWrapper.equals("")){
            queryWrapper.select("*");
          fcCells=  fcCellMapper.selectList(queryWrapper);
        }else {
            queryWrapper.eq("unit_code",unitCode);
         fcCells=   fcCellMapper.selectList(queryWrapper);
        }
        return fcCells;
    }

    public List<FcBuilding> selectBuildingByEstate(String estateCode) {
        QueryWrapper<FcBuilding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("estate_code", estateCode);
        queryWrapper.select("building_name", "building_code");
        List<FcBuilding> fcBuildings = fcBuildingMapper.selectList(queryWrapper);
        return fcBuildings;
    }
    public List<FcBuilding> selectBuildingByEstateCode(String estateCode){
        QueryWrapper <FcBuilding> queryWrapper=new QueryWrapper<>();
       List <FcBuilding>  fcBuildings;
        if(estateCode.equals("")){
            queryWrapper.select("*");
            fcBuildings = fcBuildingMapper.selectList(queryWrapper);
        }else {
            queryWrapper.eq("estateCode",estateCode);
            fcBuildings= fcBuildingMapper.selectList(queryWrapper);
        }
        return  fcBuildings;
    }

    public List<FcUnit> selectUnitByBuildingCode(String buildingCode) {
        QueryWrapper<FcUnit> queryWrapper = new QueryWrapper<>();
        List<FcUnit> fcUnits;
        if(queryWrapper.equals("")){
            queryWrapper.select("*");
        }else {
          queryWrapper.eq("building_code",buildingCode);
        }
        fcUnits = fcUnitMapper.selectList(queryWrapper);
        return fcUnits;
    }

    /**
     * @房产批量查询
     */
    public List<FcEstate> selectEstate(String company) {
        QueryWrapper<FcEstate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company", company);
        List<FcEstate> fcEstates = fcEstateMapper.selectList(queryWrapper);
        return fcEstates;
    }

    /**
     * @住宅维护
     */
    public List<FcEstate> selectAllEstate( ) {
        List<FcEstate> fcEstates = fcEstateMapper.selectAllEstate();
        System.out.println("住宅维护： "+fcEstates);
        return  fcEstates;
    }

}
