package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.StrategyPo;
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

public interface StrategyBaseMapper {
    @Delete({
        "delete from tb_strategy",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_strategy (biz_line_id, en_name, ",
        "cn_name, type, expression, ",
        "priority, remark, ",
        "create_time, create_user_id, ",
        "update_time, update_user_id, ",
        "deleted)",
        "values (#{bizLineId,jdbcType=BIGINT}, #{enName,jdbcType=VARCHAR}, ",
        "#{cnName,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, #{expression,jdbcType=VARCHAR}, ",
        "#{priority,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{updateUserId,jdbcType=BIGINT}, ",
        "#{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(StrategyPo record);

    @InsertProvider(type=StrategySqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(StrategyPo record);

    @Select({
        "select",
        "id, biz_line_id, en_name, cn_name, type, expression, priority, remark, create_time, ",
        "create_user_id, update_time, update_user_id, deleted",
        "from tb_strategy",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="expression", property="expression", jdbcType=JdbcType.VARCHAR),
        @Result(column="priority", property="priority", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    StrategyPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=StrategySqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(StrategyPo record);

    @Update({
        "update tb_strategy",
        "set biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "expression = #{expression,jdbcType=VARCHAR},",
          "priority = #{priority,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(StrategyPo record);
}