package com.example.eshop.controllers;

import com.example.eshop.models.Product;
import com.example.eshop.models.ProductResponse;
import com.example.eshop.models.ProductSearchRequest;
import com.example.eshop.models.entities.ProductEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.LogService;
import com.example.eshop.services.ProductService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "product")
@CrossOrigin("http://localhost:1510/")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @PostMapping
    public ResponseEntity<List<ProductResponse>> retrieveBySpecificConditions(@RequestBody ProductSearchRequest productSearchRequest){
        logService.save("Class: ProductController. Method: retrieveBySpecificConditions. Get products that fulfill some requests.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveBySpecificConditions(productSearchRequest), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @PostMapping(path = "retrieveNumberOf")
    public ResponseEntity<Long> retrieveNumberOf(@RequestBody ProductSearchRequest productSearchRequest){
        logService.save("Class: ProductController. Method: retrieveNumberOf. Get number of products that fulfill some requests.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveNumberOfProducts(productSearchRequest), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path="/{id}")
    public ResponseEntity<ProductEntity> retrieveById(@PathVariable("id") Long id){
        logService.save("Class: ProductController. Method: retrieveById. Get product by product_id.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveById(id), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "/exists/{id}")
    public ResponseEntity<Boolean> retrieveExists(@PathVariable("id") Long id){
        logService.save("Class: ProductController. Method: retrieveExists. Check if a product exists.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.exists(id), HttpStatus.OK);
    }

    /*
     * User needs to be logged in
     * */
    @PostMapping(path="create", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductEntity> save(@ModelAttribute Product request, Principal principal){
        System.out.println(request);
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: ProductController. Method: save. Add Product.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(productService.save(request, request.getId()), HttpStatus.CREATED);
    }

    /*
     * User needs to be logged in
     * */
    @PutMapping(path="update", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductEntity> update(@ModelAttribute Product request, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: ProductController. Method: update. Update Product By product_id.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(productService.save(request, request.getId()), HttpStatus.OK);
    }

    /*
     * User needs to be logged in
     * */
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: ProductController. Method: delete. Delete Product By product_id.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(productService.deleteById(id, userEntity.getId()), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveProductsNotBought/{id}/{left}/{right}")
    public ResponseEntity<List<ProductResponse>> retrieveProductsNotBought(@PathVariable("id") Long id,
                                                                           @PathVariable("left") Integer left,
                                                                           @PathVariable("right") Integer right){
        logService.save("Class: ProductController. Method: retrieveProductsNotBought. Get products that are not bought yet that is they are active.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveProductsNotBought(id, left, right), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveNumberOfProductsNotBought/{id}")
    public ResponseEntity<Long> retrieveNumberOfProductsNotBought(@PathVariable("id") Long id){
        logService.save("Class: ProductController. Method: retrieveNumberOfProductsNotBought. Get number of products that are not bought yet that is they are active.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveNumberProductsNotBought(id), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveProductsBought/{id}/{left}/{right}")
    public ResponseEntity<List<ProductResponse>> retrieveProductsBought(@PathVariable("id") Long id,
                                                                        @PathVariable("left") Integer left,
                                                                        @PathVariable("right") Integer right){
        logService.save("Class: ProductController. Method: retrieveProductsBought. Get products that someone bought from the user.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveProductsBought(id, left, right), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveNumberOfProductsBought/{id}")
    public ResponseEntity<Long> retrieveNumberOfProductsBought(@PathVariable("id") Long id){
        logService.save("Class: ProductController. Method: retrieveNumberOfProductsBought. Get number of products that someone bought from the user.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveNumberProductsBought(id), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveProductsUserBought/{id}/{left}/{right}")
    public ResponseEntity<List<ProductResponse>> retrieveProductsUserBought(@PathVariable("id") Long id,
                                                                            @PathVariable("left") Integer left,
                                                                            @PathVariable("right") Integer right){
        logService.save("Class: ProductController. Method: retrieveProductsUserBought. Get products the user bought for himself.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveProductsUserBought(id, left, right), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "retrieveNumberOfProductsUserBought/{id}")
    public ResponseEntity<Long> retrieveNumberOfProductsUserBought(@PathVariable("id") Long id){
        logService.save("Class: ProductController. Method: retrieveNumberOfProductsUserBought. Get number of products the user bought for himself.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(productService.retrieveNumberProductsUserBought(id), HttpStatus.OK);
    }
}
