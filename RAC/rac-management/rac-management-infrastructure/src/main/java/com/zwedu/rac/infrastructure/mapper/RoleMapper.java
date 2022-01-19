package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.RolePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 角色dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface RoleMapper extends RoleBaseMapper {
    /**
     * 查找用户对应的授权角色ID
     *
     * @return 角色列表数据
     */
    @Select({
            "select",
            "a.id id, a.en_name en_name, a.cn_name cn_name, a.biz_line_id biz_line_id, a.remark remark, ",
            "a.create_time create_time, a.create_user_id create_user_id, a.update_time update_time, ",
            "a.update_user_id update_user_id, a.deleted deleted",
            "from tb_role a join tb_user_role b on a.id = b.role_id ",
            "where b.user_id = #{userId,jdbcType=BIGINT} and a.biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and a.deleted = 0 "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<RolePo> listByUserId(@Param("bizLineId") Long bizLineId, @Param("userId") Long userId);

    /**
     * 查找业务线对应的授权角色ID
     *
     * @return 角色列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_role",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "<if test='searchVal != null and searchVal != \"\"'> ",
            "and (en_name like concat(#{searchVal, jdbcType=VARCHAR},'%') ",
            "or cn_name like concat(#{searchVal, jdbcType=VARCHAR},'%')) ",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<RolePo> listPage(@Param("bizLineId") Long bizLineId, @Param("searchVal") String searchVal);


    /**
     * 查找用户授权角色列表
     *
     * @return 角色列表数据
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_role",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT}",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<RolePo> listByBizLineId(@Param("bizLineId") Long bizLineId);

    /**
     * 查询业务线下指定英文名的记录
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @param id        ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_role",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and en_name = #{enName,jdbcType=VARCHAR} and deleted = 0 ",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    RolePo queryByEnName(@Param("bizLineId") Long bizLineId,
                         @Param("enName") String enName, @Param("id") Long id);


    /**
     * 查询业务线下指定中文名的记录
     *
     * @param bizLineId 业务线ID
     * @param cnName    中文名
     * @param id        ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_role",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and cn_name = #{cnName,jdbcType=VARCHAR} and deleted = 0 ",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    RolePo queryByCnName(@Param("bizLineId") Long bizLineId,
                         @Param("cnName") String cnName, @Param("id") Long id);

    /**
     * 根据ID查询
     *
     * @param bizLineId 业务线ID
     * @param id        ID
     * @return 角色
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_role",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} and id = #{id,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    RolePo queryById(@Param("bizLineId") Long bizLineId, @Param("id") Long id);
}
