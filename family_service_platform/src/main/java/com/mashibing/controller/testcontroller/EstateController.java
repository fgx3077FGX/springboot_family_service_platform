//package com.mashibing.controller.testcontroller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mashibing.bean.*;
//import com.mashibing.returnjson.ReturnObject;
//import com.mashibing.service.EstateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@CrossOrigin(originPatterns = "*", allowedHeaders = "*", methods = {}, allowCredentials = "true")
//public class EstateController {
//
//    @Autowired
//    private EstateService estateService;
//
//    @RequestMapping("/estate/selectEstate")
//    public String selectCompany() {
//        List<TblCompany> tblCompanies = estateService.selectCompany();
//        return JSONObject.toJSONString(new ReturnObject(tblCompanies));
//    }
//    @RequestMapping("/estate/selectCompany")
//    public String selectEstate(String company){
//        List<FcEstate> fcEstates = estateService.selectEstate(company);
//        return JSONObject.toJSONString(new ReturnObject(fcEstates));
//    }
//
//    @RequestMapping("/estate/insertEstate")
//    public String insertEstate(FcEstate fcEstate) {
//        System.out.println(fcEstate);
//        System.out.println("insert estate");
//        Integer result = estateService.insertEstate(fcEstate);
//        if (result == 0) {
//            return JSONObject.toJSONString(new ReturnObject("房产编码已经存在", 0));
//        } else {
//            return JSONObject.toJSONString(new ReturnObject("插入房产成功", 1));
//        }
//    }
//
//    /**
//     * @param buildingNumber
//     * @param estateCode
//     * @return 此处应该是完成楼宇的查询功能，但是发现现在查询数据表时候没有楼宇数据表数据，因此在辨析的时候需要进行插入且插入返回的数据
//     */
//    @RequestMapping("/estate/selectBuilding")
//    public String selectBuilding(Integer buildingNumber, String estateCode) {
//        List<FcBuilding> fcBuildings = estateService.selectBuilding(buildingNumber, estateCode);
//        System.out.println(fcBuildings);
//        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
//    }
//
//    @RequestMapping("/estate/updateBuilding")
//    public String updateBuilding(FcBuilding fcBuilding) {
//        Integer result = estateService.updateBuilding(fcBuilding);
//        if (result == 1) {
//            return JSONObject.toJSONString(new ReturnObject("楼宇更新成功", 1));
//        } else {
//            return JSONObject.toJSONString(new ReturnObject("楼宇更新失败", 0));
//        }
//
//    }
//
//    @RequestMapping("/estate/selectUnit")
//    public String selectUnit(@RequestBody UnitMessage[] unitMessages) {
//        List<FcUnit> allUnits = new ArrayList<>();
//        for (UnitMessage unitMessage : unitMessages) {
//            allUnits.addAll(estateService.selectUnit(unitMessage));
//        }
//        return JSONObject.toJSONString(new ReturnObject(allUnits));
//    }
//
//    @RequestMapping("/estate/updateUnit")
//    public String updateUnit(FcUnit fcUnit) {
//        Integer result = estateService.updateUnit(fcUnit);
//        if (result == 1) {
//            return JSONObject.toJSONString(new ReturnObject("单元更新成功", 1));
//        } else {
//            return JSONObject.toJSONString(new ReturnObject("单元更新失败", 0));
//        }
//    }
//
//    @RequestMapping("/estate/insertCell")
//    public String insertCell(@RequestBody CellMessage[] cellMessage) {
//        System.out.println("insert cell-----");
//        List<FcCell> fcCells = estateService.insertCell(cellMessage);
//        return JSONObject.toJSONString(new ReturnObject(fcCells));
//    }
//
//    @RequestMapping("/estate/selectBuildingByEstate")
//    public String selectBuildingByEstate(String estateCode) {
//        List<FcBuilding> fcBuildings = estateService.selectBuildingByEstate(estateCode);
//        System.out.println("-----------");
//        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
//    }
//
//    @RequestMapping("/estate/selectUnitByBuildingCode")
//    public String selectUnitByBuildingCode(String buildingCode) {
//        System.out.println("select unit");
//        List<FcBuilding> fcUnit = estateService.selectBuildingByEstate(buildingCode);
//        System.out.println(fcUnit.size());
//        return JSONObject.toJSONString(new ReturnObject(fcUnit));
//    }
//    @RequestMapping("/estate/selectCell")
//    public String selectCell(String unitCode){
//        List<FcCell> fcCells = estateService.selectCell(unitCode);
//        System.out.println(fcCells.size());
//        return JSONObject.toJSONString(new ReturnObject(fcCells));
//    }
//
//}
