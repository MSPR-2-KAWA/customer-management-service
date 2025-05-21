package fr.epsi.service.customer;

import fr.epsi.service.customer.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/api/customers/{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        return customerService.getById(id);
    }

    @PutMapping("/api/customers/{id}")
    public Customer updateCustomerById(@PathVariable Integer id, @RequestBody CustomerDTO updateCustomerDTO){
        return customerService.update(id, updateCustomerDTO);
    }

    @PostMapping("/api/customers")
    public Customer createCustomer(@RequestBody CustomerDTO createCustomerDTO){
        return customerService.create(createCustomerDTO);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}
