package com.exqudens.hibernate.test.model.aaa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMysqlOrder extends AbstractOrder {

    public AbstractMysqlOrder(Long id, Date modified, String orderNumber) {
        super(id, modified, orderNumber);
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    @Override
    public Long getId() {
        return super.getId();
    }

    @Column(
        name = "modified",
        nullable = false,
        columnDefinition = "timestamp default current_timestamp on update current_timestamp",
        insertable = false,
        updatable = false
    )
    @Override
    public Date getModified() {
        return super.getModified();
    }

    @Column(name = "order_number")
    @Override
    public String getOrderNumber() {
        return super.getOrderNumber();
    }

}
