package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleFuncPo;
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

public interface RoleFuncBaseMapper {
    @Delete({
        "delete from tb_role_func",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_role_func (role_id, func_id, ",
        "strategy_id, create_time, ",
        "create_user_id)",
        "values (#{roleId,jdbcType=BIGINT}, #{funcId,jdbcType=BIGINT}, ",
        "#{strategyId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(RoleFuncPo record);

    @InsertProvider(type=RoleFuncSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(RoleFuncPo record);

    @Select({
        "select",
        "id, role_id, func_id, strategy_id, create_time, create_user_id",
        "from tb_role_func",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.BIGINT),
        @Result(column="func_id", property="funcId", jdbcType=JdbcType.BIGINT),
        @Result(column="strategy_id", property="strategyId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT)
    })
    RoleFuncPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=RoleFuncSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RoleFuncPo record);

    @Update({
        "update tb_role_func",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "func_id = #{funcId,jdbcType=BIGINT},",
          "strategy_id = #{strategyId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RoleFuncPo record);
}