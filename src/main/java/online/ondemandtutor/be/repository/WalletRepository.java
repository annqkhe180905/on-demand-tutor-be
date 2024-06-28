package online.ondemandtutor.be.repository;

import online.ondemandtutor.be.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    public Wallet findWalletByAccountId(Long userId);
}
