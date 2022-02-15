package com.zwedu.rac.application.strategy;

import com.zwedu.rac.application.service.UserAppService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据访问策略处理器测试
 *
 * @author qingchuan
 * @date 2020/12/25
 */
public class DataAccessStrategyHandlerTest {
    @Resource
    UserAppService userAppService;

    @Test
    public void testUserPropertyAccess() {
//        StrategyRdo strategyRdo = new StrategyRdo();
//        strategyRdo.setExpression("#UserProperty[Ext,AuthOrgId,User[Ext,OrgId]]");
//        UserRdo userRdo = new UserRdo();
//        userRdo.setExtData(ImmutableMap.of("AdminBizLineId", "1,2,3"));
//        RacContext racContext = RacContext.builder().userRdo(userRdo)
//                .paramMap(ImmutableMap.of("bizLineId", "1")).build();
//
//        StrategyInfo strategyInfo = ;
//        boolean hasAuth = DataAccessStrategyHandlerBuilder.instance(2).hasAuth(strategyInfo, racContext);
//        Assert.assertTrue(hasAuth);
        System.out.println(userAppService);
    }

    @Test
    public void testParse(){
        Pattern pattern = Pattern.compile("#UserProperty\\[(\\w+?),(\\w+?),(\\w+?)\\[(\\w+?),(\\w+?)\\]\\]");
        Matcher matcher = pattern.matcher("#UserProperty[Ext,AuthOrgId,User[Ext,OrgId]]");
        if (!matcher.find()) {
            System.out.println(1);
            return;
        }
        // 获取用户属性类型
        String propertyType = matcher.group(1);
        System.out.println(propertyType);
        // 匹配对应的属性名称
        String propertyName = matcher.group(2);
        System.out.println(propertyName);
        // 匹配对应的属性名称
        String entityEnName = matcher.group(3);
        System.out.println(entityEnName);
        // 匹配对应的属性名称
        String entityPropertyType = matcher.group(4);
        System.out.println(entityPropertyType);
        // 匹配对应的属性名称
        String entityPropertyName = matcher.group(5);
        System.out.println(entityPropertyType);
    }
}
