package com.dailycodework.dreamshops.DTO;

import com.dailycodework.dreamshops.model.Cart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders = new ArrayList<>(); //Initializing as empty list
    private CartDto cart;
}
