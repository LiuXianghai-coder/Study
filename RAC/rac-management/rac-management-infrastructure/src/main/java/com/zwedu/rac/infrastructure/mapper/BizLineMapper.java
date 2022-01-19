package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.infrastructure.po.BizLinePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 业务线dao层接口
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface BizLineMapper extends BizLineBaseMapper {
    /**
     * 查找可用的数据列表
     *
     * @param searchVal 检索值
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, decentralized_control, decentralized_control_en_name, remark, create_time, create_user_id, update_time, update_user_id, ",
            "deleted",
            "from tb_biz_line",
            "where deleted = 0 ",
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
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "decentralized_control", property = "decentralizedControl", jdbcType = JdbcType.INTEGER),
            @Result(column = "decentralized_control_en_name", property = "decentralizedControlEnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.TINYINT)
    })
    List<BizLinePo> listPage(@Param("searchVal") String searchVal,
                             @Param("authInfo") AuthInfo authInfo);

    /**
     * 查询授权的业务线列表
     *
     * @return 业务线列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, decentralized_control, decentralized_control_en_name, remark, create_time, create_user_id, update_time, update_user_id, ",
            "deleted",
            "from tb_biz_line",
            "where deleted = 0 ",
            "<if test='authInfo != null'>",
            "and ${authInfo.dbFieldName} in (",
            "<foreach item='item' collection='authInfo.authList' separator=','>",
            " #{item,jdbcType=BIGINT} ",
            " </foreach>)",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "decentralized_control", property = "decentralizedControl", jdbcType = JdbcType.INTEGER),
            @Result(column = "decentralized_control_en_name", property = "decentralizedControlEnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.TINYINT)
    })
    List<BizLinePo> listAuthBizLine(@Param("authInfo") AuthInfo authInfo);


    /**
     * 根据英文名查询
     *
     * @param enName 英文名
     * @return 业务实体PO
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, decentralized_control, decentralized_control_en_name, remark, create_time, create_user_id, update_time, update_user_id, ",
            "deleted",
            "from tb_biz_line",
            "where deleted = 0 ",
            "and en_name = #{enName,jdbcType=VARCHAR} ",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "decentralized_control", property = "decentralizedControl", jdbcType = JdbcType.INTEGER),
            @Result(column = "decentralized_control_en_name", property = "decentralizedControlEnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.TINYINT)
    })
    BizLinePo queryByEnName(@Param("enName") String enName, @Param("id") Long id);


    /**
     * 根据中文名查询
     *
     * @param cnName 中文名
     * @return 业务实体PO
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, decentralized_control, decentralized_control_en_name, remark, create_time, create_user_id, update_time, update_user_id, ",
            "deleted",
            "from tb_biz_line",
            "where deleted = 0 ",
            "and cn_name = #{cnName,jdbcType=VARCHAR} ",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "decentralized_control", property = "decentralizedControl", jdbcType = JdbcType.INTEGER),
            @Result(column = "decentralized_control_en_name", property = "decentralizedControlEnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.TINYINT)
    })
    BizLinePo queryByCnName(@Param("cnName") String cnName, @Param("id") Long id);
}
