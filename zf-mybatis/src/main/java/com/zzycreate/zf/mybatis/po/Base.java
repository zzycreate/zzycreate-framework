package com.zzycreate.zf.mybatis.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础数据库类型
 *
 * @author zzycreate
 * @date 2019/8/11
 */
@Data
public class Base implements Serializable {

    private static final long serialVersionUID = 7558546480507939314L;
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;
    /**
     * 最后更新时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
