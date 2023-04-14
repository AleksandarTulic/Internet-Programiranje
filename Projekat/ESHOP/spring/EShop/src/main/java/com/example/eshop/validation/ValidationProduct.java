package com.example.eshop.validation;

import com.example.eshop.models.entities.ProductEntity;
import lombok.AllArgsConstructor;
import java.util.Map;
import java.util.HashMap;

@AllArgsConstructor
public class ValidationProduct extends Validation implements IValidation{
    private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("Title", "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$");
        put("Description", "^.{1,1000}$");
        put("Cost", "^[0-9]{1,}[\\.]{1,1}[0-9]{1,}[E]{0,1}[0-9]{1,}$");
        put("ProductType", "^NEW$|^USED$");
    }};

    private ProductEntity productEntity;

    @Override
    public boolean check() {
        return super.checkRegex(productEntity.getTitle(), mapRegex.get("Title")) &&
                super.checkRegex(productEntity.getDescription(), mapRegex.get("Description")) &&
                super.checkRegex(productEntity.getCost() + "", mapRegex.get("Cost")) &&
                super.checkRegex(productEntity.getProductType(), mapRegex.get("ProductType"));
    }
}
