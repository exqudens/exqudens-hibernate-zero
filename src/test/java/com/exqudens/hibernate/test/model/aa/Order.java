package com.exqudens.hibernate.test.model.aa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order extends AbstractOrder {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "seller_id")
    private Long sellerId;

    public Order() {
        this(null, null, null, null, null);
    }

    public Order(Long id, Date modified, Long userId, Long sellerId, String orderNumber) {
        super(id, modified, orderNumber);
        this.userId = userId;
        this.sellerId = sellerId;
    }

}
