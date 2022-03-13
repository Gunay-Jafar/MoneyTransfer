package az.unibank.UniTech.repository;

import az.unibank.UniTech.constant.Status;
import az.unibank.UniTech.model.Account;
import az.unibank.UniTech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByStatus(Status status);

}
