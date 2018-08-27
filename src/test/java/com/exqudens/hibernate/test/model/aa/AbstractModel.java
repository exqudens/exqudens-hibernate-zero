package com.exqudens.hibernate.test.model.aa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(
        name = "modified",
        nullable = false,
        columnDefinition = "timestamp default current_timestamp on update current_timestamp",
        insertable = false,
        updatable = false
    )
    private Date modified;

}
