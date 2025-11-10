package com.example.micro_lending.Project.Repository;


import com.example.micro_lending.Project.Entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
}
