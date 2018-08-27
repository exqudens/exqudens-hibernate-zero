package com.exqudens.hibernate.test.model.aa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order")
public class GraphOrder extends AbstractOrder {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SELECT)
    private GraphUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    @Fetch(FetchMode.SELECT)
    private GraphSeller seller;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<GraphItem> items;

    
    public GraphOrder() {
        this(null, null, null, null, null, null);
    }

    public GraphOrder(Long id, Date modified, String orderNumber, GraphUser user, GraphSeller seller, List<GraphItem> items) {
        super(id, modified, orderNumber);
        this.user = user;
        this.seller = seller;
        this.items = items;
    }

}
