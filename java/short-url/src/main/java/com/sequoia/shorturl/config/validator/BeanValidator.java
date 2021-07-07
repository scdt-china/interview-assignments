package com.sequoia.shorturl.config.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

/**
 * 统一校验
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
public class BeanValidator {

    private SmartValidator smartValidator;

    public BeanValidator(SmartValidator smartValidator) {
        this.smartValidator = smartValidator;
    }

    public void validate(Object target, BindingResult br, Object... validationHints) {
        smartValidator.validate(target, br, validationHints);
        if (br.hasErrors()) {
            FieldError fieldError = br.getFieldError();
            String errorMsg = fieldError.getField() + " " + fieldError.getDefaultMessage();
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public void validateNonThrow(Object target, BindingResult br, Object... validationHints) {
        smartValidator.validate(target, br, validationHints);
    }
}
