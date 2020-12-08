package br.com.utily.ecommerce.controller.domain.admin.trade;

import br.com.utily.ecommerce.controller.handler.exception.InternalServerErrorException;
import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeStatus;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.voucher.EVoucherType;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import br.com.utily.ecommerce.helper.security.LoggedUserHelper;
import br.com.utily.ecommerce.helper.stock.StockHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.helper.view.ViewMessageHelper;
import br.com.utily.ecommerce.helper.voucher.VoucherHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = TradeAdminController.BASE_TRADE_ADMIN_URL)
public class TradeAdminController {

    public static final String BASE_TRADE_ADMIN_URL = "/admin/trades";
    public static final String UPDATE_STATUS_URL = "/{id}/update-status";
    public static final String GENERATE_VOUCHER_URL = "/{tradeId}/generate-voucher";

    private final IDomainService<Trade> tradeDomainService;
    private final IDomainService<Voucher> voucherDomainService;

    private final Trade mockTrade;

    private LoggedUserHelper loggedUserHelper;
    private StockHelper stockHelper;
    private VoucherHelper voucherHelper;

    @Autowired
    public TradeAdminController(@Qualifier("domainService")
                                IDomainService<Trade> tradeDomainService,
                                @Qualifier("domainService")
                                IDomainService<Voucher> voucherDomainService,
                                Trade mockTrade,
                                StockHelper stockHelper,
                                VoucherHelper voucherHelper,
                                LoggedUserHelper loggedUserHelper) {
        this.tradeDomainService = tradeDomainService;
        this.voucherDomainService = voucherDomainService;
        this.mockTrade = mockTrade;
        this.stockHelper = stockHelper;
        this.voucherHelper = voucherHelper;
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping
    public ModelAndView findAll() {
        List<Trade> trades = tradeDomainService.findAll(mockTrade);

        return ModelAndViewHelper.configure(
                EViewType.TRADE_ADMIN,
                EView.LIST,
                trades,
                EModelAttribute.TRADES);
    }

    @PostMapping(path = UPDATE_STATUS_URL)
    public ModelAndView updateStatus(@PathVariable Long id,
                                     ETradeStatus newStatus,
                                     RedirectAttributes redirectAttributes) {
        Optional<Trade> foundTradeOptional = tradeDomainService.findById(id, mockTrade);
        Trade trade = foundTradeOptional.orElseThrow(NotFoundException::new);
        ETradeStatus oldStatus = trade.getStatus();

        trade.setStatus(newStatus);

        String extraMessage = "";
        if (trade.isReceivedItems()) {
            List<StockHistory> stockHistoriesOperation = stockHelper.up(trade.getItems());
            Integer totalAmountReturnedToStocks = stockHistoriesOperation.stream()
                    .map(StockHistory::getAmount)
                    .reduce(0, (Integer::sum));

            extraMessage = "*Obs: foram retornados no total " + totalAmountReturnedToStocks
                    + " itens aos seus respectivos estoques. ";
        }

        Trade updatedTrade = tradeDomainService.save(trade);

        boolean isSuccess = updatedTrade.getStatus() == newStatus;
        String tradeTypeInLowerCase = trade.getType().getDisplayName().toLowerCase();

        String successMessage = (!extraMessage.isEmpty() ? extraMessage : "") +
                "O status da " + tradeTypeInLowerCase + " foi alterado com sucesso de \""
                + oldStatus.getDisplayName() + "\" para \"" + newStatus.getDisplayName() + "\".";
        String errorMessage = "Ocorreu um erro ao tentar alterar o status da " + tradeTypeInLowerCase + " \""
                + oldStatus.getDisplayName() + "\" para \"" + newStatus.getDisplayName() + "\".";

        String message = isSuccess ? successMessage : errorMessage;
        redirectAttributes.addFlashAttribute(EModelAttribute.MESSAGE.getName(), message);
        redirectAttributes.addFlashAttribute(EModelAttribute.IS_SUCCESS_MESSAGE.getName(), isSuccess);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADES_ADMIN);
    }

    @PostMapping(path = GENERATE_VOUCHER_URL)
    public ModelAndView generateVoucher(@PathVariable Long tradeId, RedirectAttributes redirectAttributes) {
        Optional<Trade> tradeOptional = tradeDomainService.findById(tradeId, mockTrade);
        Trade foundTrade = tradeOptional.orElseThrow(NotFoundException::new);

        if (!foundTrade.isReceivedItems()) {
            return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADES_ADMIN);
        }

        if (foundTrade.getItems().isEmpty()) {
            throw new InternalServerErrorException();
        }

        Double voucherValue = foundTrade.calculateTotalBalanceOfItems();

        Voucher savedVoucher = voucherHelper
                .adaptAndSave(voucherValue, EVoucherType.TRADE);

        CustomerVoucher savedCustomerVoucher = voucherHelper
                .adaptAndSave(loggedUserHelper.getLoggedCustomerUser(), savedVoucher);

        foundTrade.changeStatusGeneratedVoucher();
        tradeDomainService.save(foundTrade);

        String message = savedCustomerVoucher.getVoucher().formatToText()
                + " gerado com sucesso para o cliente \"."
                + savedCustomerVoucher.getCustomer().getName() + "\".";

        ViewMessageHelper.configureRedirectMessageWith(message, true, redirectAttributes);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_TRADES_ADMIN);
    }
}
