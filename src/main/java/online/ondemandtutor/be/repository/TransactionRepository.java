package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Transaction;
import online.ondemandtutor.be.enums.TransactionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Transaction findTransactionById(Long id);

    @Query("SELECT t FROM Transaction t WHERE t.from.id = :walletId OR t.to.id = :walletId")
    List<Transaction> findTransactionsByFrom_IdOrTo_Id(Long walletId);

    //    public List<Transaction> findTransactionsByWalletId (Long id);

    List<Transaction> findTransactionByTransactionType(TransactionEnum status);
}
