package no.acntech.project101.web.employee.resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import no.acntech.project101.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("employees")
public class EmployeeResource {

    //TODO The constructor needs to accept the required dependencies and assign them to class variables

    private ArrayList<EmployeeDto> employees= new ArrayList<EmployeeDto>();

    public EmployeeResource() {
        LocalDate localDate = LocalDate.of(1996,6,23);

        this.employees.add(
                new EmployeeDto(1L, "Kris", "Ste", localDate, 1L)
        );
        this.employees.add(
                new EmployeeDto(2L, "Stian", "Ste", localDate, 1L)
        );
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(this.employees);
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable final Long id) {
         final var employee = this.employees.stream().filter(employeeDto ->
                 employeeDto.id().equals(id)).findFirst().orElse(null);
         System.out.println(employee);
         return ResponseEntity.ok(employee);
    }
    @PostMapping
    public ResponseEntity createEmployee(@RequestBody @Validated final EmployeeDto employeeDto) {
        this.employees.add(employeeDto);
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employeeDto.id())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployee(@PathVariable final Long id) {
        final var employeeToDelete = this.employees.stream().filter(employeeDto -> employeeDto.id().equals(id)).findFirst().orElse(null);
        if(employeeToDelete == null) {
            return ResponseEntity.notFound().build();
        }else {


            this.employees.remove(employeeToDelete);
            return ResponseEntity.accepted().build();
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateEmployee(@RequestBody @Validated EmployeeDto newEmployeeDto) {
        final var employeeToUpdate = this.employees.stream().filter(ed -> ed.companyId().equals(newEmployeeDto.companyId())).findFirst().orElse(null);
        if(employeeToUpdate == null) {
            return ResponseEntity.notFound().build();
        }else {

            this.employees.remove(employeeToUpdate);
            final var i = this.employees.indexOf(employeeToUpdate);
            this.employees.add(newEmployeeDto);
            return ResponseEntity.ok(newEmployeeDto);
        }
    }
}
