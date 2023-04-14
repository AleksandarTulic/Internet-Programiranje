package com.example.eshop.controllers;

import com.example.eshop.models.entities.CategoryEntity;
import com.example.eshop.services.CategoryService;
import com.example.eshop.services.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "category")
@CrossOrigin("http://localhost:1510/")
public class CategoryController {
    private final CategoryService categoryService;
    private final LogService logService;

    /*
    * Everyone can use this
    * */
    @GetMapping
    public ResponseEntity<List<CategoryEntity>> findAll(){
        logService.save("Class: CategoryController. Method: findAll. Getting all categories.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
}
