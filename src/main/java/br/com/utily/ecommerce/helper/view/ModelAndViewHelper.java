package br.com.utily.ecommerce.helper.view;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.folder.EPageFolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ModelAndViewHelper<T extends Entity> {


    public static ModelAndView configure(final EViewType eViewType, final Enum<?> eView) {
        String view = computeViewByEntityType(eViewType, eView);
        return extractConfiguredFrom(view);
    }

    public static ModelAndView configure(final EViewType eViewType, final Enum<?> eView, Entity additionalObject, EModelAttribute eModelAttribute) {
        String view = computeViewByEntityType(eViewType, eView);
        return extractConfiguredFrom(view, additionalObject, eModelAttribute);
    }

    private static ModelAndView extractConfiguredFrom(String view) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(view);

        return modelAndView;
    }

    private static ModelAndView extractConfiguredFrom(String view, Entity additionalObject, EModelAttribute eModelAttribute) {
        ModelAndView modelAndView = extractConfiguredFrom(view);
        addObjectTo(modelAndView, additionalObject, eModelAttribute);

        return modelAndView;
    }

    public static void addObjectTo(ModelAndView target, Entity object, EModelAttribute eModelAttribute) {
        target.addObject(eModelAttribute.getName(), object);
    }

    public void addObjectTo(ModelAndView target, Collection<T> objects, EModelAttribute eModelAttribute) {
        target.addObject(eModelAttribute.getName(), objects);
    }

    private static String computeViewByEntityType(final EViewType eViewType, final Enum<?> eView) {
        Collection<Enum<?>> paths = new ArrayList<>();
        paths.add(EPageFolder.PAGES);

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

            case PRODUCT_SHOP:
                paths.add(EPageFolder.SHOP);
                paths.add(EPageFolder.PRODUCT);
                break;
            case PRODUCT_ADMIN:
                paths.add(EPageFolder.ADMIN);
                paths.add(EPageFolder.PRODUCT);
                break;
            case AUTH_APPLICATION:
                paths.add(EPageFolder.AUTH);
                break;
        }

        paths.add(eView);

        return PathBuilderHelper.build(paths);
    }
}
