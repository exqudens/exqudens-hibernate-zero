package com.exqudens.hibernate.test.model.aaa;

import java.util.Date;

public interface Model {

    default Long getId() { return null; }
    default void setId(Long id) {}

    default Date getModified() { return null; }
    default void setModified(Date modified) {}

}
