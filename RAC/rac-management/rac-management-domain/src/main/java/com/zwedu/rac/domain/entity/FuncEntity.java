package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.constant.SeparationChar;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.util.UrlMatcher;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.TreeEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.zwedu.rac.domain.common.constant.SystemConstant.LARGE_COLUMN_LENGTH;

/**
 * 功能实体类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class FuncEntity extends TreeEntity {
    /**
     * 功能URL
     */
    private String content;
    /**
     * 策略实体
     */
    private StrategyEntity strategyEntity;

    @Override
    public void validate() {
    }

    @Override
    public void create(Long currentUserId) {
        validate();
        completeForCreate(this, currentUserId);
    }

    /**
     * 新增逻辑 校验和填充默认值
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void create(Long currentUserId, FuncEntity parentNode) {
        create(currentUserId);
        completePath(parentNode);
    }


    /**
     * 新增逻辑 校验和填充默认值
     *
     * @param currentUserId  登录用户ID
     * @param parentNode     父节点信息
     * @param funcEntityList 已经存在功能权限
     */
    public void create(Long currentUserId, FuncEntity parentNode, List<FuncEntity> funcEntityList) {
        // 检查是否有重复的url
        isRepeatUrl(Collections2.collect(funcEntityList, input -> input.getUrls()));
        create(currentUserId);
        // 需要填充对应的节点path
        completePath(parentNode);
    }

    /**
     * 判断产品线中的url路径是否唯一
     */
    public void isRepeatUrl(List<String> sourceUrls) {
        TreeSet<String> sourceUrlSet = new TreeSet<>(sourceUrls);
        for (String url : getUrls()) {
            String matchUrl = UrlMatcher.getInstance().find(sourceUrlSet, url);
            BizAssert.URL_REPEAT_ERROR.isTrue(StringUtils.isEmpty(matchUrl), url, matchUrl);
            sourceUrlSet.add(url);
        }
    }

    /**
     * 修改节点逻辑填充默认值和修改父节点逻辑处理
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void edit(Long currentUserId, FuncEntity parentNode, List<FuncEntity> funcEntityList) {
        isRepeatUrl(Collections2.collect(funcEntityList.stream().filter(input -> !input.getId().equals(this.getId()))
                .collect(Collectors.toList()), input -> input.getUrls()));
        edit(currentUserId);
        completePath(parentNode);
    }

    @Override
    public void edit(Long currentUserId) {
        validate();
        BizAssert.LOOP_TREE_ERROR.notTrue(this.getId().longValue() == this.getParentId().longValue());
        completeForUpdate(this, currentUserId);
    }


    @Override
    public void delete(Long currentUserId) {
        validate();
        completeForDelete(this, currentUserId);
    }

    /**
     * 获取url列表
     *
     * @return url列表
     */
    public Collection<String> getUrls() {
        return Collections2.toStringSet(this.getContent(), SeparationChar.SEMICOLON);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(content, "功能内容");
        BizAssert.DATA_TOO_LONG_4000_ERROR.isTrue(content.length() < LARGE_COLUMN_LENGTH, "功能内容");
        this.content = content;
    }

    public StrategyEntity getStrategyEntity() {
        return strategyEntity;
    }

    public void setStrategyEntity(StrategyEntity strategyEntity) {
        this.strategyEntity = strategyEntity;
    }
}
