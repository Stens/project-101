package no.acntech.project101.employee.repository;

import no.acntech.project101.company.Company;
import no.acntech.project101.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByCompanyId(Long id);
}
