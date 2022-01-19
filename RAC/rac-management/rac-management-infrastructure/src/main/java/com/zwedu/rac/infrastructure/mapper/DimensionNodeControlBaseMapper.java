package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionNodeControlPo;
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

public interface DimensionNodeControlBaseMapper {
    @Delete({
        "delete from tb_dimension_node_control",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_dimension_node_control (subject_node_id, object_node_id, ",
        "create_time, create_user_id)",
        "values (#{subjectNodeId,jdbcType=BIGINT}, #{objectNodeId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(DimensionNodeControlPo record);

    @InsertProvider(type=DimensionNodeControlSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(DimensionNodeControlPo record);

    @Select({
        "select",
        "id, subject_node_id, object_node_id, create_time, create_user_id",
        "from tb_dimension_node_control",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="subject_node_id", property="subjectNodeId", jdbcType=JdbcType.BIGINT),
        @Result(column="object_node_id", property="objectNodeId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    DimensionNodeControlPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=DimensionNodeControlSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DimensionNodeControlPo record);

    @Update({
        "update tb_dimension_node_control",
        "set subject_node_id = #{subjectNodeId,jdbcType=BIGINT},",
          "object_node_id = #{objectNodeId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DimensionNodeControlPo record);
}