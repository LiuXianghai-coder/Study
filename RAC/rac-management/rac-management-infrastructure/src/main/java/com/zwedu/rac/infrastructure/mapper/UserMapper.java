package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.infrastructure.po.UserPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 用户Mapper
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserMapper extends UserBaseMapper {
    /**
     * 查找可用的数据列表
     *
     * @param searchVal
     * @return 列表数据
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, password, id_card_num, mobile_phone, email, gender, biz_line_id, ",
            "decentralized_control_id, status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_user",
            "where deleted = 0 and biz_line_id = #{bizLineId, jdbcType=BIGINT}",
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
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id_card_num", property = "idCardNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile_phone", property = "mobilePhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.INTEGER),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column="decentralized_control_id", property="decentralizedControlId", jdbcType=JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    List<UserPo> listPage(@Param("bizLineId") Long bizLineId, @Param("currentUserId") Long currentUserId,
                          @Param("searchVal") String searchVal,
                          @Param("authInfo") AuthInfo authInfo);

    /**
     * 查询指定英文名的记录
     *
     * @param enName    英文名
     * @param id        ID
     * @return 列表
     */
    @Select({
            "<script>",
            "select",
            "id, en_name, cn_name, password, id_card_num, mobile_phone, email, gender, biz_line_id, ",
            "decentralized_control_id, status, remark, create_time, create_user_id, update_time, update_user_id, deleted",
            "from tb_user",
            "where en_name = #{enName, jdbcType=VARCHAR} and deleted = 0",
            "<if test='id != null'>",
            "and id != #{id,jdbcType=BIGINT}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "en_name", property = "enName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cn_name", property = "cnName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id_card_num", property = "idCardNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mobile_phone", property = "mobilePhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gender", property = "gender", jdbcType = JdbcType.INTEGER),
            @Result(column = "biz_line_id", property = "bizLineId", jdbcType = JdbcType.BIGINT),
            @Result(column="decentralized_control_id", property="decentralizedControlId", jdbcType=JdbcType.BIGINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "create_user_id", property = "createUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "update_time", property = "updateTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "update_user_id", property = "updateUserId", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.INTEGER)
    })
    UserPo queryByEnName(@Param("enName") String enName, @Param("id") Long id);
}
