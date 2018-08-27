package com.exqudens.hibernate.test.model.aaa;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractModel implements Model {

    private Long id;
    private Date modified;

}
