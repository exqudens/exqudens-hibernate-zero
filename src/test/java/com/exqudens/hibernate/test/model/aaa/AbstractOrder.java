package com.exqudens.hibernate.test.model.aaa;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractOrder extends AbstractModel {

    private String orderNumber;

    public AbstractOrder(Long id, Date modified, String orderNumber) {
        super(id, modified);
        this.orderNumber = orderNumber;
    }

}
