package br.com.utily.ecommerce.helper.session;

import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionHelper {

    private static HttpSession httpSession;

    private SessionHelper() { }

    public static void initialize(HttpSession httpSession) {
        if (SessionHelper.httpSession == null) {
            SessionHelper.httpSession = httpSession;
        }
    }

    public static Optional<HttpSession> getHttpSession() {
        return Optional.ofNullable(httpSession);
    }

    public static void remove(String attributeName, Object object) {
        if (!httpSession.isNew())
            httpSession.removeAttribute(attributeName);
            object = null;
    }

    public static void removeAttributesForSaleFinished(ShopCart shopCart,
                                                       SaleInProgress saleInProgress) {
        remove(shopCart);
        remove(saleInProgress);
    }

    public static void remove(ShopCart shopCart) {
        remove("scopedTarget.shopCart", shopCart);
    }

    public static void remove(SaleInProgress saleInProgress) {
        remove("scopedTarget.saleInProgress", saleInProgress);
    }

    public static void destroy() {
        httpSession.invalidate();
    }
}
