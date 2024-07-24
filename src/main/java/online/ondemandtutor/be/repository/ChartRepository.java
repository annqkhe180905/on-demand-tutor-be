package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface ChartRepository extends JpaRepository<Account, Long> {

    @Query("SELECT COUNT(a) as count, CASE WHEN a.nextPaymentDate IS NOT NULL THEN 'paid' ELSE 'notPaid' END as status FROM Account a WHERE a.role = 'TUTOR' GROUP BY CASE WHEN a.nextPaymentDate IS NOT NULL THEN 'paid' ELSE 'notPaid' END")
    List<Map<String, Object>> countTutorsByPaymentStatus();

    @Query("SELECT new map(DATE(t.transactionDate) as date, SUM(t.amount) as total) FROM Transaction t WHERE t.description = 'Recharge' GROUP BY DATE(t.transactionDate)")
    List<Map<String, Object>> findMoneyByDays();

    @Query("SELECT a.role as role, COUNT(a) as count FROM Account a GROUP BY a.role")
    List<Map<String, Object>> findRolePercentages();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.description = 'Recharge'")
    BigDecimal findTotalRechargedMoney();

    long count();
}
