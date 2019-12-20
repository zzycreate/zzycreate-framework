package com.zzycreate.zf.core.support;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * mapstruct 对象转换
 * 继承本接口的类需要在类上添加 {@link org.mapstruct.Mapper} 注解，并提供 componentModel = "spring" 的参数值
 * 如果对象之间有特殊类型转换，请在to方法上使用 {@link Mappings} 和 {@link org.mapstruct.Mapping} 注解进行转换
 * 调用的时候直接调用接口的 to 或者 from 方法，程序在编译的时候会自动生成转换器的实现类，本质是注入到spring的 @Component，
 * 调用getter/setter进行参数赋值
 * TIPS 1: IDEA 请安装 MapStruct Support 插件
 * TIPS 2: 实现接口需要 UserMapping INSTANCE = Mappers.getMapper(UserMapping.class); 声明单例属性
 *
 * @author zzycreate
 * @date 2019/4/18
 */
@MapperConfig
public interface BaseMapping<Source, Target> {

    /**
     * Source -> Target
     *
     * @param source Source
     * @return Target
     */
    @Mappings({})
    @InheritConfiguration
    Target to(Source source);

    /**
     * Source LIST -> Target
     *
     * @param sourceList List<Source>
     * @return List<Target>
     */
    @InheritConfiguration
    List<Target> to(List<Source> sourceList);

    /**
     * Target -> Source
     * @param target Target
     * @return Source
     */
    @InheritInverseConfiguration
    Source from(Target target);

    /**
     * Target LIST -> Source
     * @param targetList List<Target>
     * @return List<Source>
     */
    @InheritInverseConfiguration
    List<Source> from(List<Target> targetList);

}
