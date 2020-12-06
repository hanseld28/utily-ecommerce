package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.endpoint.EEndpoint;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.folder.EPageFolder;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
public class ModelAndViewHelper {

    public static ModelAndView configure(final EViewType eViewType) {
        String view = computeViewByEntityType(eViewType);
        return extractConfiguredFrom(view);
    }

    public static ModelAndView configure(final EViewType eViewType, final Enum<?> eView) {
        String view = computeViewByEntityType(eViewType, eView);
        return extractConfiguredFrom(view);
    }

    public static ModelAndView configure(final EViewType eViewType, final Enum<?> eView, Object additionalObject, EModelAttribute eModelAttribute) {
        String view = computeViewByEntityType(eViewType, eView);
        return extractConfiguredFrom(view, additionalObject, eModelAttribute);
    }

    private static ModelAndView extractConfiguredFrom(String view) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(view);
        return modelAndView;
    }

    private static ModelAndView extractConfiguredFrom(String view, Object additionalObject, EModelAttribute eModelAttribute) {
        ModelAndView modelAndView = extractConfiguredFrom(view);
        addObjectTo(modelAndView, additionalObject, eModelAttribute);
        return modelAndView;
    }

    public static void addObjectTo(ModelAndView target, Object object, EModelAttribute eModelAttribute) {
        target.addObject(eModelAttribute.getName(), object);
    }

    public static void addObjectTo(ModelAndView target, Collection<?> objects, EModelAttribute eModelAttribute) {
        target.addObject(eModelAttribute.getName(), objects);
    }

    public static void addModelMapTo(ModelAndView target, Map<String, ?> modelMap) {
        target.addAllObjects(modelMap);
    }

    private static Collection<Enum<?>> identifyPaths(final EViewType eViewType) {
        Collection<Enum<?>> paths = new ArrayList<>();

        switch (eViewType) {

            case CUSTOMER_ACCOUNT_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CUSTOMER);
                paths.add(EPageFolder.ACCOUNT);
                break;
            case CUSTOMER_ADDRESS_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CUSTOMER);
                paths.add(EPageFolder.ADDRESS);
                break;
            case CUSTOMER_CREDIT_CARD_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CUSTOMER);
                paths.add(EPageFolder.CREDIT_CARD);
                break;

            case CUSTOMER_ORDER_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CUSTOMER);
                paths.add(EPageFolder.ORDER);
                break;

            case PRODUCT_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.PRODUCT);
                break;

            case PRODUCT_ADMIN:
                paths.add(EPageFolder.ADMIN);
                paths.add(EPageFolder.PRODUCT);
                break;

            case STOCK_ADMIN:
                paths.add(EPageFolder.ADMIN);
                paths.add(EPageFolder.STOCK);
                break;

            case STOCK_HISTORY_ADMIN:
                paths.add(EPageFolder.ADMIN);
                paths.add(EPageFolder.STOCK);
                paths.add(EPageFolder.STOCK_HISTORY);
                break;

            case CART_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CART);
                break;

            case CHECKOUT_STEP_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CHECKOUT);
                paths.add(EPageFolder.STEP);
                break;

            case CHECKOUT_FINISH_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.CHECKOUT);
                break;

            case SALE_ADMIN:
                paths.add(EPageFolder.ADMIN);
                paths.add(EPageFolder.SALE);
                break;

            case REDIRECT_STOCKS_ADMIN:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.ADMIN);
                paths.add(EEndpoint.STOCKS);
                break;

            case REDIRECT_NEW_STOCK_ADMIN:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.ADMIN);
                paths.add(EEndpoint.STOCKS);
                paths.add(EEndpoint.NEW);
                break;

            case REDIRECT_SALES_ADMIN:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.ADMIN);
                paths.add(EEndpoint.SALES);
                break;

            case REDIRECT_PRODUCT_SHOP:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.SHOP);
                paths.add(EEndpoint.PRODUCTS);
                break;

            case REDIRECT_CHECKOUT_STEP_ONE:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.SHOP);
                paths.add(EEndpoint.CHECKOUT);
                paths.add(EEndpoint.STEP);
                paths.add(EEndpoint.STEP_ONE);
                break;

            case REDIRECT_CHECKOUT_STEP_TWO:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.SHOP);
                paths.add(EEndpoint.CHECKOUT);
                paths.add(EEndpoint.STEP);
                paths.add(EEndpoint.STEP_TWO);
                break;

            case REDIRECT_CHECKOUT_STEP_THREE:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.SHOP);
                paths.add(EEndpoint.CHECKOUT);
                paths.add(EEndpoint.STEP);
                paths.add(EEndpoint.STEP_THREE);
                break;

            case REDIRECT_LOGIN_APPLICATION:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.AUTH);
                paths.add(EEndpoint.LOGIN);
                break;

            case REDIRECT_CUSTOMER_ACCOUNT_SHOP:
                paths.add(EView.REDIRECT);
                paths.add(EEndpoint.CUSTOMER);
                paths.add(EEndpoint.ACCOUNT);
                break;

            case AUTH_APPLICATION:
                paths.add(EPageFolder.AUTH);
                break;

            case DASHBOARD_ADMIN:
                paths.add(EPageFolder.ADMIN);
                break;

            default:
                throw new NotFoundException();
        }

        return paths;
    }

    private static String computeViewByEntityType(final EViewType eViewType) {
        Collection<Enum<?>> paths = identifyPaths(eViewType);
        return PathBuilderHelper.build(paths);
    }

    private static String computeViewByEntityType(final EViewType eViewType, final Enum<?> eView) {
        Collection<Enum<?>> paths = new ArrayList<>();

        paths.add(EPageFolder.PAGES);
        paths.addAll(identifyPaths(eViewType));
        paths.add(eView);

        return PathBuilderHelper.build(paths);
    }
}
