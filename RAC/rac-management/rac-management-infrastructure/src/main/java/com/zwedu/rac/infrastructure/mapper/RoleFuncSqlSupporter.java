package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleFuncPo;
import org.apache.ibatis.jdbc.SQL;

public class RoleFuncSqlSupporter {

    public String insertSelective(RoleFuncPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_role_func");
        
        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=BIGINT}");
        }
        
        if (record.getFuncId() != null) {
            sql.VALUES("func_id", "#{funcId,jdbcType=BIGINT}");
        }
        
        if (record.getStrategyId() != null) {
            sql.VALUES("strategy_id", "#{strategyId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(RoleFuncPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_role_func");
        
        if (record.getRoleId() != null) {
            sql.SET("role_id = #{roleId,jdbcType=BIGINT}");
        }
        
        if (record.getFuncId() != null) {
            sql.SET("func_id = #{funcId,jdbcType=BIGINT}");
        }
        
        if (record.getStrategyId() != null) {
            sql.SET("strategy_id = #{strategyId,jdbcType=BIGINT}");
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