package com.mashibing.mapper;

import com.mashibing.bean.FcEstate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 楼盘信息 Mapper 接口
 * </p>
 *
 * @author lian
 * @since 2023-02-26
 */
public interface FcEstateMapper extends BaseMapper<FcEstate> {

    List<FcEstate> selectAllEstate();
}
