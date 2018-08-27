package com.exqudens.hibernate.test.model.aaa;

public interface Item extends Model {

    default String getDescription() { return null; }
    default void setDescription(String description) {}

}
