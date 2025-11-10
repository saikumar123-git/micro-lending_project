package com.example.micro_lending.Project.Repository;

import com.example.micro_lending.Project.Entity.BankAccount;
import com.example.micro_lending.Project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByUser(User user);
}
