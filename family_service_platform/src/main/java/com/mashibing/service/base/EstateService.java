package com.mashibing.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.bean.*;
import com.mashibing.mapper.FcBuildingMapper;
import com.mashibing.mapper.FcEstateMapper;
import com.mashibing.mapper.FcUnitMapper;
import com.mashibing.mapper.TblCompanyMapper;
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

    public List<TblCompany> selectCompany() {
        List<TblCompany> companies = tblCompanyMapper.selectCompany();
        return companies;
    }
    /**
     * @param
     * @return
     * 在插入数据之前，最好对当前信息做判断，判断住宅编码是否存在，如果存在则不允许插入，如果存在则允许插入
     *
     * */
    public Integer insertEstate(FcEstate fcEstate){
        //定义查询包装类
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("estate_code", fcEstate.getEstateCode());
        /*
        * estate_code:数据库字段名
        * */
        FcEstate findResult = fcEstateMapper.selectOne(queryWrapper);
        //定义返回结果
        int result=0;
        if(findResult!=null){
            return result;
        }else{
            result = fcEstateMapper.insert(fcEstate);
        }
        return result;
    }
    /**
     * @param
     * @return
     * */
    public List<FcBuilding> selectBuilding(Integer buildingNumber,String estateCode){
        List<FcBuilding> fcBuildings=new ArrayList<>();
        for(int i=0;i<buildingNumber;i++){
            FcBuilding fcBuilding=new FcBuilding();
            fcBuilding.setBuildingCode("B"+(i+1));
            fcBuilding.setBuildingName("第"+(i+1)+"号楼");
            fcBuilding.setEstateCode((estateCode));
            fcBuildingMapper.insert(fcBuilding);
            fcBuildings.add(fcBuilding);
            System.out.println("fcBuildings: "+fcBuildings);
        }
        return  fcBuildings;
    }
    /**
     * @param
     * @return
     * 更新楼宇
     * */
    public Integer updateBuilding(FcBuilding fcBuilding){
        int result = fcBuildingMapper.updateById(fcBuilding);
        return result;
    }
    public List<FcUnit> selectUnit(UnitMessage unitMessage){
        //定义返回结果集
        List<FcUnit> fcUnitList=new ArrayList<>();
        for(int i=0;i<unitMessage.getUnitCount();i++){
            FcUnit fcUnit=new FcUnit();
            fcUnit.setBuildingCode(unitMessage.getBuildingCode());
            fcUnit.setUnitCode("U"+(i+1));
            fcUnit.setUnitName("第"+(i+1)+"单元");
            fcUnitMapper.insert(fcUnit);
            fcUnitList.add(fcUnit);
        }
        return  fcUnitList;
    }


}
