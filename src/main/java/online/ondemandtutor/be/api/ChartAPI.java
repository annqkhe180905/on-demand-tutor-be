package online.ondemandtutor.be.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import online.ondemandtutor.be.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('ADMIN')")
public class ChartAPI {

    @Autowired
    private ChartService chartService;

    @GetMapping("/charts")
    public Map<String, Object> showCharts() {
        Map<String, Object> response = new HashMap<>();
        response.put("moneyByDays", chartService.getMoneyByDays());
        response.put("rolePercentages", chartService.getRolePercentages());
        response.put("tutorPaymentStatus", chartService.countTutorsByPaymentStatus());
        response.put("totalRechargedMoney", chartService.getTotalRechargedMoney());
        return response;
    }
}