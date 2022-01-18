package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.BizEntityControlPo;
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

public interface BizEntityControlBaseMapper {
    @Delete({
        "delete from tb_biz_entity_control",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_biz_entity_control (subject_entity_id, object_entity_id, ",
        "biz_line_id, description, ",
        "parent_id, parent_path, ",
        "remark, create_time, ",
        "create_user_id, update_time, ",
        "update_user_id, deleted)",
        "values (#{subjectEntityId,jdbcType=BIGINT}, #{objectEntityId,jdbcType=BIGINT}, ",
        "#{bizLineId,jdbcType=BIGINT}, #{description,jdbcType=VARCHAR}, ",
        "#{parentId,jdbcType=BIGINT}, #{parentPath,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=BIGINT}, #{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(BizEntityControlPo record);

    @InsertProvider(type=BizEntityControlSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(BizEntityControlPo record);

    @Select({
        "select",
        "id, subject_entity_id, object_entity_id, biz_line_id, description, parent_id, ",
        "parent_path, remark, create_time, create_user_id, update_time, update_user_id, ",
        "deleted",
        "from tb_biz_entity_control",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="subject_entity_id", property="subjectEntityId", jdbcType=JdbcType.BIGINT),
        @Result(column="object_entity_id", property="objectEntityId", jdbcType=JdbcType.BIGINT),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_path", property="parentPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    BizEntityControlPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=BizEntityControlSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(BizEntityControlPo record);

    @Update({
        "update tb_biz_entity_control",
        "set subject_entity_id = #{subjectEntityId,jdbcType=BIGINT},",
          "object_entity_id = #{objectEntityId,jdbcType=BIGINT},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "description = #{description,jdbcType=VARCHAR},",
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
    int updateByPrimaryKey(BizEntityControlPo record);
}