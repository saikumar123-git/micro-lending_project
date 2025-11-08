package com.example.micro_lending.Project.Repository;

import com.example.micro_lending.Project.Entity.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule,Long> {
}
