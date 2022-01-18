package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionNodePo;
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

public interface DimensionNodeBaseMapper {
    @Delete({
        "delete from tb_dimension_node",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_dimension_node (en_name, cn_name, ",
        "type, biz_line_id, ",
        "decentralized_control_id, dimension_id, ",
        "parent_id, parent_path, ",
        "status, remark, ",
        "create_time, create_user_id, ",
        "update_time, update_user_id, ",
        "deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER}, #{bizLineId,jdbcType=BIGINT}, ",
        "#{decentralizedControlId,jdbcType=BIGINT}, #{dimensionId,jdbcType=BIGINT}, ",
        "#{parentId,jdbcType=BIGINT}, #{parentPath,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{updateUserId,jdbcType=BIGINT}, ",
        "#{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(DimensionNodePo record);

    @InsertProvider(type=DimensionNodeSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(DimensionNodePo record);

    @Select({
        "select",
        "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, ",
        "parent_id, parent_path, status, remark, create_time, create_user_id, update_time, ",
        "update_user_id, deleted",
        "from tb_dimension_node",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="decentralized_control_id", property="decentralizedControlId", jdbcType=JdbcType.BIGINT),
        @Result(column="dimension_id", property="dimensionId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_path", property="parentPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    DimensionNodePo selectByPrimaryKey(Long id);

    @UpdateProvider(type=DimensionNodeSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DimensionNodePo record);

    @Update({
        "update tb_dimension_node",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "decentralized_control_id = #{decentralizedControlId,jdbcType=BIGINT},",
          "dimension_id = #{dimensionId,jdbcType=BIGINT},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "parent_path = #{parentPath,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DimensionNodePo record);
}