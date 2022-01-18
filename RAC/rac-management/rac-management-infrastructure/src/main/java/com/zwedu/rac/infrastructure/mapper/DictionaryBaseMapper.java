package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DictionaryPo;
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

public interface DictionaryBaseMapper {
    @Delete({
        "delete from tb_dictionary",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_dictionary (en_name, cn_name, ",
        "biz_line_id, use_ext_property, ",
        "remark, create_time, ",
        "create_user_id, update_time, ",
        "update_user_id, deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{bizLineId,jdbcType=BIGINT}, #{useExtProperty,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=BIGINT}, #{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(DictionaryPo record);

    @InsertProvider(type=DictionarySqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(DictionaryPo record);

    @Select({
        "select",
        "id, en_name, cn_name, biz_line_id, use_ext_property, remark, create_time, create_user_id, ",
        "update_time, update_user_id, deleted",
        "from tb_dictionary",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="use_ext_property", property="useExtProperty", jdbcType=JdbcType.INTEGER),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    DictionaryPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=DictionarySqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DictionaryPo record);

    @Update({
        "update tb_dictionary",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "use_ext_property = #{useExtProperty,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DictionaryPo record);
}