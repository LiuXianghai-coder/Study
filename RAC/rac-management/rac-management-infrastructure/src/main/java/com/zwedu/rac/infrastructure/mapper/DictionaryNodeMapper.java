package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.infrastructure.po.DictionaryNodePo;
import com.zwedu.rac.infrastructure.po.IdNumPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 字典dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DictionaryNodeMapper extends DictionaryNodeBaseMapper {

    /**
     * 查询子功能
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param parentId     父节点ID
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            " and parent_id = #{parentId,jdbcType=BIGINT}  and deleted=0 ",
            "<if test='authInfo != null'>",
            "and ${authInfo.dbFieldName} in (",
            "<foreach item='item' collection='authInfo.authList' separator=','>",
            " #{item,jdbcType=BIGINT} ",
            " </foreach>)",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DictionaryNodePo> listByParentId(@Param("bizLineId") Long bizLineId,
                                          @Param("dictionaryId") Long dictionaryId,
                                          @Param("parentId") Long parentId,
                                          @Param("authInfo") AuthInfo authInfo);

    /**
     * 查询子功能
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param parentPaths  父节点路径
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            " and deleted=0 and (",
            "<foreach item='item' collection='parentPaths' separator='or'>",
            " parent_path LIKE concat(#{item,jdbcType=VARCHAR},'/%') or parent_path = #{item,jdbcType=VARCHAR} ",
            " </foreach>)",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DictionaryNodePo> listByParentPaths(@Param("bizLineId") Long bizLineId,
                                             @Param("dictionaryId") Long dictionaryId,
                                             @Param("parentPaths") Collection<String> parentPaths);

    /**
     * 统计子节点数量
     *
     * @param bizLineId 业务线ID
     * @param idList    节点ID
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "parent_id, count(1) childCount",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            "and deleted=0",
            "and parent_id in (",
            "<foreach collection='idList' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>) group by parent_id",
            "</script>"
    })
    @Results({
            @Result(column = "parent_id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "childCount", property = "childCount", jdbcType = JdbcType.BIGINT),
    })
    List<IdNumPo> countChildByIds(@Param("bizLineId") Long bizLineId, @Param("dictionaryId") Long dictionaryId,
                                  @Param("idList") Set<Long> idList);

    /**
     * 查询业务线下指定英文名的记录
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param enName       英文名
     * @param id           ID
     * @return 列表
     */
    @Select({

            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            "and en_name = #{enName,jdbcType=VARCHAR} and deleted = 0",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DictionaryNodePo queryByEnName(@Param("bizLineId") Long bizLineId, @Param("dictionaryId") Long dictionaryId,
                                   @Param("enName") String enName, @Param("id") Long id);


    /**
     * 查询业务线下指定中文名的记录
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param cnName       中文名
     * @param id           ID
     * @return 列表
     */
    @Select({

            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            "and cn_name = #{cnName,jdbcType=VARCHAR} and deleted = 0",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DictionaryNodePo queryByCnName(@Param("bizLineId") Long bizLineId, @Param("dictionaryId") Long dictionaryId,
                                   @Param("cnName") String cnName, @Param("id") Long id);


    /**
     * 查找字典节点列表
     *
     * @param dictionaryIds 字典ID
     * @return 字典列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where deleted=0 ",
            "and dictionary_id in (",
            "<foreach collection='dictionaryIds' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>) ",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DictionaryNodePo> listByDictionaryIds(@Param("dictionaryIds") List<Long> dictionaryIds);


    /**
     * 查询字典节点
     *
     * @param bizLineId    业务线ID
     * @param dictionaryId 字典ID
     * @param searchVal    检索条件
     * @return 功能列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dictionary_id = #{dictionaryId,jdbcType=BIGINT} ",
            " and parent_path like concat('/-1%') and deleted=0 ",
            "<if test='searchVal != null and searchVal != \"\"'> ",
            "and (en_name like concat(#{searchVal, jdbcType=VARCHAR},'%') ",
            "or cn_name like concat(#{searchVal, jdbcType=VARCHAR},'%')) ",
            "</if>",
            "<if test='authInfo != null'>",
            "and ${authInfo.dbFieldName} in (",
            "<foreach item='item' collection='authInfo.authList' separator=','>",
            " #{item,jdbcType=BIGINT} ",
            " </foreach>)",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DictionaryNodePo> listByDictionaryId(@Param("bizLineId") Long bizLineId,
                                              @Param("dictionaryId") Long dictionaryId,
                                              @Param("searchVal") String searchVal,
                                              @Param("authInfo") AuthInfo authInfo);

    @Select({
            "select",
            "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
            "remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dictionary_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and ",
            "dictionary_id = #{dictionaryId,jdbcType=BIGINT} and",
            "id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dictionary_id", property = "dictionaryId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DictionaryNodePo queryById(@Param("bizLineId") Long bizLineId, @Param("dictionaryId") Long dictionaryId,
                               @Param("id") Long id);


    @Update({
            "<script>",
            "update tb_dictionary_node",
            "set parent_path = CONCAT(#{newParentPath,jdbcType=VARCHAR}, ",
            "SUBSTR(parent_path, LENGTH(#{oldParentPath,jdbcType=VARCHAR}) + 1))",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and dictionary_id = #{dictionaryId,jdbcType=BIGINT}",
            "and id in (",
            "<foreach collection='ids' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"
    })
    void changeNodeWithNewPath(@Param("bizLineId") Long bizLineId,
                               @Param("dictionaryId") Long dictionaryId,
                               @Param("newParentPath") String newParentPath,
                               @Param("oldParentPath") String oldParentPath,
                               @Param("ids") Collection<Long> ids);
}
