package com.example.eshop.controllers;

import com.example.eshop.models.ProductBoughtRequest;
import com.example.eshop.models.entities.ProductBoughtEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.LogService;
import com.example.eshop.services.ProductBoughtService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping(path = "product_bought")
@CrossOrigin("http://localhost:1510/")
public class ProductBoughtController {
    private final ProductBoughtService productBoughtService;
    private final LogService logService;
    private final UserService userService;

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "existsByProductId/{id}")
    public ResponseEntity<Boolean> existsByProductId(@PathVariable("id") Long id){
        logService.save("Class: ProductBoughtController. Method: existsByProductId. Check if a product is bought.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productBoughtService.existsByProductId(id), HttpStatus.OK);
    }

    /*
     * User needs to be logged in
     * */
    @PostMapping(path = "create")
    public ResponseEntity<ProductBoughtEntity> create(@RequestBody ProductBoughtRequest productBoughtRequest, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: ProductBoughtController. Method: create. Product is bought.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(productBoughtService.save(productBoughtRequest), HttpStatus.CREATED);
    }
}
