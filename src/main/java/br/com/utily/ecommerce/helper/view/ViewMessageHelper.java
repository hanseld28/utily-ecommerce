package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

public class ViewMessageHelper {

    public static void configureRedirectMessageWith(Errors errors,
                                                    Boolean isSuccess,
                                                    RedirectAttributes redirectAttributes) {
        String message = errors.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(";"));

        addMessageAndFlagTo(redirectAttributes, message, isSuccess);
    }

    public static void configureRedirectMessageWith(String message,
                                                    Boolean isSuccess,
                                                    RedirectAttributes redirectAttributes) {
        addMessageAndFlagTo(redirectAttributes, message, isSuccess);
    }

    public static void addMessageAndFlagTo(RedirectAttributes redirectAttributes, String message, Boolean flag) {
        addFlashAttributeTo(
                redirectAttributes,
                EModelAttribute.IS_SUCCESS_MESSAGE.getName(),
                flag
        );
        addFlashAttributeTo(
                redirectAttributes,
                EModelAttribute.MESSAGE.getName(),
                message
        );
    }

    public static void addFlashAttributeTo(RedirectAttributes redirectAttributes, String attribute, Object value) {
        redirectAttributes.addFlashAttribute(attribute, value);
    }
}
