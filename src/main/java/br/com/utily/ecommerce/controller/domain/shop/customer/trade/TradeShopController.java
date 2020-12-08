package br.com.utily.ecommerce.controller.domain.shop.customer.trade;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.shop.trade.TradeRequestItemDTO;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeType;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.ItemInProgress;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.TradeInProgress;
import br.com.utily.ecommerce.helper.stock.StockHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TradeShopController {

    public static final String TRADE_ORDER_URL = "/orders/{orderId}/trade";
    public static final String TRADE_ITEMS_IN_PROGRESS_URL = "/order/trade/in-progress/items";

    private final IDomainService<Trade> tradeDomainService;
    private final IDomainService<Sale> orderDomainService;
    private final IDomainService<Product> productDomainService;


    private ShopCart shopCart;
    private TradeInProgress tradeInProgress;
    private Sale orderMock;
    private Product productMock;

    private TradeRequestItemDTO tradeRequestItemDTOmock;

    private StockHelper stockHelper;


    @Autowired
    public TradeShopController(@Qualifier("domainService")
                               final IDomainService<Trade> tradeDomainService,
                               @Qualifier("domainService")
                               final IDomainService<Sale> orderDomainService,
                               @Qualifier("domainService")
                               IDomainService<Product> productDomainService) {
        this.tradeDomainService = tradeDomainService;
        this.orderDomainService = orderDomainService;
        this.productDomainService = productDomainService;
    }

    @Autowired
    private void setDependencyEntities(final ShopCart shopCart,
                                       final TradeInProgress tradeInProgress,
                                       final Sale orderMock,
                                       final Product productMock) {
        this.shopCart = shopCart;
        this.tradeInProgress = tradeInProgress;
        this.orderMock = orderMock;
        this.productMock = productMock;
    }

    @Autowired
    private void setDependencyDTOs(TradeRequestItemDTO tradeRequestItemDTOmock) {
        this.tradeRequestItemDTOmock = tradeRequestItemDTOmock;
    }

    @Autowired
    private void setDependencyHelpers(final StockHelper stockHelper) {
        this.stockHelper = stockHelper;
    }

    @PostMapping(path = TRADE_ORDER_URL)
    public ModelAndView showPageToRequest(@PathVariable Long orderId, ETradeType type) {
        Optional<Sale> foundOrderOptional = orderDomainService.findById(orderId, orderMock);
        Sale foundOrder = foundOrderOptional.orElseThrow(NotFoundException::new);
        List<ItemInProgress> previousItems = foundOrder.getItems()
                .stream()
                .map(saleItem -> ItemInProgress.build(saleItem.getProduct(), saleItem.getQuantity()))
                .collect(Collectors.toList());

        tradeInProgress.setSale(foundOrder);
        tradeInProgress.setType(type);
        tradeInProgress.setItems(previousItems);

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put(EModelAttribute.ORDER.getName(), foundOrder);
        modelMap.put(EModelAttribute.TRADE.getName(), tradeInProgress);

        ModelAndView modelAndView = ModelAndViewHelper.configure(EViewType.TRADE_SHOP, EView.NEW);
        ModelAndViewHelper.addModelMapTo(modelAndView, modelMap);

        return modelAndView;
    }

    @PostMapping(path = TRADE_ITEMS_IN_PROGRESS_URL)
    public ModelAndView addItemToRequest(TradeRequestItemDTO tradeRequestItemDTO) {
        Long itemId = tradeRequestItemDTO.getId();
        Integer amount = tradeRequestItemDTO.getAmount();
        String reason = tradeRequestItemDTO.getReason();
        Boolean include = tradeRequestItemDTO.getInclude();

        Optional<Product> foundProductOptional = productDomainService.findById(itemId, productMock);
        Product foundProduct = foundProductOptional.orElseThrow(NotFoundException::new);

        ItemInProgress itemInProgress = ItemInProgress.build(foundProduct, amount, reason, include);

        tradeInProgress.updateItem(itemInProgress);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
    }

    @ModelAttribute("shopCart")
    public ShopCart shopCart() {
        return shopCart;
    }

    @ModelAttribute("tradeInProgress")
    public TradeInProgress tradeInProgress() {
        return tradeInProgress;
    }

    @ModelAttribute("tradeRequestItem")
    public TradeRequestItemDTO tradeRequestItemDTO() {
        return tradeRequestItemDTOmock;
    }

}
