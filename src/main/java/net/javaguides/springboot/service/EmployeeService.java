package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There's no Employee with id: " + id));
    }

    public Employee updateEmployee(long id, Employee employeeDetails) {
        try {
            Employee updateEmployee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("There's no Employee with id: " + id));

            updateEmployee.setFirstName(employeeDetails.getFirstName());
            updateEmployee.setLastName(employeeDetails.getLastName());
            updateEmployee.setEmailId(employeeDetails.getEmailId());

            return employeeRepository.save(updateEmployee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to update employee");
        }
    }

    public void deleteEmployee(long id) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Couldn't find the employee with ID of: " + id));
            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete employee");
        }
    }
}

