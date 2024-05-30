package online.ondemandtutor.be.repository;


import online.ondemandtutor.be.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByIsDeletedFalse();
    Account findAccountByIdAndIsDeletedFalse(long id);

    Account findAccountByPhone(String phone);
}
