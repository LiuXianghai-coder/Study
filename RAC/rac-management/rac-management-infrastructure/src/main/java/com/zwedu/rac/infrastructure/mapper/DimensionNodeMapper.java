package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.infrastructure.po.DimensionNodePo;
import com.zwedu.rac.infrastructure.po.IdNumPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 维度节点dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface DimensionNodeMapper extends DimensionNodeBaseMapper {

    /**
     * 查询子功能
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度节点ID
     * @param parentId    父节点ID
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
            " and parent_id = #{parentId,jdbcType=BIGINT}  and deleted=0",
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
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listByParentId(@Param("bizLineId") Long bizLineId,
                                         @Param("dimensionId") Long dimensionId,
                                         @Param("parentId") Long parentId,
                                         @Param("authInfo") AuthInfo authInfo);


    /**
     * 查询子功能
     *
     * @param bizLineId      业务线ID
     * @param dimensionId    维度节点ID
     * @param parentPathList 父节点路径
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
            " and deleted=0 and (",
            "<foreach item='item' collection='parentPathList' separator='or'>",
            " parent_path LIKE concat(#{item,jdbcType=VARCHAR},'/%') or parent_path = #{item,jdbcType=VARCHAR} ",
            " </foreach>)",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listByParentPaths(@Param("bizLineId") Long bizLineId,
                                            @Param("dimensionId") Long dimensionId,
                                            @Param("parentPathList") Collection<String> parentPathList);


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
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
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
    List<IdNumPo> countChildByIds(@Param("bizLineId") Long bizLineId, @Param("dimensionId") Long dimensionId,
                                  @Param("idList") Set<Long> idList);

    /**
     * 查询业务线下指定英文名的记录
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param enName      英文名
     * @param id          ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
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
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DimensionNodePo queryByEnName(@Param("bizLineId") Long bizLineId, @Param("dimensionId") Long dimensionId,
                                  @Param("enName") String enName, @Param("id") Long id);


    /**
     * 查询业务线下指定中文名的记录
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param cnName      中文名
     * @param id          ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
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
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DimensionNodePo queryByCnName(@Param("bizLineId") Long bizLineId, @Param("dimensionId") Long dimensionId,
                                  @Param("cnName") String cnName, @Param("id") Long id);


    /**
     * 查询维度节点
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param searchVal   检索条件
     * @return 功能列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
            " and parent_path like concat('/-1%') and deleted=0 ",
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
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listByDimensionId(@Param("bizLineId") Long bizLineId, @Param("dimensionId") Long dimensionId,
                                            @Param("searchVal") String searchVal);

    @Select({
            "select",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
            "and id = #{id,jdbcType=BIGINT}  and deleted=0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    DimensionNodePo queryById(@Param("bizLineId") Long bizLineId, @Param("dimensionId") Long dimensionId,
                              @Param("id") Long id);

    /**
     * 查询维度节点
     *
     * @param dimensionNodeIds 维度节点ID
     * @return 维度节点列表
     */
    @Select({
            "<script>",
            "id, en_name, cn_name, type, biz_line_id, decentralized_control_id, dimension_id, parent_id, parent_path, ",
            "status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_dimension_node",
            "where deleted=0",
            "and id in (",
            "<foreach collection='dimensionNodeIds' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.BIGINT),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "decentralized_control_id", property = "decentralizedControlId", jdbcType = JdbcType.BIGINT),
            @Result(column = "dimension_id", property = "dimensionId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<DimensionNodePo> listDimensionNodeByIds(@Param("dimensionNodeIds") List<Long> dimensionNodeIds);


    @Update({
            "<script>",
            "update tb_dimension_node",
            "set parent_path = CONCAT(#{newParentPath,jdbcType=VARCHAR}, ",
            "SUBSTR(parent_path, LENGTH(#{oldParentPath,jdbcType=VARCHAR}) + 1))",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and dimension_id = #{dimensionId,jdbcType=BIGINT} ",
            "and id in (",
            "<foreach collection='ids' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"
    })
    void changeNodeWithNewPath(@Param("bizLineId") Long bizLineId,
                               @Param("dimensionId") Long dimensionId,
                               @Param("newParentPath") String newParentPath,
                               @Param("oldParentPath") String oldParentPath,
                               @Param("ids") Collection<Long> ids);
}
