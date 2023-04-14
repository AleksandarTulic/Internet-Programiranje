package com.example.eshop.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair <K,V>{
    private K a;
    private V b;
}
