package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.ExtData;
import com.zwedu.rac.infrastructure.po.ExtDataPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 扩展数据dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtDataMapper extends ExtDataBaseMapper {
    /**
     * 查询扩展数据
     *
     * @param bizLineId     业务线ID
     * @param extPropertyId 扩展属性ID
     * @param bizDataId     业务数据ID
     * @return 扩展数据
     */
    @Select({
            "select",
            "id, ext_property_id, biz_data_id, biz_line_id, value, remark, create_time, create_user_id, ",
            "update_time, update_user_id, deleted",
            "from tb_ext_data",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and ext_property_id = #{extPropertyId,jdbcType=BIGINT}",
            " and biz_data_id = #{bizDataId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ext_property_id", property = "extPropertyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_data_id", property = "bizDataId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    ExtDataPo queryExtData(@Param("bizLineId") Long bizLineId, @Param("extPropertyId") Long extPropertyId,
                           @Param("bizDataId") Long bizDataId);

    /**
     * 删除扩展数据
     *
     * @param bizLineId     业务线ID
     * @param extPropertyId 扩展属性ID
     * @param bizDataId     业务数据ID
     */
    @Delete({
            "delete from tb_ext_data",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and ext_property_id = #{extPropertyId,jdbcType=BIGINT}",
            " and biz_data_id = #{bizDataId,jdbcType=BIGINT}"
    })
    void dropExtProperty(@Param("bizLineId") Long bizLineId, @Param("extPropertyId") Long extPropertyId,
                         @Param("bizDataId") Long bizDataId);

    /**
     * 查询扩展属性
     *
     * @param bizLineId 业务线
     * @param bizDataId 用户ID
     * @return 扩展属性值
     */
    @Select({
            "select",
            "id, ext_property_id, biz_data_id, biz_line_id, value, remark, create_time, create_user_id, ",
            "update_time, update_user_id, deleted",
            "from tb_ext_data",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            " and biz_data_id = #{bizDataId,jdbcType=BIGINT}  and deleted = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "ext_property_id", property = "extPropertyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_data_id", property = "bizDataId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<ExtDataPo> listExtProperty(@Param("bizLineId") Long bizLineId, @Param("bizDataId") Long bizDataId);

    /**
     * 扩展属性数据
     *
     * @param bizEntityId 业务实体ID
     * @param bizDataId   业务数据
     * @return 扩展数据列表
     */
    @Select({
            "select",
            "a.id, a.ext_property_id, a.biz_data_id, a.biz_line_id, a.value, a.remark, a.create_time, ",
            "a.create_user_id,a.update_time, a.update_user_id, a.deleted, b.en_name name",
            "from tb_ext_data a join tb_ext_property b on a.ext_property_id = b.id ",
            "where b.biz_entity_id = #{bizEntityId,jdbcType=BIGINT} ",
            " and a.biz_data_id = #{bizDataId,jdbcType=BIGINT}  and a.deleted = 0 and b.deleted=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ext_property_id", property = "extPropertyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_data_id", property = "bizDataId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<ExtData> listDataByBizId(@Param("bizEntityId") Long bizEntityId, @Param("bizDataId") Long bizDataId);
}
