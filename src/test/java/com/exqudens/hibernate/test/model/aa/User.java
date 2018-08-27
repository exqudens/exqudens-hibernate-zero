package com.exqudens.hibernate.test.model.aa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends AbstractUser {

    public User() {
        this(null, null, null);
    }

    public User(Long id, Date modified, String email) {
        super(id, modified, email);
    }

}
