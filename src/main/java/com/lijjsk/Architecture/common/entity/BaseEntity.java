package com.lijjsk.Architecture.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
//注解表示这是一个映射的父类，不会映射到数据库表，但可以被其他实体类继承
@MappedSuperclass
//注解表示在执行更新操作时，只更新有变化的字段，而不是全部字段。
@DynamicUpdate
//@EntityListeners 注解用于指定实体类的监听器，这里使用 AuditingEntityListener.class。
//AuditingEntityListener 是 Spring Data JPA 提供的一个实体监听器，用于自动处理实体的创建时间和最后修改时间（审计信息）
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity{
    //@Id 注解用于标识实体类的主键字段。
    @Id
    //@GeneratedValue 注解用于指定主键的生成策略。
    //strategy = GenerationType.IDENTITY 表示使用数据库自增长的方式生成主键。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    //创建时间
    @CreatedDate
    //nullable 属性指定该字段是否允许为 null。
    //updatable 属性指定在执行更新操作时，是否更新该字段的值
    @Column(nullable = false, updatable = false)
    protected Date createTime;
    //最后编辑时间
    @LastModifiedDate
    @Column
    protected Date updateTime;
}
