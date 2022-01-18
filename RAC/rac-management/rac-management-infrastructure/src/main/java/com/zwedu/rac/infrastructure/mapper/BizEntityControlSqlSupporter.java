package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.BizEntityControlPo;
import org.apache.ibatis.jdbc.SQL;

public class BizEntityControlSqlSupporter {

    public String insertSelective(BizEntityControlPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_biz_entity_control");
        
        if (record.getSubjectEntityId() != null) {
            sql.VALUES("subject_entity_id", "#{subjectEntityId,jdbcType=BIGINT}");
        }
        
        if (record.getObjectEntityId() != null) {
            sql.VALUES("object_entity_id", "#{objectEntityId,jdbcType=BIGINT}");
        }
        
        if (record.getBizLineId() != null) {
            sql.VALUES("biz_line_id", "#{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getDescription() != null) {
            sql.VALUES("description", "#{description,jdbcType=VARCHAR}");
        }
        
        if (record.getParentId() != null) {
            sql.VALUES("parent_id", "#{parentId,jdbcType=BIGINT}");
        }
        
        if (record.getParentPath() != null) {
            sql.VALUES("parent_path", "#{parentPath,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(BizEntityControlPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_biz_entity_control");
        
        if (record.getSubjectEntityId() != null) {
            sql.SET("subject_entity_id = #{subjectEntityId,jdbcType=BIGINT}");
        }
        
        if (record.getObjectEntityId() != null) {
            sql.SET("object_entity_id = #{objectEntityId,jdbcType=BIGINT}");
        }
        
        if (record.getBizLineId() != null) {
            sql.SET("biz_line_id = #{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getDescription() != null) {
            sql.SET("description = #{description,jdbcType=VARCHAR}");
        }
        
        if (record.getParentId() != null) {
            sql.SET("parent_id = #{parentId,jdbcType=BIGINT}");
        }
        
        if (record.getParentPath() != null) {
            sql.SET("parent_path = #{parentPath,jdbcType=VARCHAR}");
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