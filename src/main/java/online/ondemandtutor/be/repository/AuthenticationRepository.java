package online.ondemandtutor.be.repository;


import online.ondemandtutor.be.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByIsDeletedFalse();
    Account findAccountByIdAndIsDeletedFalse(long id);



    Account findAccountByEmail(String email);
}
