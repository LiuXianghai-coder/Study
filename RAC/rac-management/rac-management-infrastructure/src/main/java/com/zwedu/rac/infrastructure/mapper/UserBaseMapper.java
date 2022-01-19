package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.UserPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserBaseMapper {
    @Delete({
        "delete from tb_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_user (en_name, cn_name, ",
        "password, id_card_num, ",
        "mobile_phone, email, ",
        "gender, biz_line_id, ",
        "decentralized_control_id, status, ",
        "remark, create_time, ",
        "create_user_id, update_time, ",
        "update_user_id, deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{idCardNum,jdbcType=VARCHAR}, ",
        "#{mobilePhone,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{gender,jdbcType=INTEGER}, #{bizLineId,jdbcType=BIGINT}, ",
        "#{decentralizedControlId,jdbcType=BIGINT}, #{status,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=BIGINT}, #{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(UserPo record);

    @InsertProvider(type=UserSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(UserPo record);

    @Select({
        "select",
        "id, en_name, cn_name, password, id_card_num, mobile_phone, email, gender, biz_line_id, ",
        "decentralized_control_id, status, remark, create_time, create_user_id, update_time, ",
        "update_user_id, deleted",
        "from tb_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_card_num", property="idCardNum", jdbcType=JdbcType.VARCHAR),
        @Result(column="mobile_phone", property="mobilePhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.INTEGER),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="decentralized_control_id", property="decentralizedControlId", jdbcType=JdbcType.BIGINT),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    UserPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserPo record);

    @Update({
        "update tb_user",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "id_card_num = #{idCardNum,jdbcType=VARCHAR},",
          "mobile_phone = #{mobilePhone,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=INTEGER},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "decentralized_control_id = #{decentralizedControlId,jdbcType=BIGINT},",
          "status = #{status,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserPo record);
}