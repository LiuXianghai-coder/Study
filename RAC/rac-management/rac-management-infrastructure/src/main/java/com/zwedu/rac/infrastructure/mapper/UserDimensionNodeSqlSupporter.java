package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserDimensionNodePo;
import org.apache.ibatis.jdbc.SQL;

public class UserDimensionNodeSqlSupporter {

    public String insertSelective(UserDimensionNodePo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_user_dimension_node");
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getDimensionNodeId() != null) {
            sql.VALUES("dimension_node_id", "#{dimensionNodeId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserDimensionNodePo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_user_dimension_node");
        
        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=BIGINT}");
        }
        
        if (record.getDimensionNodeId() != null) {
            sql.SET("dimension_node_id = #{dimensionNodeId,jdbcType=BIGINT}");
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