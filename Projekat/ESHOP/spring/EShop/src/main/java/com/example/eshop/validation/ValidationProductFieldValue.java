package com.example.eshop.validation;

import com.example.eshop.models.ProductFieldValues;
import com.example.eshop.models.entities.CategoryFieldsEntity;
import com.example.eshop.models.entities.ProductFieldValuesEntity;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ValidationProductFieldValue extends Validation implements IValidation{
    private static final Map<String, Function<Pair<ProductFieldValuesEntity, CategoryFieldsEntity>, Boolean>> mapRegex = new HashMap<>() {{
        put("TEXT", (Pair<ProductFieldValuesEntity, CategoryFieldsEntity> a) -> {
            Pattern REGEX_PATTERN = Pattern.compile(a.getB().getRegex(), Pattern.CASE_INSENSITIVE);
            return REGEX_PATTERN.matcher(a.getA().getFieldValue()).find();
        });

        put("NUMBER", (Pair<ProductFieldValuesEntity, CategoryFieldsEntity> a) -> {
            Long left = 0L;
            Long right = 0L;
            Long value = 0L;

            try {
                String[] sp = a.getB().getRegex().split("\\|");

                left = Long.parseLong(sp[0]);
                right = Long.parseLong(sp[1]);
                value = Long.parseLong(a.getA().getFieldValue());
            }catch (Exception e){
                e.printStackTrace();
            }

            return value >= left && value <= right;
        });

        put("FIXED", (Pair<ProductFieldValuesEntity, CategoryFieldsEntity> a) -> {
            Pattern REGEX_PATTERN = Pattern.compile(a.getB().getRegex(), Pattern.CASE_INSENSITIVE);
            return REGEX_PATTERN.matcher(a.getA().getFieldValue()).find();
        });
    }};

    private ProductFieldValuesEntity productFieldValuesEntity;
    private CategoryFieldsEntity categoryFieldsEntity;

    @Override
    public boolean check() {
        return mapRegex.get(categoryFieldsEntity.getFieldType()).apply(new Pair(productFieldValuesEntity, categoryFieldsEntity));
    }
}
