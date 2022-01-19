package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RoleFuncPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;

/**
 * 角色功能dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleFuncMapper extends RoleFuncBaseMapper {

    @Select({
            "select",
            "func_id",
            "from tb_role_func",
            "where role_id = #{roleId, jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "func_id", property = "funcId", jdbcType = JdbcType.BIGINT)
    })
    List<RoleFuncPo> listByRoleId(@Param("roleId") Long roleId);


    @Select({
            "select",
            "func_id,strategy_id",
            "from tb_role_func a join tb_role b on a.role_id = b.id ",
            "where a.role_id = #{roleId, jdbcType=BIGINT} and b.deleted=0 ",
            "and b.biz_line_id = #{bizLineId, jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "func_id", property = "funcId", jdbcType = JdbcType.BIGINT),
            @Result(column = "strategy_id", property = "strategyId", jdbcType = JdbcType.BIGINT)
    })
    List<RoleFuncPo> listAuth(@Param("bizLineId") Long bizLineId, @Param("roleId") Long roleId);

    /**
     * 绑定接口
     *
     * @param currentUserId 当前登录用户ID
     * @param roleId        角色ID
     * @param funcIds       功能ID列表
     * @param strategyId    策略ID
     */
    @Insert({
            "<script>",
            "insert into tb_role_func (role_id, func_id, strategy_id, create_time, create_user_id)",
            "values ",
            "<foreach collection='funcIds' index='index'  item='item' separator=','>",
            " (#{roleId,jdbcType=BIGINT}, #{item,jdbcType=BIGINT}, #{strategyId,jdbcType=BIGINT}, ",
            "now(), #{currentUserId,jdbcType=BIGINT}) ",
            "</foreach>",
            "</script>"
    })
    void bindAuth(@Param("currentUserId") Long currentUserId, @Param("roleId") Long roleId,
                   @Param("funcIds") Collection<Long> funcIds, @Param("strategyId") Long strategyId);

    /**
     * 删除角色功能关系
     *
     * @param roleId 角色ID
     */
    @Delete({
            "delete from tb_role_func",
            "where role_id = #{roleId,jdbcType=BIGINT}"
    })
    void deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 解绑角色功能
     *
     * @param roleId     角色ID
     * @param funcIds    功能ID列表
     * @param strategyId 策略ID
     */
    @Delete({
            "<script>",
            "delete from tb_role_func",
            "where role_id = #{roleId,jdbcType=BIGINT} and func_id in (",
            "<foreach collection='funcIds' index='index'  item='item' separator=','>",
            " #{item,jdbcType=BIGINT} ",
            "</foreach>) and strategy_id = #{strategyId,jdbcType=BIGINT}",
            "</script>"
    })
    void unbindAuth(@Param("roleId") Long roleId,
                    @Param("funcIds") Collection<Long> funcIds, @Param("strategyId") Long strategyId);
}
