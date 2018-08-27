package com.exqudens.hibernate.test.model.aa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractOrder extends AbstractModel {

    @Column(name = "order_number")
    private String orderNumber;

    public AbstractOrder(Long id, Date modified, String orderNumber) {
        super(id, modified);
        this.orderNumber = orderNumber;
    }

}
