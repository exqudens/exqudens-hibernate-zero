package com.exqudens.hibernate.test.model.aaa;

public interface Order extends Model {

    default String getOrderNumber() { return null; }
    default void setOrderNumber(String orderNumber) {}

}
