package com.zwedu.rac.application.converter;

import com.zwedu.rac.domain.entity.UserEntity;
import com.zwedu.rac.sdk.rdo.ExtPropertyRdo;
import com.zwedu.rac.sdk.rdo.UserRdo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户entity-dto转换器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Mapper
public interface UserEntity2RdoConverter extends Entity2RdoConverter<UserEntity, UserRdo> {
    /**
     * 实例
     */
    UserEntity2RdoConverter INSTANCE = Mappers.getMapper(UserEntity2RdoConverter.class);

    /**
     * 转化用户请求响应对象
     *
     * @param userEntity         用户实体
     * @param extPropertyRdoList 扩展属性列表
     * @return 用户对象
     */
    @Mappings({
            @Mapping(target = "userId", expression = "java(userEntity.getId())"),
            @Mapping(target = "extData", expression = "java(com.zwedu.rac.domain.common.util.Collections2" +
                    ".toMap(extPropertyRdoList, input->input.getName(),input->input.getValue()))")
    })
    UserRdo toRdo(UserEntity userEntity, List<ExtPropertyRdo> extPropertyRdoList);
}
