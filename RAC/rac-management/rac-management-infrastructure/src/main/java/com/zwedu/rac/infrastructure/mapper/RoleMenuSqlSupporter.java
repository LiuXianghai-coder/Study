package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleMenuPo;
import org.apache.ibatis.jdbc.SQL;

public class RoleMenuSqlSupporter {

    public String insertSelective(RoleMenuPo record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("tb_role_menu");
        
        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=BIGINT}");
        }
        
        if (record.getMenuId() != null) {
            sql.VALUES("menu_id", "#{menuId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateUserId() != null) {
            sql.VALUES("create_user_id", "#{createUserId,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(RoleMenuPo record) {
        SQL sql = new SQL();
        sql.UPDATE("tb_role_menu");
        
        if (record.getRoleId() != null) {
            sql.SET("role_id = #{roleId,jdbcType=BIGINT}");
        }
        
        if (record.getMenuId() != null) {
            sql.SET("menu_id = #{menuId,jdbcType=BIGINT}");
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