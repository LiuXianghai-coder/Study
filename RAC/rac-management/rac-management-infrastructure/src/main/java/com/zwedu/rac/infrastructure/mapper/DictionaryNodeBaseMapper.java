package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.DictionaryNodePo;
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

public interface DictionaryNodeBaseMapper {
    @Delete({
        "delete from tb_dictionary_node",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_dictionary_node (en_name, cn_name, ",
        "value, biz_line_id, ",
        "dictionary_id, parent_id, ",
        "parent_path, remark, ",
        "create_time, create_user_id, ",
        "update_time, update_user_id, ",
        "deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=VARCHAR}, #{bizLineId,jdbcType=BIGINT}, ",
        "#{dictionaryId,jdbcType=BIGINT}, #{parentId,jdbcType=BIGINT}, ",
        "#{parentPath,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{createUserId,jdbcType=BIGINT}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{updateUserId,jdbcType=BIGINT}, ",
        "#{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(DictionaryNodePo record);

    @InsertProvider(type=DictionaryNodeSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(DictionaryNodePo record);

    @Select({
        "select",
        "id, en_name, cn_name, value, biz_line_id, dictionary_id, parent_id, parent_path, ",
        "remark, create_time, create_user_id, update_time, update_user_id, deleted",
        "from tb_dictionary_node",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="dictionary_id", property="dictionaryId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_path", property="parentPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    DictionaryNodePo selectByPrimaryKey(Long id);

    @UpdateProvider(type=DictionaryNodeSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DictionaryNodePo record);

    @Update({
        "update tb_dictionary_node",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "value = #{value,jdbcType=VARCHAR},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "dictionary_id = #{dictionaryId,jdbcType=BIGINT},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "parent_path = #{parentPath,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "create_user_id = #{createUserId,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "update_user_id = #{updateUserId,jdbcType=BIGINT},",
          "deleted = #{deleted,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DictionaryNodePo record);
}