package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.ExtDataPo;
import org.apache.ibatis.jdbc.SQL;

public class ExtDataSqlSupporter {

    public String insertSelective(ExtDataPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_ext_data");
        
        if (record.getExtPropertyId() != null) {
            sql.VALUES("ext_property_id", "#{extPropertyId,jdbcType=BIGINT}");
        }
        
        if (record.getBizDataId() != null) {
            sql.VALUES("biz_data_id", "#{bizDataId,jdbcType=BIGINT}");
        }
        
        if (record.getBizLineId() != null) {
            sql.VALUES("biz_line_id", "#{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getValue() != null) {
            sql.VALUES("value", "#{value,jdbcType=VARCHAR}");
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

    public String updateByPrimaryKeySelective(ExtDataPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_ext_data");
        
        if (record.getExtPropertyId() != null) {
            sql.SET("ext_property_id = #{extPropertyId,jdbcType=BIGINT}");
        }
        
        if (record.getBizDataId() != null) {
            sql.SET("biz_data_id = #{bizDataId,jdbcType=BIGINT}");
        }
        
        if (record.getBizLineId() != null) {
            sql.SET("biz_line_id = #{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getValue() != null) {
            sql.SET("value = #{value,jdbcType=VARCHAR}");
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