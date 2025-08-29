package org.example.podcommerce.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileExtensionValidator implements ConstraintValidator<ValidFile, List<MultipartFile>> {

    private List<String> allowedTypes;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.allowedTypes = Arrays.asList(constraintAnnotation.allowedTypes());
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) {
            return false;
        }

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                return false;
            }
            String contentType = file.getContentType();
            if (contentType == null || !allowedTypes.contains(contentType.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}
