package com.mashibing.mapper;

import com.mashibing.bean.TblUserRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户档案 Mapper 接口
 * </p>
 *
 * @author lian
 * @since 2023-02-26
 */
public interface TblUserRecordMapper extends BaseMapper<TblUserRecord> {
    public TblUserRecord login(@Param("username") String username,@Param("password") String password);

}
