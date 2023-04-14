package com.example.eshop.controllers;

import com.example.eshop.models.CommentRequest;
import com.example.eshop.models.entities.CommentEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.CommentService;
import com.example.eshop.services.LogService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "comment")
@CrossOrigin("http://localhost:1510/")
public class CommentController {
    private final CommentService commentService;

    private final LogService logService;

    private final UserService userService;

    /*
     * Everyone can use this
     * */
    @GetMapping(path = "{id}/{left}/{right}")
    public ResponseEntity<List<CommentEntity>> retrieveAllByProductId(@PathVariable("id") Long id, @PathVariable("left") Integer left,
                                                                      @PathVariable("right") Integer right){
        logService.save("Class: CommentController. Method: retrieveAllByProductId. Getting all comments of a product offer.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(commentService.retrieveAllByProductId(id, left, right), HttpStatus.OK);
    }

    /*
     * Everyone can use this
     * */
    @GetMapping(path = "retrieveNumberOfComments/{id}")
    public ResponseEntity<Long> retrieveNumberOfComments(@PathVariable("id") Long id){
        logService.save("Class: CommentController. Method: retrieveNumberOfComments. Getting number of comments of a product offer.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(commentService.retrieveNumberOfComments(id), HttpStatus.OK);
    }

    /*
     * User needs to be logged in
     * */
    @PostMapping(path = "create")
    public ResponseEntity<CommentEntity> create(@RequestBody CommentRequest commentRequest, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: CommentController. Method: create. Creating a comment for a product offer.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(commentService.create(commentRequest), HttpStatus.CREATED);
    }
}
