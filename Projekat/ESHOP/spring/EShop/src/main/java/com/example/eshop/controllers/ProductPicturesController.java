package com.example.eshop.controllers;

import com.example.eshop.services.LogService;
import com.example.eshop.services.ProductPictureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "product_pictures")
@CrossOrigin("http://localhost:1510/")
public class ProductPicturesController {
    private final ProductPictureService productPictureService;
    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @GetMapping(path="/{id}")
    public ResponseEntity<List<byte[]>> retrieveByProductId(@PathVariable("id") Long id){
        logService.save("Class: ProductPicturesController. Method: retrieveByProductId. Get Pictures of a product by product_id.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productPictureService.retrieveByProductId(id), HttpStatus.OK);
    }
}
