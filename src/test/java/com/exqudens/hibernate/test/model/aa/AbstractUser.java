package com.exqudens.hibernate.test.model.aa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AbstractUser extends AbstractModel {

    @Column(name = "email")
    private String email;

    public AbstractUser(Long id, Date modified, String email) {
        super(id, modified);
        this.email = email;
    }

}
