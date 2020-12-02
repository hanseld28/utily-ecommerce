package br.com.utily.ecommerce.controller.domain.admin;

import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = AdminDashboardController.BASE_ADMIN_URL)
public class AdminDashboardController {

    public static final String BASE_ADMIN_URL = "/admin";
    public static final String DASHBOARD_URL = "/dashboard";


    @GetMapping(path = DASHBOARD_URL)
    public ModelAndView showDashboard() {
        return ModelAndViewHelper.configure(
                EViewType.DASHBOARD_ADMIN,
                EView.ADMIN_DASHBOARD);
    }
}
