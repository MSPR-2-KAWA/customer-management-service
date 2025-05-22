package fr.epsi.service.customer;

import fr.epsi.service.customer.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getById(Integer id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + id + " not found"));
    }

    public Customer update(Integer id, CustomerDto updateCustomerDto) {
        Customer dbCustomer = getById(id);
        return customerRepository.save(new Customer(
                dbCustomer.getId(),
                dbCustomer.getCreatedAt(),
                updateCustomerDto.getUsername(),
                updateCustomerDto.getFirstname(),
                updateCustomerDto.getLastname(),
                updateCustomerDto.getPostalCode(),
                updateCustomerDto.getCity(),
                updateCustomerDto.getCompanyName()
        ));
    }

    public Customer create(CustomerDto createCustomerDto){
        return customerRepository.save(new Customer(
                createCustomerDto.getUsername(),
                createCustomerDto.getFirstname(),
                createCustomerDto.getLastname(),
                createCustomerDto.getPostalCode(),
                createCustomerDto.getCity(),
                createCustomerDto.getCompanyName()
        ));
    }

    public void delete(Integer id){
        Customer dbCustomer = getById(id);

        customerRepository.delete(dbCustomer);
    }
}
