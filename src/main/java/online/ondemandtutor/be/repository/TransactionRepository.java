package online.ondemandtutor.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import online.ondemandtutor.be.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
