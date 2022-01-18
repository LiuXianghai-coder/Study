package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleMenuPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface RoleMenuBaseMapper {
    @Delete({
        "delete from tb_role_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_role_menu (role_id, menu_id, ",
        "create_time, create_user_id)",
        "values (#{roleId,jdbcType=BIGINT}, #{menuId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(RoleMenuPo record);

    @InsertProvider(type=RoleMenuSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(RoleMenuPo record);

    @Select({
        "select",
        "id, role_id, menu_id, create_time, create_user_id",
        "from tb_role_menu",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT),
        @Result(column="menu_id", property="menuId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    RoleMenuPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=RoleMenuSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RoleMenuPo record);

    @Update({
        "update tb_role_menu",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "menu_id = #{menuId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleMenuPo record);
}