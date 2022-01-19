package com.zwedu.rac.infrastructure.mapper;

import com.zwedu.rac.infrastructure.po.MenuPo;
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

public interface MenuBaseMapper {
    @Delete({
        "delete from tb_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_menu (en_name, cn_name, ",
        "biz_line_id, url, seq, ",
        "parent_id, parent_path, ",
        "remark, create_time, ",
        "create_user_id, update_time, ",
        "update_user_id, deleted)",
        "values (#{enName,jdbcType=VARCHAR}, #{cnName,jdbcType=VARCHAR}, ",
        "#{bizLineId,jdbcType=BIGINT}, #{url,jdbcType=VARCHAR}, #{seq,jdbcType=INTEGER}, ",
        "#{parentId,jdbcType=BIGINT}, #{parentPath,jdbcType=VARCHAR}, ",
        "#{remark,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{createUserId,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{updateUserId,jdbcType=BIGINT}, #{deleted,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(MenuPo record);

    @InsertProvider(type=MenuSqlSupporter.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(MenuPo record);

    @Select({
        "select",
        "id, en_name, cn_name, biz_line_id, url, seq, parent_id, parent_path, remark, ",
        "create_time, create_user_id, update_time, update_user_id, deleted",
        "from tb_menu",
        "where id = #{id,jdbcType=BIGINT} and deleted = 0"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="en_name", property="enName", jdbcType=JdbcType.VARCHAR),
        @Result(column="cn_name", property="cnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="biz_line_id", property="bizLineId", jdbcType=JdbcType.BIGINT),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="seq", property="seq", jdbcType=JdbcType.INTEGER),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.BIGINT),
        @Result(column="parent_path", property="parentPath", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_user_id", property="updateUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="deleted", property="deleted", jdbcType=JdbcType.INTEGER)
    })
    MenuPo selectByPrimaryKey(Long id);

    @UpdateProvider(type=MenuSqlSupporter.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MenuPo record);

    @Update({
        "update tb_menu",
        "set en_name = #{enName,jdbcType=VARCHAR},",
          "cn_name = #{cnName,jdbcType=VARCHAR},",
          "biz_line_id = #{bizLineId,jdbcType=BIGINT},",
          "url = #{url,jdbcType=VARCHAR},",
          "seq = #{seq,jdbcType=INTEGER},",
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
    int updateByPrimaryKey(MenuPo record);
}