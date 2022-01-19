package com.zwedu.cust.mapper;

import com.zwedu.cust.entity.Cust;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 客户mapper
 *
 * @author qingchuan
 * @date 2020/12/26
 */
@Mapper
public interface CustMapper {
    @Select({
            "<script>",
            "select",
            "id, name, pos_id",
            "from tb_cust",
            "where pos_id in (",
            "<foreach collection='posIdList' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pos_id", property = "posId", jdbcType = JdbcType.BIGINT)
    })
    List<Cust> listByPosId(List<Long> posIdList);
}
