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

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        String viewName = null;

        if (ex instanceof UnauthorizedException) {
            viewName = EErrorPage.UNAUTHORIZED.getPath();
        }
        if (ex instanceof AccessDeniedException) {
            viewName = EErrorPage.ACCESS_DENIED.getPath();
        }
        if (ex instanceof NotFoundException) {
            viewName = EErrorPage.NOT_FOUND.getPath();
        }
        if (ex instanceof InternalServerErrorException) {
            viewName = EErrorPage.INTERNAL_SERVER_ERROR.getPath();
        }

        if (viewName == null) {
            // TODO: make a default error page and create respective enum constant
            viewName = EErrorPage.INTERNAL_SERVER_ERROR.getPath();
        }

        modelAndView.setViewName(viewName);

        return modelAndView;
    }
}
