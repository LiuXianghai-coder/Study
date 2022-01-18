package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.BizEntityPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 实体dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizEntityMapper extends BizEntityBaseMapper {
    /**
     * 查找可用的数据列表
     *
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_biz_entity",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT}",
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
    List<BizEntityPo> listPage(@Param("bizLineId") Long bizLineId, @Param("searchVal") String searchVal);

    /**
     * 根据英文名查询
     *
     * @param bizLineId 业务线ID
     * @param enName    英文名
     * @return 业务实体PO
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_biz_entity",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and en_name = #{enName,jdbcType=VARCHAR} and deleted=0",
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
    BizEntityPo queryByEnName(@Param("bizLineId") Long bizLineId, @Param("enName") String enName,
                              @Param("id") Long id);


    /**
     * 根据中文名查询
     *
     * @param bizLineId 业务线ID
     * @param cnName    中文名
     * @return 业务实体PO
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_biz_entity",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and cn_name = #{cnName,jdbcType=VARCHAR} and deleted=0",
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
    BizEntityPo queryByCnName(@Param("bizLineId") Long bizLineId, @Param("cnName") String cnName,
                              @Param("id") Long id);

    /**
     * 根据业务线查询
     *
     * @param bizLineId 业务线ID
     * @return 列表
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_biz_entity",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT}"
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
    List<BizEntityPo> listByBizLineId(@Param("bizLineId") Long bizLineId);

    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, remark, create_time, create_user_id, update_time, ",
            "update_user_id, deleted",
            "from tb_biz_entity",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and id = #{id,jdbcType=BIGINT} and deleted = 0"
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
    BizEntityPo queryById(@Param("bizLineId") Long bizLineId, @Param("id") Long id);
}
