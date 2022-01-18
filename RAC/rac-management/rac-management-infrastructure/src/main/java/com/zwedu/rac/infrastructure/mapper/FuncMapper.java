package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.FuncExtPo;
import com.zwedu.rac.infrastructure.po.FuncPo;
import com.zwedu.rac.infrastructure.po.IdNumPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 资源dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface FuncMapper extends FuncBaseMapper {
    /**
     * 查找角色对应的授权功能列表
     *
     * @return 功能列表数据
     */
    @Select({
            "<script>",
            "select",
            "a.id id, a.en_name en_name, a.cn_name cn_name, a.biz_line_id biz_line_id, a.content content, ",
            "a.parent_id parent_id, a.parent_path parent_path, a.remark remark, ",
            "a.create_time create_time, a.create_user_id create_user_id, a.update_time update_time, ",
            "a.update_user_id update_user_id, a.deleted deleted, b.strategy_id",
            "from tb_func a join tb_role_func b on a.id = b.func_id ",
            "where a.biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and a.deleted = 0 and b.role_id in (",
            "<foreach collection='roleIds' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"

    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "strategy_id", property = "strategyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncExtPo> listByRoleIds(@Param("bizLineId") Long bizLineId,
                                  @Param("roleIds") Collection<Long> roleIds);

    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @param parentId  父节点ID
     * @return 功能列表数据
     */
    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and parent_id = #{parentId, jdbcType=BIGINT}",
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncPo> listByParentId(@Param("bizLineId") Long bizLineId,
                                @Param("parentId") Long parentId);


    /**
     * 查询子功能
     *
     * @param bizLineId      业务线ID
     * @param parentPathList 父节点路径
     * @return 功能列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
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
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncPo> listByParentPaths(@Param("bizLineId") Long bizLineId,
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
            "from tb_func",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
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
    List<IdNumPo> countChildByIds(@Param("bizLineId") Long bizLineId, @Param("idList") Set<Long> idList);

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
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
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
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    FuncPo queryByEnName(@Param("bizLineId") Long bizLineId,
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
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
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
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    FuncPo queryByCnName(@Param("bizLineId") Long bizLineId,
                         @Param("cnName") String cnName, @Param("id") Long id);

    /**
     * 根据ID查询
     *
     * @param idList 功能IDs
     * @return 功能列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark,",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and id in (",
            "<foreach collection='idList' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>) ",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncPo> listByIds(@Param("bizLineId") Long bizLineId, @Param("idList") Collection<Long> idList);

    /**
     * 查询子功能
     *
     * @param bizLineId 业务线ID
     * @param searchVal 检索条件
     * @return 功能列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
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
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncPo> listByBizLineId(@Param("bizLineId") Long bizLineId, @Param("searchVal") String searchVal);

    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and deleted = 0",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<FuncPo> listBizLineAll(@Param("bizLineId") Long bizLineId);

    @Select({
            "select",
            "id, en_name, cn_name, biz_line_id, content, parent_id, parent_path, remark, ",
            "create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_func",
            "where biz_line_id = #{bizLineId,jdbcType=BIGINT} and id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.BIGINT),
            @Result(column = "parent_path", property = "parentPath", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    FuncPo queryById(@Param("bizLineId") Long bizLineId, @Param("id") Long id);

    @Update({
            "<script>",
            "update tb_func",
            "set parent_path = CONCAT(#{newParentPath,jdbcType=VARCHAR}, ",
            "SUBSTR(parent_path, LENGTH(#{oldParentPath,jdbcType=VARCHAR}) + 1))",
            "where deleted = 0 and biz_line_id = #{bizLineId,jdbcType=BIGINT} ",
            "and id in (",
            "<foreach collection='ids' item='item' index='index' separator=','>",
            " #{item,jdbcType=BIGINT}",
            "</foreach>)",
            "</script>"
    })
    void changeNodeWithNewPath(@Param("bizLineId") Long bizLineId,
                               @Param("newParentPath") String newParentPath,
                               @Param("oldParentPath") String oldParentPath,
                               @Param("ids") Collection<Long> ids);
}
