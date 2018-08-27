package com.exqudens.hibernate.test.model.aa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class GraphUser extends AbstractUser {

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<GraphOrder> orders;

    public GraphUser() {
        this(null, null, null, null);
    }

    public GraphUser(Long id, Date modified, String email, List<GraphOrder> orders) {
        super(id, modified, email);
        this.orders = orders;
    }

}
