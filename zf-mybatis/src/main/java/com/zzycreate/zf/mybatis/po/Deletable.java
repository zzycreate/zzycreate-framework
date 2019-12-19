package com.zzycreate.zf.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zzycreate.zf.core.enums.DeletedStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * extends BaseDO 扩展 delete 操作
 *
 * @author zzycreate
 * @date 2019/8/11
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Deletable extends Base {

    private static final long serialVersionUID = -6055227504098290947L;
    /**
     * 是否删除
     *
     * @see DeletedStatusEnum#getCode()
     */
    @TableField(value = "deleted")
    private Integer deleted;

}
