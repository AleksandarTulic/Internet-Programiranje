package com.example.eshop.services;

import com.example.eshop.exceptions.NotFoundException;
import com.example.eshop.models.CommentRequest;
import com.example.eshop.models.entities.CommentEntity;
import com.example.eshop.models.entities.ProductBoughtEntity;
import com.example.eshop.models.entities.ProductEntity;
import com.example.eshop.repositories.CommentRepository;
import com.example.eshop.repositories.ProductBoughtRepository;
import com.example.eshop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductBoughtRepository productBoughtRepository;
    private final ProductRepository productRepository;
    private final LogService logService;


    public List<CommentEntity> retrieveAllByProductId(Long productId, Integer left, Integer right){
        List<CommentEntity> arr = commentRepository.findAllByProductId(productId);
        return arr.subList(Math.min(arr.size(), left), Math.min(arr.size(), right));
    }

    public Long retrieveNumberOfComments(Long productId){
        List<CommentEntity> arr = commentRepository.findAllByProductId(productId);
        return arr.size() + 0L;
    }

    public CommentEntity create(CommentRequest commentRequest){
        Optional<ProductBoughtEntity> productBoughtEntity = productBoughtRepository.findById(commentRequest.getProductId());

        if (productBoughtEntity.isPresent()) {
            logService.save("Class: CommentService. Method: create. Product with product_id=" + commentRequest.getProductId() + " is already bought. Comments on bought products are not allowed.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Product with product_id=" + commentRequest.getProductId() + " is already bought. Comments on bought products are not allowed.");
        }

        Optional<ProductEntity> productEntity = productRepository.findById(commentRequest.getProductId());
        if (!productEntity.isPresent()) {
            logService.save("Class: CommentService. Method: create. Product with product_id=" + commentRequest.getProductId() + " does not exist.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Product with product_id=" + commentRequest.getProductId() + " does not exist.");
        }

        return commentRepository.save(new CommentEntity(commentRequest.getProductId(), commentRequest.getConsumerId(), Timestamp.valueOf(LocalDateTime.now()), commentRequest.getValue()));
    }
}
