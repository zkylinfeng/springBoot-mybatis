package com.common.lightweight.core.base;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import java.io.Serializable;

/**
 * Description:<p>Model基类</p>
 * 需要用到分页查询的领域类继承该类
 * @author zouky
 * @date 2017-11-30
 */
@Data
public class BaseModelWithPage extends BaseModel implements Serializable{
    private static final long serialVersionUID = -7859681697184595409L;

    /**
     * 分页查询时每个显示的条数
     * */
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 分页查询时需要查询的页数
     * */
    @TableField(exist = false)
    private Integer page;
}
