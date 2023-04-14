package com.example.eshop.controllers;

import com.example.eshop.models.entities.CategoryFieldsEntity;
import com.example.eshop.services.CategoryFieldsService;
import com.example.eshop.services.LogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "categoryFields")
@AllArgsConstructor
@CrossOrigin("http://localhost:1510/")
public class CategoryFieldsController {
    private final CategoryFieldsService categoryFieldsService;
    private final LogService logService;

    /*
     * Everyone can use this
     * */
    @GetMapping(path = "/{categoryTitle}")
    public ResponseEntity<List<CategoryFieldsEntity>> findAllCategoryFieldsByTitle(@PathVariable("categoryTitle") String categoryTitle){
        logService.save("Class: CategoryFieldsController. Method: findAllCategoryFieldsByTitle. Getting all Fields of a category.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(categoryFieldsService.findByCategoryTitle(categoryTitle), HttpStatus.OK);
    }
}
