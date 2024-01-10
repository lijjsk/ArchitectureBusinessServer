package com.lijjsk.Architecture.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class LogicEntity extends BaseEntity{
    @Column(insertable = false)
    protected Date deletedTime;
    //insertable 属性指定是否在执行插入操作时插入该字段的值。
    //columnDefinition 属性允许为数据库表的列定义特定的 SQL 类型和其他选项。
    //在这里，INT default 0 指定了该字段在数据库中的类型为整数 (INT)，并设置默认值为 0。如果插入时没有提供该字段的值，数据库会自动使用默认值 0
    @Column(insertable = false,
            nullable = false,
            columnDefinition="INT default 0")
    protected Integer isDeleted;
}
