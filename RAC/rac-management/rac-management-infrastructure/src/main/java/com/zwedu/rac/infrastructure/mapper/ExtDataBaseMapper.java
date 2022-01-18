package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.ExtDataPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface ExtDataBaseMapper {
    @Delete({
        "delete from tb_ext_data",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_ext_data (ext_property_id, biz_data_id, ",
        "biz_line_id, value, ",
        "remark, create_time, ",
        "create_user_id, update_time, ",
        "update_user_id, deleted)",
        "values (#{extPropertyId,jdbcType=BIGINT}, #{bizDataId,jdbcType=BIGINT}, ",
        "#{bizLineId,jdbcType=BIGINT}, #{value,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=BIGINT}, #{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ExtDataPo record);

    @InsertProvider(type=ExtDataSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ExtDataPo record);

    @Select({
        "select",
        "id, ext_property_id, biz_data_id, biz_line_id, value, remark, create_time, create_user_id, ",
        "update_time, update_user_id, deleted",
        "from tb_ext_data",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="ext_property_id", property="extPropertyId", jdbcType=JdbcType.BIGINT),
        @Result(column="biz_data_id", property="bizDataId", jdbcType=JdbcType.BIGINT),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    ExtDataPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=ExtDataSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ExtDataPo record);

    @Update({
        "update tb_ext_data",
        "set ext_property_id = #{extPropertyId,jdbcType=BIGINT},",
          "biz_data_id = #{bizDataId,jdbcType=BIGINT},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "value = #{value,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ExtDataPo record);
}