package com.exqudens.hibernate.test.model.aaa;

import java.util.List;

import com.exqudens.hibernate.test.model.aa.GraphItem;

public interface OrderGraph extends Order {

    default List<GraphItem> getItems() { return null; }
    default void setItems(List<GraphItem> items) {}

}
