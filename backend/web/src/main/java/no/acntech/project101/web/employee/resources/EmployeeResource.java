package no.acntech.project101.web.employee.resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.acntech.project101.employee.Employee;
import no.acntech.project101.employee.service.EmployeeService;
import no.acntech.project101.web.employee.resources.converter.EmployeeConverter;
import no.acntech.project101.web.employee.resources.converter.EmployeeDtoConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("employees")
public class EmployeeResource {
    private final EmployeeService employeeService;
    private final EmployeeDtoConverter employeDtoConverter;
    private final EmployeeConverter employeeConverter;


    public EmployeeResource(EmployeeService employeeService, EmployeeConverter employeeConverter, EmployeeDtoConverter employeeDtoConverter) {
        this.employeeService = employeeService;
        this.employeDtoConverter = employeeDtoConverter;
        this.employeeConverter = employeeConverter;
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {

        //return ResponseEntity.ok();
        Stream<EmployeeDto> employeeDtoStream = this.employeeService.findAll().stream().map(this.employeDtoConverter::convert);
        return  ResponseEntity.ok(employeeDtoStream.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable final Long id) {
        Optional<Employee> byId = this.employeeService.findById(id);
        return byId.map(employee -> ResponseEntity.ok(this.employeDtoConverter.convert(employee))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity createEmployee(@RequestBody @Validated final EmployeeDto employeeDto) {
        Employee save = this.employeeService.save(this.employeeConverter.convert(employeeDto));
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable final Long id) {

        Optional<Employee> byId = this.employeeService.findById(id);
        if (byId.isPresent()) {
            this.employeeService.delete(id);
            return ResponseEntity.accepted().build();
        }else {

            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateEmployee(@RequestBody @Validated EmployeeDto newEmployeeDto) {
        Optional<Employee> byId = this.employeeService.findById(newEmployeeDto.id());
        if(byId.isPresent()) {
            final var byIdfinal = byId.get();
            byIdfinal.setCompanyId(newEmployeeDto.companyId());
            byIdfinal.setDateOfBirth(newEmployeeDto.dateOfBirth());
            byIdfinal.setFirstName(newEmployeeDto.firstName());
            byIdfinal.setLastName(newEmployeeDto.lastName());
            this.employeeService.save(byIdfinal);
            return ResponseEntity.ok(this.employeDtoConverter.convert(byIdfinal));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all-employees/{companyId}")
    public ResponseEntity<List<EmployeeDto>> findEmployeesByCompany(@PathVariable Long id) {
        Stream<EmployeeDto> employeeDtoStream = this.employeeService.getEmployeesByCompanyId(id).stream().map(this.employeDtoConverter::convert);
        return ResponseEntity.ok(employeeDtoStream.toList());
    }
}
