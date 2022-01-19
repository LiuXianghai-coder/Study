package com.zwedu.rac.domain.repository;


import com.zwedu.rac.domain.entity.DimensionNodeEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 维度节点
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface DimensionNodeRepository {
    /**
     * 查询业务线对应的维度节点
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param parentId    维度节点ID
     * @return 维度节点列表
     */
    List<DimensionNodeEntity> listByParentId(Long bizLineId, Long dimensionId, Long parentId);


    /**
     * 查询业务线对应的维度节点
     *
     * @param bizLineId      业务线ID
     * @param dimensionId    维度ID
     * @param parentPathList 父路径列表
     * @return 维度节点列表
     */
    List<DimensionNodeEntity> listByParentPaths(Long bizLineId, Long dimensionId, Collection<String> parentPathList);

    /**
     * 根据业务线查询维度节点
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param searchVal   检索值
     * @return 维度节点列表
     */
    List<DimensionNodeEntity> listByDimensionId(Long bizLineId, Long dimensionId, String searchVal);

    /**
     * 根据ID查询实体信息
     *
     * @param id ID
     * @return 实体信息
     */
    DimensionNodeEntity queryById(Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param enName      英文名
     * @param id          数据ID
     * @return true or false
     */
    Boolean hasSameEnName(Long bizLineId, Long dimensionId, String enName, Long id);

    /**
     * 是否有重名的数据
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param cnName      中文名
     * @param id          数据ID
     * @return true or false
     */
    Boolean hasSameCnName(Long bizLineId, Long dimensionId, String cnName, Long id);

    /**
     * 新增维度节点
     *
     * @param record 维度节点实体
     */
    void insert(DimensionNodeEntity record);

    /**
     * 更新维度节点
     *
     * @param record 维度节点实体
     */
    void edit(DimensionNodeEntity record);

    /**
     * 删除维度节点
     *
     * @param record 维度节点实体
     */
    void delete(DimensionNodeEntity record);

    /**
     * 获取维度节点信息
     *
     * @param bizLineId   业务线ID
     * @param dimensionId 维度ID
     * @param id          维度节点ID
     * @return 维度节点信息
     */
    DimensionNodeEntity queryById(Long bizLineId, Long dimensionId, Long id);

    /**
     * 绑定主题节点和客体节点
     *
     * @param currentUserId 当前用户
     * @param subjectNodeId 主体节点ID
     * @param objectNodeId  客体节点ID
     */
    void bindObjectNode(Long currentUserId, Long subjectNodeId, Long objectNodeId);

    /**
     * 解绑主题节点和客体节点
     *
     * @param subjectNodeId 主体节点ID
     * @param objectNodeId  客体节点ID
     */
    void unbindObjectNode(Long subjectNodeId, Long objectNodeId);

    /**
     * 获取客体节点ID
     *
     * @param subjectNodeId 主体节点ID
     * @return 客体节点ID
     */
    Long queryObjectNodeId(Long subjectNodeId);

    /**
     * 根据维度节点ID查询
     *
     * @param dimensionNodeIds 维度节点ID列表
     * @return 维度节点列表
     */
    List<DimensionNodeEntity> listDimensionNodeByIds(List<Long> dimensionNodeIds);


    /**
     * 更新节点的parent path
     *
     * @param bizLineId     业务线ID
     * @param dimensionId   维度ID
     * @param newParentPath 节点的新的父路径前缀
     * @param oldParentPath 节点的老的父路径前缀
     * @param ids           节点ID列表
     */
    void changeNodeWithNewPath(Long bizLineId, Long dimensionId,
                               String newParentPath, String oldParentPath, Set<Long> ids);

}
