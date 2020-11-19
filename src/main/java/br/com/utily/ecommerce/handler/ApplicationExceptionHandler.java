package br.com.utily.ecommerce.handler;

import br.com.utily.ecommerce.handler.exception.AccessDeniedException;
import br.com.utily.ecommerce.handler.exception.InternalServerErrorException;
import br.com.utily.ecommerce.handler.exception.NotFoundException;
import br.com.utily.ecommerce.handler.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ApplicationExceptionHandler extends SimpleMappingExceptionResolver {

    private final String UNAUTHORIZED_PAGE = "pages/error/401";
    private final String ACCESS_DENIED_PAGE = "pages/error/403";
    private final String NOT_FOUND_PAGE = "pages/error/404";
    private final String INTERNAL_SERVER_ERROR_PAGE = "pages/error/500";

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = null;

        if (ex instanceof UnauthorizedException) {
            viewName = UNAUTHORIZED_PAGE;
        }
        if (ex instanceof AccessDeniedException) {
            viewName = ACCESS_DENIED_PAGE;
        }
        if (ex instanceof NotFoundException) {
            viewName = NOT_FOUND_PAGE;
        }
        if (ex instanceof InternalServerErrorException) {
            viewName = INTERNAL_SERVER_ERROR_PAGE;
        }
        modelAndView.setViewName(viewName);

        return modelAndView;
    }
}
