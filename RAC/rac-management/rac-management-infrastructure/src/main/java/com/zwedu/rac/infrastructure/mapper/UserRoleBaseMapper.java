package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserRolePo;
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

public interface UserRoleBaseMapper {
    @Delete({
        "delete from tb_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_user_role (user_id, role_id, ",
        "create_time, create_user_id)",
        "values (#{userId,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UserRolePo record);

    @InsertProvider(type=UserRoleSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UserRolePo record);

    @Select({
        "select",
        "id, user_id, role_id, create_time, create_user_id",
        "from tb_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    UserRolePo selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserRoleSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserRolePo record);

    @Update({
        "update tb_user_role",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "role_id = #{roleId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRolePo record);
}