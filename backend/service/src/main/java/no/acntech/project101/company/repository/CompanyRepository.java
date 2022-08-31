package no.acntech.project101.company.repository;

import no.acntech.project101.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import no.acntech.project101.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findAllByCompanyEmployees(Employee employee);
}
