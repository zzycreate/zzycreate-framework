package com.zzycreate.zf.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 可排序参数
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@Data
@ApiModel("可排序参数")
public class Sortable implements Serializable {

    private static final long serialVersionUID = 4067682976783217772L;

    @ApiModelProperty(value = "排序字段", required = true, example = "id")
    private String field;
    /**
     * @see com.zzycreate.zf.core.enums.SortEnum
     */
    @ApiModelProperty(value = "排序方式，asc-升序，desc-降序", required = true, example = "asc")
    private String order;

}
