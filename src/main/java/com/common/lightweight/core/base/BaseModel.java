package com.common.lightweight.core.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import java.io.Serializable;

/**
 * Description:<p>Model基类</p>
 * 不需要用到分页查询的领域类继承该类
 * @author zouky
 */
@Data
public class BaseModel extends Model implements Serializable{
    private static final long serialVersionUID = 5267513614949347562L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /** 指定主键 */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
