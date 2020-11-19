package br.com.utily.ecommerce.helper;

import br.com.utily.ecommerce.entity.Entity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Component
public class ModelAndViewHelper<T extends Entity> {

    private static final String BASE_RESOURCE_PATH_PAGES = "pages";

    public static ModelAndView extractConfiguredFrom(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        StringBuilder resourcePathCustomerPageRequested = new StringBuilder(ModelAndViewHelper.BASE_RESOURCE_PATH_PAGES);

        String requestURI = httpServletRequest.getRequestURI();

        resourcePathCustomerPageRequested.append(requestURI);
        modelAndView.setViewName(resourcePathCustomerPageRequested.toString());

        return modelAndView;
    }

    public ModelAndView extractConfiguredFrom(HttpServletRequest httpServletRequest, Entity additionalObject, String attributeName) {
        ModelAndView modelAndView = extractConfiguredFrom(httpServletRequest);
        addObjectTo(modelAndView, additionalObject, attributeName);

        return modelAndView;
    }

    public void addObjectTo(ModelAndView target, Entity object, String attributeName) {
        String entityName = object.getClass().getName();
        target.addObject(attributeName, object);
    }

    public void addObjectTo(ModelAndView target, Collection<T> objects, String attributeName) {
        String entityName = objects.getClass().getName();
        target.addObject(attributeName, objects);
    }
}
