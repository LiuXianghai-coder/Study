package com.zwedu.rac.sdk.provider;

import com.zwedu.rac.sdk.rdo.DimensionNodeRdo;
import com.zwedu.rac.sdk.rdo.ExtPropertyRdo;
import com.zwedu.rac.sdk.rdo.FuncRdo;
import com.zwedu.rac.sdk.rdo.MenuRdo;
import com.zwedu.rac.sdk.rpo.DimensionAuthRpo;
import com.zwedu.rac.sdk.rpo.FuncAuthRpo;
import com.zwedu.rac.sdk.rpo.MenuRpo;
import com.zwedu.rac.sdk.rpo.UserExtPropertyRpo;

import java.util.List;

/**
 * 权限服务接口类
 *
 * @author qingchuan
 * @date 2020/12/11
 */
public interface AuthProvider {
    /**
     * 获取业务线下用户功能URL授权列表
     *
     * @param rpo 功能URL查询参数
     * @return 用户功能授权URL
     */
    List<FuncRdo> listFuncAuth(FuncAuthRpo rpo);

    /**
     * 获取业务线下用户绑定菜单树
     *
     * @param rpo 菜单查询参数
     * @return 用户绑定菜单
     */
    List<MenuRdo> listMenu(MenuRpo rpo);

    /**
     * 查询维度节点
     *
     * @return 维度节点列表
     */
    List<DimensionNodeRdo> listDimensionNodeByUser(DimensionAuthRpo rpo);

    /**
     * 查询维度节点以及其子节点
     *
     * @return 维度节点列表
     */
    List<DimensionNodeRdo> listDimensionNodeWithChildByUser(DimensionAuthRpo rpo);

    /**
     * 查询用户扩展属性列表
     *
     * @param rpo 扩展属性查询参数
     * @return 用户扩展属性列表
     */
    List<ExtPropertyRdo> listExtPropertyByUser(UserExtPropertyRpo rpo);
}
