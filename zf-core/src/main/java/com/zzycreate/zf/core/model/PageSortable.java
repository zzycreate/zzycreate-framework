package com.zzycreate.zf.core.model;

import com.zzycreate.zf.core.enums.SortEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 可分页可排序参数
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("可分页可排序参数")
public class PageSortable extends Pageable {

    private static final long serialVersionUID = 5901464100866009442L;

    @ApiModelProperty(value = "排序字段", required = true, example = "id")
    private String field;
    /**
     * @see SortEnum
     */
    @ApiModelProperty(value = "排序方式，asc-升序，desc-降序", required = true, example = "asc")
    private String order;

}
