package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionNodeControlPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * 维度节点管控dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodeControlMapper extends DimensionNodeControlBaseMapper {


    /**
     * 绑定object节点
     *
     * @param currentUserId 当前用户
     * @param subjectNodeId 主体ID
     * @param objectNodeId  客体ID
     */
    @Insert({
            "insert into tb_dimension_node_control (subject_node_id, object_node_id, ",
            "create_time, create_user_id)",
            "values (#{subjectNodeId,jdbcType=BIGINT}, #{objectNodeId,jdbcType=BIGINT}, ",
            "now(), #{currentUserId,jdbcType=BIGINT})"
    })
    void bindObjectNode(@Param("currentUserId") Long currentUserId,
                        @Param("subjectNodeId") Long subjectNodeId,
                        @Param("objectNodeId") Long objectNodeId);


    /**
     * 绑定object节点
     *
     * @param subjectNodeId 主体ID
     * @param objectNodeId  客体ID
     */
    @Insert({
            "delete from tb_dimension_node_control where subject_node_id = #{subjectNodeId,jdbcType=BIGINT}",
            " and object_node_id = #{objectNodeId,jdbcType=BIGINT} "
    })
    void unbindObjectNode(@Param("subjectNodeId") Long subjectNodeId,
                          @Param("objectNodeId") Long objectNodeId);

    @Select({
            "select",
            "id, subject_node_id, object_node_id, create_time, create_user_id ",
            "from tb_dimension_node_control",
            "where subject_node_id = #{subjectNodeId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "subject_node_id", property = "subjectNodeId", jdbcType = JdbcType.BIGINT),
            @Result(column = "object_node_id", property = "objectNodeId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT)
    })
    DimensionNodeControlPo queryControl(@Param("subjectNodeId") Long subjectNodeId);
}
