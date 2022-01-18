package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.ExtPropertyPo;
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

public interface ExtPropertyBaseMapper {
    @Delete({
        "delete from tb_ext_property",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_ext_property (en_name, cn_name, ",
        "biz_entity_id, biz_line_id, ",
        "type, dictionary_id, ",
        "default_value, remark, ",
        "create_time, create_user_id, ",
        "update_time, update_user_id, ",
        "deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{bizEntityId,jdbcType=BIGINT}, #{bizLineId,jdbcType=BIGINT}, ",
        "#{type,jdbcType=INTEGER}, #{dictionaryId,jdbcType=BIGINT}, ",
        "#{defaultValue,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{updateUserId,jdbcType=BIGINT}, ",
        "#{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(ExtPropertyPo record);

    @InsertProvider(type=ExtendPropertySqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(ExtPropertyPo record);

    @Select({
        "select",
        "id, en_name, cn_name, biz_entity_id, biz_line_id, type, dictionary_id, default_value, ",
        "remark, create_time, create_user_id, update_time, update_user_id, deleted",
        "from tb_ext_property",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_entity_id", property="bizEntityId", jdbcType=JdbcType.BIGINT),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="dictionary_id", property="dictionaryId", jdbcType=JdbcType.BIGINT),
        @Result(column="default_value", property="defaultValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    ExtPropertyPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=ExtendPropertySqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ExtPropertyPo record);

    @Update({
        "update tb_ext_property",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "biz_entity_id = #{bizEntityId,jdbcType=BIGINT},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "dictionary_id = #{dictionaryId,jdbcType=BIGINT},",
          "default_value = #{defaultValue,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ExtPropertyPo record);
}