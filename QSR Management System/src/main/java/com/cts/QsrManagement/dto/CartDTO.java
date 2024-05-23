package com.cts.QsrManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CartDTO
{
    private int quantity;
    private Long productId;
    private Long userId;
}
