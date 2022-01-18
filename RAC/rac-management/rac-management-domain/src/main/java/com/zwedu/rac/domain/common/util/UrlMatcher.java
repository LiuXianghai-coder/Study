package com.zwedu.rac.domain.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.SortedSet;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * url工具类
 *
 * @author qingchuan
 * @date 2020/12/11
 */
public class UrlMatcher {
    /**
     * 私有化构造
     */
    private UrlMatcher() {
    }

    /**
     * 实例
     */
    private static final UrlMatcher INSTANCE = new UrlMatcher();

    /**
     * 返回实例
     *
     * @return 实例
     */
    public static UrlMatcher getInstance() {
        return INSTANCE;
    }

    /**
     * 匹配路径是否match
     *
     * @param urls url集合
     * @param path 当前路径
     * @return 是否match
     */
    public String find(SortedSet<String> urls, String path) {
        if (CollectionUtils.isEmpty(urls) || StringUtils.isEmpty(path)) {
            return EMPTY;
        }
        SortedSet<String> left = urls.headSet(path + "\0");
        SortedSet<String> right = urls.tailSet(path + "\0");
        // 匹配左子树
        if (left.size() > 0) {
            String before = left.last();
            if (match(before, path)) {
                return before;
            }
        }
        // 匹配右子树
        if (right.size() > 0) {
            String after = right.first();
            if (match(after, path)) {
                return after;
            }
        }
        return EMPTY;
    }

    /**
     * 检查路径是否匹配
     *
     * @param path   源字符串
     * @param pattern 目标字符串
     * @return 是否匹配
     */
    public boolean match(String pattern, String path) {
        PathMatcher matcher = new AntPathMatcher();
        if (matcher.isPattern(pattern)) {
            return matcher.match(pattern, path);
        }
        return pattern.equals(path);
    }

}
