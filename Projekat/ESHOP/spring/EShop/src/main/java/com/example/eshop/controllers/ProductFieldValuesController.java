package com.example.eshop.controllers;

import com.example.eshop.models.entities.ProductFieldValuesEntity;
import com.example.eshop.services.LogService;
import com.example.eshop.services.ProductFieldValueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "product_field_values")
@CrossOrigin("http://localhost:1510/")
public class ProductFieldValuesController {
    private final ProductFieldValueService productFieldValueService;
    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @GetMapping(path="/{productId}/{categoryFieldTitle}")
    public ResponseEntity<List<ProductFieldValuesEntity>> retrieveByProductId(@PathVariable("productId") Long productId, @PathVariable("categoryFieldTitle") String categoryFieldTitle){
        logService.save("Class: ProductFieldValuesController. Method: retrieveByProductId. Get product field values given product_id and category_field_title.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productFieldValueService.findByCategoryFieldTitleAndProductId(categoryFieldTitle, productId), HttpStatus.OK);
    }
}
