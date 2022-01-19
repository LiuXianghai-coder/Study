package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.BizLinePo;
import org.apache.ibatis.jdbc.SQL;

public class BizLineSqlSupporter {

    public String insertSelective(BizLinePo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_biz_line");
        
        if (record.getEnName() != null) {
            sql.VALUES("en_name", "#{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.VALUES("cn_name", "#{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getDecentralizedControl() != null) {
            sql.VALUES("decentralized_control", "#{decentralizedControl,jdbcType=INTEGER}");
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
        
        if (record.getDecentralizedControlEnName() != null) {
            sql.VALUES("decentralized_control_en_name", "#{decentralizedControlEnName,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(BizLinePo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_biz_line");
        
        if (record.getEnName() != null) {
            sql.SET("en_name = #{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.SET("cn_name = #{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getDecentralizedControl() != null) {
            sql.SET("decentralized_control = #{decentralizedControl,jdbcType=INTEGER}");
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
        
        if (record.getDecentralizedControlEnName() != null) {
            sql.SET("decentralized_control_en_name = #{decentralizedControlEnName,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}