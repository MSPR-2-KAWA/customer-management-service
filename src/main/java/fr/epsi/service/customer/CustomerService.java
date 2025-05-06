package fr.epsi.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

     public List<Customer> getAll(){
         return customerRepository.findAll();
     }
}
