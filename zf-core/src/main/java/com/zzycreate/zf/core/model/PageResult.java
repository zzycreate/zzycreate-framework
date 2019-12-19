package com.zzycreate.zf.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果对象
 *
 * @author zzycreate
 * @date 2019/8/12
 */
@Data
@Accessors(chain = true)
@ApiModel("分页结果")
public final class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -4020285760785552783L;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码", required = true)
    private Integer pageNo;
    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数", required = true)
    private Integer pageSize;
    /**
     * 总页码
     */
    @ApiModelProperty(value = "总页数", required = true)
    private Integer totalPage;
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数", required = true)
    private Integer totalRecords;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据", required = true)
    private List<T> data;

    public <S, T> PageResult<T> wrap(PageResult<S> source, Function<S, T> function) {
        PageResult<T> result = new PageResult<>();
        if (source == null || CollectionUtils.isEmpty(source.getData())) {
            return result;
        }
        result.setPageNo(source.getPageNo())
                .setPageSize(source.getPageSize())
                .setTotalPage(source.getTotalPage())
                .setTotalRecords(source.getTotalRecords())
                .setData(source.getData().stream().map(function).collect(Collectors.toList()));
        return result;
    }

}
