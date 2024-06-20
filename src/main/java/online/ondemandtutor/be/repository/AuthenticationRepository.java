package online.ondemandtutor.be.repository;


import online.ondemandtutor.be.entity.Account;
import online.ondemandtutor.be.enums.RoleEnum;
import online.ondemandtutor.be.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthenticationRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByIsDeletedFalse();
    Account findAccountByIdAndIsDeletedFalse(Long id);

    Account findAccountByIdAndIsDeletedTrue(Long id);
    List<Account> findAccountByIsDeletedTrue();

    Account findAccountById(Long id);

    List<Account> findAll();

    Account findAccountByEmail(String email);

    List<Account> findAccountByRole(RoleEnum role);

    List<Account> findAccountByRequestStatus(StatusEnum statusEnum);

}
