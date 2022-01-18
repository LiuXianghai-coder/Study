package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionNodeControlPo;
import org.apache.ibatis.jdbc.SQL;

public class DimensionNodeControlSqlSupporter {

    public String insertSelective(DimensionNodeControlPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_dimension_node_control");
        
        if (record.getSubjectNodeId() != null) {
            sql.VALUES("subject_node_id", "#{subjectNodeId,jdbcType=BIGINT}");
        }
        
        if (record.getObjectNodeId() != null) {
            sql.VALUES("object_node_id", "#{objectNodeId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DimensionNodeControlPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_dimension_node_control");
        
        if (record.getSubjectNodeId() != null) {
            sql.SET("subject_node_id = #{subjectNodeId,jdbcType=BIGINT}");
        }
        
        if (record.getObjectNodeId() != null) {
            sql.SET("object_node_id = #{objectNodeId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.SET("create_user_id = #{createUserId,jdbcType=BIGINT}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}