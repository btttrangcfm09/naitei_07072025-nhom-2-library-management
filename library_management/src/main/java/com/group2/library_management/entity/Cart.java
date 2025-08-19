package com.group2.library_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @Id
    private Integer id; // Sử dụng ID của User làm khóa chính

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId 
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(
        mappedBy = "cart",
        cascade = CascadeType.ALL, 
        orphanRemoval = true 
    )
    @Builder.Default
    private Set<CartItem> items = new HashSet<>();

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
