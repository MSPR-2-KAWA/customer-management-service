package fr.epsi.service.customer;

import fr.epsi.service.customer.dto.CustomerDTO;
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

    public Customer update(Integer id, CustomerDTO updateCustomerDTO) {
        Customer dbCustomer = getById(id);
        return customerRepository.save(new Customer(
                dbCustomer.getId(),
                dbCustomer.getCreatedAt(),
                updateCustomerDTO.getUsername(),
                updateCustomerDTO.getFirstname(),
                updateCustomerDTO.getLastname(),
                updateCustomerDTO.getPostalCode(),
                updateCustomerDTO.getCity(),
                updateCustomerDTO.getCompanyName()
        ));
    }

    public Customer create(CustomerDTO createCustomerDTO){
        return customerRepository.save(new Customer(
                createCustomerDTO.getUsername(),
                createCustomerDTO.getFirstname(),
                createCustomerDTO.getLastname(),
                createCustomerDTO.getPostalCode(),
                createCustomerDTO.getCity(),
                createCustomerDTO.getCompanyName()
        ));
    }

    public void delete(Integer id){
        Customer dbCustomer = getById(id);

        customerRepository.delete(dbCustomer);
    }
}
