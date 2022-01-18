package com.zwedu.rac.interfaces.rpc;

import com.zwedu.rac.application.service.AuthAppService;
import com.zwedu.rac.sdk.provider.AuthProvider;
import com.zwedu.rac.sdk.rdo.DimensionNodeRdo;
import com.zwedu.rac.sdk.rdo.ExtPropertyRdo;
import com.zwedu.rac.sdk.rdo.FuncRdo;
import com.zwedu.rac.sdk.rdo.MenuRdo;
import com.zwedu.rac.sdk.rpo.DimensionAuthRpo;
import com.zwedu.rac.sdk.rpo.FuncAuthRpo;
import com.zwedu.rac.sdk.rpo.MenuRpo;
import com.zwedu.rac.sdk.rpo.UserExtPropertyRpo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能权限dubbo实现
 *
 * @author qingchuan
 * @date 2020/12/11
 */
@Service
@DubboService
public class AuthDubProvider implements AuthProvider {
    @Resource
    private AuthAppService authAppService;

    @Override
    public List<FuncRdo> listFuncAuth(FuncAuthRpo rpo) {
        return authAppService.listFuncAuth(rpo);
    }

    @Override
    public List<MenuRdo> listMenu(MenuRpo rpo) {
        return authAppService.listMenu(rpo);
    }

    @Override
    public List<DimensionNodeRdo> listDimensionNodeByUser(DimensionAuthRpo rpo) {
        return authAppService.listDimensionNodeByUser(rpo);
    }

    @Override
    public List<DimensionNodeRdo> listDimensionNodeWithChildByUser(DimensionAuthRpo rpo) {
        return authAppService.listDimensionNodeWithChildByUser(rpo);
    }

    @Override
    public List<ExtPropertyRdo> listExtPropertyByUser(UserExtPropertyRpo rpo) {
        return authAppService.listExtPropertyByUser(rpo);
    }


}
