package com.dailycodework.dreamshops.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addItem(CartItem item){
        this.items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item){
        this.items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    public void updateTotalAmount(){
        this.totalAmount = items.stream().map(item-> {
            BigDecimal unitPrice = item.getUnitPrice();
            if(unitPrice ==null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.ZERO.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
