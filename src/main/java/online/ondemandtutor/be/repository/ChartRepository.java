package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ChartRepository extends JpaRepository<Account, Long> {

    @Query("SELECT COUNT(a) as count, CASE WHEN a.nextPaymentDate IS NOT NULL THEN 'paid' ELSE 'notPaid' END as status FROM Account a WHERE a.role = 'TUTOR' GROUP BY CASE WHEN a.nextPaymentDate IS NOT NULL THEN 'paid' ELSE 'notPaid' END")
    List<Map<String, Object>> countTutorsByPaymentStatus();

    @Query("SELECT DATE(a.nextPaymentDate) as day, SUM(w.money) as totalMoney FROM Account a, Wallet w GROUP BY DATE(a.nextPaymentDate)")
    List<Map<String, Object>> findMoneyByDays();

    @Query("SELECT a.role as role, COUNT(a) as count FROM Account a GROUP BY a.role")
    List<Map<String, Object>> findRolePercentages();
}
