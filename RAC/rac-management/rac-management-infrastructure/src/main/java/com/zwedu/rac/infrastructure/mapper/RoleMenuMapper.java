package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleMenuPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;

/**
 * 角色菜单dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleMenuMapper extends RoleMenuBaseMapper {

    @Select({
            "select",
            "id, role_id, menu_id, create_time, create_user_id",
            "from tb_role_menu",
            "where role_id = #{roleId,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT),
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.BIGINT),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT)
    })
    List<RoleMenuPo> listByRoleId(@Param("roleId") Long roleId);


    @Select({
            "select",
            "a.menu_id",
            "from tb_role_menu a join tb_role b on a.role_id = b.id ",
            "where a.role_id = #{roleId, jdbcType=BIGINT} and b.deleted=0 ",
            "and b.biz_line_id = #{bizLineId, jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "menu_id", property = "menuId", jdbcType = JdbcType.BIGINT)
    })
    List<RoleMenuPo> listBindMenuId(@Param("bizLineId") Long bizLineId, @Param("roleId") Long roleId);

    /**
     * 绑定接口
     *
     * @param currentUserId 当前登录用户ID
     * @param roleId        角色ID
     * @param menuIds       菜单ID列表
     */
    @Insert({
            "<script>",
            "insert into tb_role_menu (role_id, menu_id, create_time, create_user_id)",
            "values ",
            "<foreach collection='menuIds' index='index'  item='item' separator=','>",
            " (#{roleId,jdbcType=BIGINT}, #{item,jdbcType=BIGINT},  ",
            "now(), #{currentUserId,jdbcType=BIGINT}) ",
            "</foreach>",
            "</script>"
    })
    void bindMenu(@Param("currentUserId") Long currentUserId, @Param("roleId") Long roleId,
                  @Param("menuIds") Collection<Long> menuIds);


    /**
     * 删除角色菜单关系
     *
     * @param roleId 角色ID
     */
    @Delete({
            "delete from tb_role_menu",
            "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    void deleteByRoleId(@Param("roleId") Long roleId);
}
