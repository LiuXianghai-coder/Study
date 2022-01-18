package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserDimensionNodePo;
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

public interface UserDimensionNodeBaseMapper {
    @Delete({
        "delete from tb_user_dimension_node",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_user_dimension_node (user_id, dimension_node_id, ",
        "create_time, create_user_id)",
        "values (#{userId,jdbcType=BIGINT}, #{dimensionNodeId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UserDimensionNodePo record);

    @InsertProvider(type=UserDimensionNodeSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UserDimensionNodePo record);

    @Select({
        "select",
        "id, user_id, dimension_node_id, create_time, create_user_id",
        "from tb_user_dimension_node",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="dimension_node_id", property="dimensionNodeId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    UserDimensionNodePo selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserDimensionNodeSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserDimensionNodePo record);

    @Update({
        "update tb_user_dimension_node",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "dimension_node_id = #{dimensionNodeId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserDimensionNodePo record);
}