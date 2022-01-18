package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserPo;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlSupporter {

    public String insertSelective(UserPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_user");
        
        if (record.getEnName() != null) {
            sql.VALUES("en_name", "#{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.VALUES("cn_name", "#{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getIdCardNum() != null) {
            sql.VALUES("id_card_num", "#{idCardNum,jdbcType=VARCHAR}");
        }
        
        if (record.getMobilePhone() != null) {
            sql.VALUES("mobile_phone", "#{mobilePhone,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.VALUES("email", "#{email,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.VALUES("gender", "#{gender,jdbcType=INTEGER}");
        }
        
        if (record.getBizLineId() != null) {
            sql.VALUES("biz_line_id", "#{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getDecentralizedControlId() != null) {
            sql.VALUES("decentralized_control_id", "#{decentralizedControlId,jdbcType=BIGINT}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=INTEGER}");
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

    public String updateByPrimaryKeySelective(UserPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_user");
        
        if (record.getEnName() != null) {
            sql.SET("en_name = #{enName,jdbcType=VARCHAR}");
        }
        
        if (record.getCnName() != null) {
            sql.SET("cn_name = #{cnName,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getIdCardNum() != null) {
            sql.SET("id_card_num = #{idCardNum,jdbcType=VARCHAR}");
        }
        
        if (record.getMobilePhone() != null) {
            sql.SET("mobile_phone = #{mobilePhone,jdbcType=VARCHAR}");
        }
        
        if (record.getEmail() != null) {
            sql.SET("email = #{email,jdbcType=VARCHAR}");
        }
        
        if (record.getGender() != null) {
            sql.SET("gender = #{gender,jdbcType=INTEGER}");
        }
        
        if (record.getBizLineId() != null) {
            sql.SET("biz_line_id = #{bizLineId,jdbcType=BIGINT}");
        }
        
        if (record.getDecentralizedControlId() != null) {
            sql.SET("decentralized_control_id = #{decentralizedControlId,jdbcType=BIGINT}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=INTEGER}");
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