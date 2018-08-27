package com.exqudens.hibernate.test.model.aaa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class MysqlOrder extends AbstractMysqlOrder {

    public MysqlOrder() {
        this(null, null, null);
    }

    public MysqlOrder(Long id, Date modified, String orderNumber) {
        super(id, modified, orderNumber);
    }

}
