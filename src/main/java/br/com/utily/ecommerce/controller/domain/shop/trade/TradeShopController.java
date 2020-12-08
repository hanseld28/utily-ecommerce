package br.com.utily.ecommerce.controller.domain.shop.trade;

import br.com.utily.ecommerce.controller.handler.exception.InternalServerErrorException;
import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.shop.trade.TradeRequestItemDTO;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeType;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.ItemInProgress;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.TradeInProgress;
import br.com.utily.ecommerce.helper.proxy.ProxyHelper;
import br.com.utily.ecommerce.helper.trade.TradeHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.helper.view.ViewMessageHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class TradeShopController {

    public static final String TRADE_ORDER_URL = "/orders/{orderId}/trade";
    public static final String TRADE_REQUEST_ITEMS_IN_PROGRESS_URL = "/order/trade/request/in-progress/items";
    public static final String TRADE_REQUEST_FINISH_URL = "/order/trade/request/finish";

    private final IDomainService<Trade> tradeDomainService;
    private final IDomainService<Sale> orderDomainService;
    private final IDomainService<Product> productDomainService;


    private ShopCart shopCart;
    private TradeInProgress tradeInProgress;
    private Sale orderMock;
    private Product productMock;

    private static Sale currentOrder;

    private static UUID hashOperation;

    private TradeRequestItemDTO tradeRequestItemDTOmock;

    private TradeHelper tradeHelper;


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
    private void setDependencyHelpers(final TradeHelper tradeHelper) {
        this.tradeHelper = tradeHelper;
    }

    @PostMapping(path = TRADE_ORDER_URL)
    public ModelAndView showPageToRequest(@PathVariable Long orderId, ETradeType type) {
        Optional<Sale> foundOrderOptional = orderDomainService.findById(orderId, orderMock);
        Sale foundOrder = foundOrderOptional.orElseThrow(NotFoundException::new);
        List<ItemInProgress> previousItems = foundOrder.getItems()
                .stream()
                .map(saleItem -> ItemInProgress.build(saleItem.getProduct(), saleItem.getQuantity()))
                .collect(Collectors.toList());

        tradeInProgress.setOrder(foundOrder);
        tradeInProgress.setType(type);
        tradeInProgress.setItems(previousItems);

        currentOrder = foundOrder;

        return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
    }

    @GetMapping(path = TRADE_REQUEST_ITEMS_IN_PROGRESS_URL)
    public ModelAndView showPageRequestItems() {
        hashOperation = UUID.randomUUID();

        return ModelAndViewHelper.configure(EViewType.TRADE_SHOP, EView.NEW, hashOperation.toString(), EModelAttribute.HASH_OPERATION);
    }

    @PostMapping(path = TRADE_REQUEST_ITEMS_IN_PROGRESS_URL)
    public ModelAndView addItemToRequest(@Valid TradeRequestItemDTO tradeRequestItemDTO,
                                         Errors errors,
                                         RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            ViewMessageHelper.configureRedirectMessageWith(errors, false, redirectAttributes);

            return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
        }

        Long itemId = tradeRequestItemDTO.getId();
        Integer amount = tradeRequestItemDTO.getAmount();
        String reason = tradeRequestItemDTO.getReason();
        Boolean include = tradeRequestItemDTO.isInclude();

        Optional<Product> foundProductOptional = productDomainService.findById(itemId, productMock);
        Product foundProduct = foundProductOptional.orElseThrow(NotFoundException::new);

        ItemInProgress itemInProgress = ItemInProgress.build(foundProduct, amount, reason, include);

        tradeInProgress.updateItem(itemInProgress);

        if (!itemInProgress.isInclude() && tradeInProgress.isAlreadyAddedItem(itemInProgress)) {
            String message = "Item de troca removido da solicitação.";

            ViewMessageHelper.configureRedirectMessageWith(message, true, redirectAttributes);

            return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
        }

        tradeInProgress.updateItem(itemInProgress);

        String message = "Item para troca adicionado a solicitação. " +
                "Você ainda pode adicionar mais itens ou finalizar a solicitação clicando no botão correspondente.";

        ViewMessageHelper.configureRedirectMessageWith(message, true, redirectAttributes);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
    }

    @PostMapping(path = TRADE_REQUEST_FINISH_URL)
    public ModelAndView finishAndSendRequest(String hashOperation, RedirectAttributes redirectAttributes) {
        try {
            if (!TradeShopController.hashOperation.equals(UUID.fromString(hashOperation))) {
                String message = "Ocorreu um erro ao processar a solicitação. Por favor, tente mais tarde.";

                ViewMessageHelper.configureRedirectMessageWith(message, false, redirectAttributes);

                return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_IN_PROGRESS);
            }

            Optional<TradeInProgress> tradeInProgressOptional = ProxyHelper.recoveryEntityFromProxy(tradeInProgress);
            Trade newTrade = tradeHelper.adapt(
                    tradeInProgressOptional.orElseThrow(InternalError::new)
            );

            newTrade.request();
            Trade requestedTrade = tradeDomainService.save(newTrade);

            String message = "Solicitação de "
                    + requestedTrade.getType().getDisplayName().toLowerCase()
                    + " enviada. " + requestedTrade.getStatus().getDisplayName()
                    + ".";

            ViewMessageHelper.configureRedirectMessageWith(
                    message, true,
                    redirectAttributes);

            ViewMessageHelper.addFlashAttributeTo(
                    redirectAttributes,
                    EModelAttribute.TRADE.getName(),
                    requestedTrade);

            return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADE_SHOP_FINISH);

        } catch (Exception exception) {
            throw new InternalServerErrorException();
        }
    }

    @GetMapping(path = TRADE_REQUEST_FINISH_URL)
    public ModelAndView finish() {
        return ModelAndViewHelper.configure(EViewType.TRADE_SHOP, EView.FINISH);
    }

    @ModelAttribute("shopCart")
    public ShopCart shopCart() {
        return shopCart;
    }

    @ModelAttribute("order")
    public Sale currentOrder() {
        return currentOrder;
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
