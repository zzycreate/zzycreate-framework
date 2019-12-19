package com.zzycreate.zzz.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jackson 工具类
 * jackson 主要操作类为 ObjectMapper, 本工具类参考自 https://github.com/gudaoxuri/dew-common
 * <p>
 * ObjectMapper 通过 writeValue 系列方法 将 java 对象序列化 为 json，并将 json 存储成不同的格式，String（writeValueAsString），
 * Byte Array（writeValueAsString），Writer， File，OutStream 和 DataOutput。
 * ObjectMapper 通过 readValue 系列方法从不同的数据源像 String， Byte Array， Reader， File， URL， InputStream 将 json 反序列化为 java 对象。
 * <p>
 * ObjectMapper 只是配置后线程安全，一旦进行配置修改，对象操作并不是线程安全的，官方推荐使用 {@link com.fasterxml.jackson.databind.ObjectWriter} 和
 * {@link com.fasterxml.jackson.databind.ObjectReader} 进行操作，这两个类是完全线程安全，并且对象不可变，轻量
 *
 * @author zzycreate
 * @date 18-12-1
 */
public class JacksonHelper {

    /**
     * Jackson 的核心类，不对外暴露以避免被修改配置，如果需要非默认配置，使用 {@link JacksonHelper#newMapper()} 获取一个默认配置的 ObjectMapper 再进行
     * 其他操作，使用 ObjectReader 或者 ObjectWriter 进行操作是完全线程安全的。
     */
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = newMapper();
    }

    /**
     * 默认配置
     * 1. 反序列化时，无对应实体属性的字段解析不会报错
     * 2. 允许注释
     * 3. 字段可以不使用双引号
     * 4. 可以使用单引号
     * 5. 时间不使用时间戳格式，使用默认格式 yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * 6. 序列化时忽略值为 null 的属性
     * 7. java8 的 LocalDateTime 时间使用 ISO 标准日期时间格式
     * 8. 使用系统默认时区
     */
    public static ObjectMapper newMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 反序列化时，JSON字符串中的字段如果在实体中没有对应字段，解析时不会出错(false)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许注释
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 字段可以不用双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 可用单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 序列化时，Date 日期格式序列化默认时间格式 yyyy-MM-dd'T'HH:mm:ss.SSSZ (false)
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 序列化时，忽略值为 null 的属性(NON_NULL)
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // java8 LocalDateTime格式化使用ISO格式
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        // 使用当前系统时区
        mapper.setTimeZone(Calendar.getInstance().getTimeZone());
        return mapper;
    }

    /**
     * 获取类型工厂 {@link com.fasterxml.jackson.databind.type.TypeFactory}，用于生成 {@link com.fasterxml.jackson.databind.JavaType}
     *
     * @return 类型工厂 {@link com.fasterxml.jackson.databind.type.TypeFactory}
     */
    public static TypeFactory getTypeFactory() {
        return MAPPER.getTypeFactory();
    }

    // -------------------------------------- Reader/Writer ------------------------------------------

    public static ObjectWriter writer() {
        return MAPPER.writer();
    }

    public static ObjectWriter writer(List<SerializationFeature> withFeatures, List<SerializationFeature> withoutFeatures) {
        ObjectWriter writer = MAPPER.writer();
        if (!EmptyHelper.isEmpty(withFeatures)) {
            if (withFeatures.size() > 1) {
                SerializationFeature[] arr =
                        CastHelper.toArray(withFeatures.subList(1, withFeatures.size()), SerializationFeature.class);
                writer.with(withFeatures.get(0), arr);
            } else {
                writer.with(withFeatures.get(0));
            }
        }
        if (!EmptyHelper.isEmpty(withoutFeatures)) {
            if (withoutFeatures.size() > 1) {
                SerializationFeature[] arr =
                        CastHelper.toArray(withoutFeatures.subList(1, withoutFeatures.size()), SerializationFeature.class);
                writer.without(withoutFeatures.get(0), arr);
            } else {
                writer.without(withoutFeatures.get(0));
            }
        }
        return writer;
    }

    public static ObjectWriter writer(boolean pretty) {
        if (pretty) {
            return MAPPER.writer().withDefaultPrettyPrinter();
        }
        return MAPPER.writer();
    }

    public static ObjectReader reader() {
        return MAPPER.reader();
    }

    public static <T> ObjectReader reader(Class<T> clazz) {
        return MAPPER.readerFor(clazz);
    }

    public static ObjectReader reader(JavaType javaType) {
        return MAPPER.readerFor(javaType);
    }

    public static ObjectReader reader(JavaType javaType, List<DeserializationFeature> withFeatures,
                                      List<DeserializationFeature> withoutFeatures) {
        ObjectReader reader = MAPPER.readerFor(javaType);
        if (!EmptyHelper.isEmpty(withFeatures)) {
            if (withFeatures.size() > 1) {
                DeserializationFeature[] arr =
                        CastHelper.toArray(withFeatures.subList(1, withFeatures.size()), DeserializationFeature.class);
                reader.with(withFeatures.get(0), arr);
            } else {
                reader.with(withFeatures.get(0));
            }
        }
        if (!EmptyHelper.isEmpty(withoutFeatures)) {
            if (withoutFeatures.size() > 1) {
                DeserializationFeature[] arr =
                        CastHelper.toArray(withoutFeatures.subList(1, withoutFeatures.size()), DeserializationFeature.class);
                reader.without(withoutFeatures.get(0), arr);
            } else {
                reader.without(withoutFeatures.get(0));
            }
        }
        return reader;
    }

    public static <T> ObjectReader reader(TypeReference<T> typeReference) {
        return MAPPER.readerFor(MAPPER.getTypeFactory().constructType(typeReference));
    }

    public static ObjectReader reader(Class<?> parametrized, Class... parameterClasses) {
        return MAPPER.readerFor(MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses));
    }

    // ------------------------------------------ 序列化 ----------------------------------------------

    /**
     * 序列化： Java对象转成Json字符串
     * 如果本身就是字符串，直接返回字符串
     *
     * @param obj Java对象
     * @return Json字符串
     */
    public static String toJsonString(Object obj) {
        try {
            if (obj instanceof String) {
                return (String) obj;
            }
            return writer().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化： Java对象转成Json字符串，可直接配置
     * 如果本身就是字符串，直接返回字符串
     *
     * @param obj     Java对象
     * @param with    with 的序列化配置
     * @param without without 的序列化配置
     * @return Json字符串
     */
    public static String toJsonString(Object obj, List<SerializationFeature> with, List<SerializationFeature> without) {
        try {
            if (obj instanceof String) {
                return (String) obj;
            }
            return writer(with, without).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化： Java对象转成Json字符串， 格式化显示
     * 如果本身就是字符串，直接返回字符串
     *
     * @param obj Java对象
     * @return Json字符串
     */
    public static String format(Object obj) {
        try {
            if (obj instanceof String) {
                return (String) obj;
            }
            return writer(true).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------ 反序列化：使用 TypeReference 进行类型转换 ------------------------------------

    /**
     * Object 对象转指定类型对象
     *
     * @param obj  源数据（Object），可以是Json字符串、JsonNode或其它Java对象
     * @param type 指定的类型引用
     * @param <E>  指定的类型
     * @return List 集合
     */
    public static <E> E toObject(Object obj, TypeReference<E> type) {
        try {
            if (obj instanceof String) {
                return reader(type).readValue((String) obj);
            } else if (obj instanceof JsonNode) {
                JsonNode jsonNode = (JsonNode) obj;
                return reader(type).readValue(jsonNode.toString());
            } else {
                return reader(type).readValue(writer().writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------ 反序列化：使用 TypeReference 进行指定类型转换 ----------------------------------------

    /**
     * 对象转 List 泛型对象
     *
     * @param obj  源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param type List 类型 TypeReference
     * @param <E>  List 中的泛型类型
     * @return 目标 List 集合
     */
    public static <E> List<E> toList(Object obj, TypeReference<List<E>> type) {
        return JacksonHelper.toObject(obj, type);
    }

    /**
     * 对象转 Set 泛型对象
     *
     * @param obj  源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param type Set 类型 TypeReference
     * @param <E>  Set 中的泛型类型
     * @return 目标 Set 集合
     */
    public static <E> Set<E> toSet(Object obj, TypeReference<Set<E>> type) {
        return JacksonHelper.toObject(obj, type);
    }

    /**
     * 对象转 Map 泛型对象
     *
     * @param obj  源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param type Map 类型 TypeReference
     * @param <K>  Map 中键的泛型类型
     * @param <V>  Map 中值的泛型类型
     * @return 目标 Map 集合
     */
    public static <K, V> Map<K, V> toMap(Object obj, TypeReference<Map<K, V>> type) {
        return JacksonHelper.toObject(obj, type);
    }

    // ------------------------------------ 反序列化：使用 JavaType 进行类型转换 ----------------------------------------

    /**
     * 对象转泛型对象
     *
     * @param obj  源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param type 转换目标 JavaType 类型
     * @return 目标对象（可以视输入的类型进行类型强转）
     */
    public static Object toObject(Object obj, JavaType type) {
        try {
            if (obj instanceof String) {
                return reader(type).readValue((String) obj);
            } else if (obj instanceof JsonNode) {
                return reader(type).readValue(obj.toString());
            } else {
                return reader(type).readValue(writer().writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转泛型对象
     *
     * @param obj     源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param type    转换目标 JavaType 类型
     * @param with    with 的反序列化配置
     * @param without without 的反序列化配置
     * @return 目标对象（可以视输入的类型进行类型强转）
     */
    public static Object toObject(Object obj, JavaType type, List<DeserializationFeature> with, List<DeserializationFeature> without) {
        try {
            if (obj instanceof String) {
                return reader(type, with, without).readValue((String) obj);
            } else if (obj instanceof JsonNode) {
                return reader(type, with, without).readValue(obj.toString());
            } else {
                return reader(type, with, without).readValue(writer().writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------ 反序列化：使用 Class 进行类型转换 ----------------------------------------

    /**
     * 对象转成指定类型的目标对象
     * 如果源数据对象是 String 类型，直接转换 为String
     *
     * @param obj   源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param clazz 目标对象类型
     * @return 目标对象
     */
    public static <E> E toObject(Object obj, Class<E> clazz) {
        try {

            if (obj instanceof String) {
                if (clazz == String.class) {
                    return (E) obj;
                } else {
                    return reader(clazz).readValue((String) obj);
                }
            } else if (obj instanceof JsonNode) {
                return reader(clazz).readValue(obj.toString());
            } else {
                return reader(clazz).readValue(writer().writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转成 Object 对象，需要指定包装类型及包装的泛型类型，常见包装类型有 List, Set, Map 等
     * 本质是使用 JavaType 进行转换, 由于 JavaType 是没有泛型的，所以只能获取到 Object
     *
     * @param obj              源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param parametrized     目标包装类型（集合类型或者 Map）Class
     * @param parameterClasses 目标集合或者 Map 的泛型 Class
     * @return 目标对象（可以视输入的类型进行类型强转）
     */
    public static Object toObject(Object obj, Class<?> parametrized, Class... parameterClasses) {
        try {
            if (obj instanceof String) {
                return reader(parametrized, parameterClasses).readValue((String) obj);
            } else if (obj instanceof JsonNode) {
                return reader(parametrized, parameterClasses).readValue(obj.toString());
            } else {
                return reader(parametrized, parameterClasses).readValue(writer().writeValueAsString(obj));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------ 反序列化：使用 Class 进行指定类型转换 ----------------------------------------

    /**
     * 对象转 List 泛型对象
     *
     * @param obj   源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param clazz List 中的泛型 Class
     * @param <E>   List 中的泛型类型
     * @return 目标 List 集合
     */
    public static <E> List<E> toList(Object obj, Class<E> clazz) {
        return (List<E>) JacksonHelper.toObject(obj, List.class, clazz);
    }

    /**
     * 对象转 Set 泛型对象
     *
     * @param obj   源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param clazz Set 中的泛型 Class
     * @param <E>   Set 中的泛型类型
     * @return 目标 Set 集合
     */
    public static <E> Set<E> toSet(Object obj, Class<E> clazz) {
        return (Set<E>) JacksonHelper.toObject(obj, Set.class, clazz);
    }

    /**
     * 对象转 Map 泛型对象
     *
     * @param obj        源数据，可以是Json字符串、JsonNode或其它Java对象
     * @param keyClazz   Map 中键的泛型 Class
     * @param valueClazz Map 中值的泛型 Class
     * @param <K>        Map 中键的泛型类型
     * @param <V>        Map 中值的泛型类型
     * @return 目标 Map 集合
     */
    public static <K, V> Map<K, V> toMap(Object obj, Class<K> keyClazz, Class<V> valueClazz) {
        return (Map<K, V>) JacksonHelper.toObject(obj, Map.class, keyClazz, valueClazz);
    }

    // ------------------------------ jackson 树模型相关 -----------------------------------

    /**
     * 创建ObjectNode
     *
     * @return objectNode
     */
    public static ObjectNode createObjectNode() {
        return MAPPER.createObjectNode();
    }

    /**
     * 创建ArrayNode
     *
     * @return arrayNode
     */
    public static ArrayNode createArrayNode() {
        return MAPPER.createArrayNode();
    }

    /**
     * Java对象转成JsonNode
     * 字符串使用 readTree 方法解析成 JsonNode
     * 非字符串对象用 valueToTree 方法解析成 JsonNode
     *
     * @param obj Java对象
     * @return JsonNode
     */
    public static JsonNode toJsonNode(Object obj) {
        try {
            if (obj instanceof String) {
                return MAPPER.readTree((String) obj);
            } else {
                return MAPPER.valueToTree(obj);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取对应路径下的Json
     *
     * @param jsonNode Json对象
     * @param pathStr  路径
     * @return 对应的Json对象
     */
    public static JsonNode path(JsonNode jsonNode, String pathStr) {
        String[] splitPaths = pathStr.split("\\.");
        jsonNode = jsonNode.path(splitPaths[0]);
        if (jsonNode instanceof MissingNode) {
            return null;
        } else if (splitPaths.length == 1) {
            return jsonNode;
        } else {
            return path(jsonNode, pathStr.substring(pathStr.indexOf(".") + 1));
        }
    }

}
