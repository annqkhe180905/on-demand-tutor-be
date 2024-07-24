package online.ondemandtutor.be.service;

import online.ondemandtutor.be.repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    @Autowired
    private ChartRepository chartRepository;

    public List<Map<String, Object>> countTutorsByPaymentStatus() {
        return chartRepository.countTutorsByPaymentStatus();
    }

    public List<Map<String, Object>> getMoneyByDays() {
        return chartRepository.findMoneyByDays();
    }

    public List<Map<String, Object>> getRolePercentages() {
        return chartRepository.findRolePercentages();
    }

    public BigDecimal getTotalRechargedMoney() {
        return chartRepository.findTotalRechargedMoney();
    }

    public long getTotalNumberOfAccounts() {
        return chartRepository.count();
    }
}