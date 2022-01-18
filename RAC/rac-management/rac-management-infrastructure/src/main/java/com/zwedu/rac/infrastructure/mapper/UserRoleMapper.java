package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserRolePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 用户角色Mapper
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserRoleMapper extends UserRoleBaseMapper {
    @Select({
            "select",
            "role_id",
            "from tb_user_role",
            "where user_id = #{userId, jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.BIGINT)
    })
    List<UserRolePo> listUserRoleId(@Param("userId") Long userId);

    @Insert({
            "<script>",
            "insert into tb_user_role (user_id, role_id, create_time, create_user_id)",
            "values ",
            "<foreach collection='roleIds' item='item' index='index' separator=','>",
            " (#{userId,jdbcType=BIGINT}, #{item,jdbcType=BIGINT}, now(), #{currentUserId,jdbcType=BIGINT}) ",
            "</foreach>",
            "</script>"
    })
    void bindRoles(@Param("currentUserId") Long currentUserId, @Param("userId") Long userId,
                   @Param("roleIds") List<Long> roleIds);

    @Delete({
            "delete from tb_user_role",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    void deleteByUserId(@Param("userId") Long userId);
}
