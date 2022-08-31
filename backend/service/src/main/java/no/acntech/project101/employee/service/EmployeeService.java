package no.acntech.project101.employee.service;

import java.util.List;
import java.util.Optional;

import no.acntech.project101.company.Company;
import no.acntech.project101.employee.Employee;
import no.acntech.project101.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(final Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(final Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        return employeeRepository.findAllByCompanyId(id);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }
}
