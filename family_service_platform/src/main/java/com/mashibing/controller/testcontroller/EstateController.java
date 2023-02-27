package com.mashibing.controller.testcontroller;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.bean.*;
import com.mashibing.returnjson.ReturnObject;
import com.mashibing.service.base.EstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", methods = {}, allowCredentials = "true")
public class EstateController {

    @Autowired
    private EstateService estateService;


    @RequestMapping("/estate/selectCompany")
    public String selectCompany() {
        System.out.println("selectCompany-----------");
        List<TblCompany> companies = estateService.selectCompany();
        System.out.println("companies:" + companies);
        return JSONObject.toJSONString(new ReturnObject(companies));
    }

    /**
     * @插入数据
     */
    @RequestMapping("/estate/insertEstate")
    public String insertEstate(FcEstate fcEstate) {
        int result = estateService.insertEstate(fcEstate);
        //对结果进行逻辑判断
        if (result == 0) {
            return JSONObject.toJSONString(new ReturnObject("0", "房产编码已经存在"));

        } else {
            return JSONObject.toJSONString(new ReturnObject("1", "插入房产成功"));

        }

    }

    /**
     * @param
     * @return 此处应该完成的是楼宇的查询功能，但是现在数据表中没有任何楼宇的数据，因此在辨析的时候需要进行插入且返回插入的数据
     */
    @RequestMapping("/estate/selectBuilding")
    public String selectBuilding(Integer buildingNumber, String estateCode) {
        System.out.println("selectBuilding");
        List<FcBuilding> fcBuildings = estateService.selectBuilding(buildingNumber, estateCode);
        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
    }

    @RequestMapping("/estate/updateBuilding")
    public String updateBuilding(FcBuilding fcBuilding) {
        Integer result = estateService.updateBuilding(fcBuilding);
        if (result == 0) {
            System.out.println("result: " + result);
            return JSONObject.toJSONString(new ReturnObject("更新楼宇失败,请重新输入正确的楼宇信息"));

        } else {
            System.out.println("result: " + result);
            return JSONObject.toJSONString(new ReturnObject("楼宇更新成功"));
        }


    }

    @RequestMapping("estate/selectUnit")
    public String selectUnit(@RequestBody UnitMessage[] unitMessages) {
        System.out.println("selectUnit--------");
        List<FcUnit> allUnit = new ArrayList<>();
        for (UnitMessage unitMessage : unitMessages
        ) {
            allUnit.addAll(estateService.selectUnit(unitMessage));
        }
        return JSONObject.toJSONString(new ReturnObject(allUnit));
    }
}

