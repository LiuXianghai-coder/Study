package com.zwedu.rac.domain.common.constant;

/**
 * 系统常量
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface SystemConstant {
    /**
     * 默认ID值
     */
    Long DEFAULT_ID_VALUE = -1L;
    /**
     * 默认根节点PATH
     */
    String ROOT_PATH = "/-1";
    /**
     * 标准长度列大小
     */
    Integer NORMAL_COLUMN_LENGTH = 50;
    /**
     * 小长度列大小
     */
    Integer MIN_COLUMN_LENGTH = 255;
    /**
     * 中等长度列大小
     */
    Integer MIDDLE_COLUMN_LENGTH = 1000;
    /**
     * 巨型长度列大小
     */
    Integer LARGE_COLUMN_LENGTH = 4000;
    /**
     * rac业务线名称
     */
    String BIZ_LINE_RAC = "rac";
    /**
     * 用户实体英文名
     */
    String ENTITY_USER = "User";
}
