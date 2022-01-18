package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DimensionPo;
import org.apache.ibatis.jdbc.SQL;

public class DimensionSqlSupporter {

    public String insertSelective(DimensionPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_dimension");
        
        if (record.getEnName() != null) {
            sql.VALUES("en_name", "#{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.VALUES("cn_name", "#{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getBizLineId() != null) {
            sql.VALUES("biz_line_id", "#{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getNodeTypeId() != null) {
            sql.VALUES("node_type_id", "#{nodeTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getUseExtProperty() != null) {
            sql.VALUES("use_ext_property", "#{useExtProperty,jdbcType=INTEGER}");
        }
        
        if (record.getRemark() != null) {
            sql.VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=BIGINT}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            sql.VALUES("update_user_id", "#{updateUserId,jdbcType=BIGINT}");
        }
        
        if (record.getDeleted() != null) {
            sql.VALUES("deleted", "#{deleted,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DimensionPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_dimension");
        
        if (record.getEnName() != null) {
            sql.SET("en_name = #{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.SET("cn_name = #{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getBizLineId() != null) {
            sql.SET("biz_line_id = #{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getNodeTypeId() != null) {
            sql.SET("node_type_id = #{nodeTypeId,jdbcType=BIGINT}");
        }
        
        if (record.getUseExtProperty() != null) {
            sql.SET("use_ext_property = #{useExtProperty,jdbcType=INTEGER}");
        }
        
        if (record.getRemark() != null) {
            sql.SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.SET("create_user_id = #{createUserId,jdbcType=BIGINT}");
        }
        
        if (record.getUpdateTime() != null) {
            sql.SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateUserId() != null) {
            sql.SET("update_user_id = #{updateUserId,jdbcType=BIGINT}");
        }
        
        if (record.getDeleted() != null) {
            sql.SET("deleted = #{deleted,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}