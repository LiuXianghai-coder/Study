package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionNodePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 用户维度节点Mapper
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserDimensionNodeMapper extends UserDimensionNodeBaseMapper {
    @Select({
            "select",
            "b.id, b.en_name, b.cn_name, b.type, b.biz_line_id, b.dimension_id, b.parent_id, b.parent_path, ",
            "b.status, b.remark, b.create_time, b.create_user_id, b.update_time, b.update_user_id, b.deleted",
            "from tb_user_dimension_node a join tb_dimension_node b on a.dimension_node_id = b.id",
            "where b.deleted = 0 and a.user_id = #{userId, jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listDimensionNodes(@Param("userId") Long userId);

    @Select({
            "select",
            "b.id, b.en_name, b.cn_name, b.type, b.biz_line_id, b.dimension_id, b.parent_id, b.parent_path, ",
            "b.status, b.remark, b.create_time, b.create_user_id, b.update_time, b.update_user_id, b.deleted",
            "from tb_user_dimension_node a join tb_dimension_node b on a.dimension_node_id = b.id",
            "where b.deleted = 0 and a.user_id = #{userId, jdbcType=BIGINT} and b.status = 0",
            " and b.biz_line_id = #{bizLineId, jdbcType=BIGINT} and b.dimension_id=#{dimensionId, jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listDimensionNodeByUser(@Param("userId") Long userId,
                                             @Param("bizLineId") Long bizLineId,
                                             @Param("dimensionId") Long dimensionId);

    /**
     * 给用户绑定维度节点
     *
     * @param currentUserId    当前用户
     * @param userId           用户
     * @param dimensionNodeIds 维度节点列表
     */
    @Insert({
            "<script>",
            "insert into tb_user_dimension_node (user_id, dimension_node_id, create_time, create_user_id)",
            "values ",
            "<foreach collection='dimensionNodeIds' item='item' index='index' separator=','>",
            " (#{userId,jdbcType=BIGINT}, #{item,jdbcType=BIGINT}, now(), #{currentUserId,jdbcType=BIGINT}) ",
            "</foreach>",
            "</script>"
    })
    void bindDimensionNodes(@Param("currentUserId") Long currentUserId, @Param("userId") Long userId,
                            @Param("dimensionNodeIds") List<Long> dimensionNodeIds);

    /**
     * 根据用户删除维度
     *
     * @param userId 用户ID
     */
    @Delete({
            "delete from tb_user_dimension_node",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    void deleteByUserId(@Param("userId") Long userId);
}
