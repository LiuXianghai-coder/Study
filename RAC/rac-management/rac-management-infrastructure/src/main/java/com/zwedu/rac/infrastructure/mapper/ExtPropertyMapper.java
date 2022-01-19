package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.ExtPropertyPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 扩展属性dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface ExtPropertyMapper extends ExtPropertyBaseMapper {
    /**
     * 查找可用的数据列表
     *
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted, dictionary_id",
            "from tb_ext_property",
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
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT)
    })
    List<ExtPropertyPo> listPage(@Param("bizLineId") Long bizLineId, @Param("searchVal") String searchVal);

    /**
     * 查询业务线下指定英文名的记录
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @param enName      英文名
     * @param id          ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted, dictionary_id",
            "from tb_ext_property",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and biz_entity_id = #{bizEntityId,jdbcType=BIGINT} ",
            "and en_name = #{enName,jdbcType=VARCHAR}  and deleted=0",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT)
    })
    ExtPropertyPo queryByEnName(@Param("bizLineId") Long bizLineId, @Param("bizEntityId") Long bizEntityId,
                                @Param("enName") String enName, @Param("id") Long id);


    /**
     * 查询业务线下指定中文名的记录
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @param cnName      中文名
     * @param id          ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted, dictionary_id",
            "from tb_ext_property",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and biz_entity_id = #{bizEntityId,jdbcType=BIGINT} ",
            "and cn_name = #{cnName,jdbcType=VARCHAR}  and deleted=0",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT)
    })
    ExtPropertyPo queryByCnName(@Param("bizLineId") Long bizLineId, @Param("bizEntityId") Long bizEntityId,
                                @Param("cnName") String cnName, @Param("id") Long id);


    /**
     * 查询业务线对应记录
     *
     * @param bizLineId 业务线ID
     * @return 列表
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted, dictionary_id",
            "from tb_ext_property",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} deleted=0 "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT)
    })
    List<ExtPropertyPo> listByBizLineId(@Param("bizLineId") Long bizLineId);


    /**
     * 查询业务线对应记录
     *
     * @param bizLineId   业务线ID
     * @param bizEntityId 业务实体ID
     * @return 列表
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, dictionary_id, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_ext_property",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and ",
            "biz_entity_id = #{bizEntityId,jdbcType=BIGINT} and deleted=0 "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<ExtPropertyPo> listByBizEntityId(@Param("bizLineId") Long bizLineId,
                                          @Param("bizEntityId") Long bizEntityId);

    /**
     * 根据ID查询扩展属性
     *
     * @param ids id列表
     * @return 扩展属性
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted, dictionary_id",
            "from tb_ext_property",
            "where deleted = 0 and id in ( ",
            "<foreach collection='ids' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT} ",
            "</foreach>)",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT)
    })
    List<ExtPropertyPo> listByIds(@Param("ids") List<Long> ids);

    @Select({
            "select",
            "id, en_name, cn_name, biz_entity_id, biz_line_id, type, dictionary_id, default_value, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_ext_property",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and ",
            "biz_entity_id = #{bizEntityId,jdbcType=BIGINT} and deleted=0 ",
            " and id = #{id,jdbcType=BIGINT} "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_entity_id", property = "bizEntityId", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "default_value", property = "defaultValue", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    ExtPropertyPo queryById(@Param("bizLineId") Long bizLineId,
                            @Param("bizEntityId") Long bizEntityId, @Param("id") Long id);
}
