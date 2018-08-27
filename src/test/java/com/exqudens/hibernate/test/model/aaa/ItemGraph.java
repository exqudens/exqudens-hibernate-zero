package com.exqudens.hibernate.test.model.aaa;

import com.exqudens.hibernate.test.model.aa.GraphOrder;

public interface ItemGraph extends Item {

    default GraphOrder getOrder() { return null; }
    default void setOrder(GraphOrder order) {}

}
