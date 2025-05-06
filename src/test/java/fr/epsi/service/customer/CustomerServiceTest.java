package fr.epsi.service.customer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Nested
    class getAll{
        @Test
        void shouldReturnListOfCustomers(){
            Customer customer1 = new Customer(1, null, "username1", "firstname1","lastname1", "123","City", "company1");
            Customer customer2 = new Customer(2, null, "username2", "firstname2","lastname2", "123","City", "company2");
            Customer customer3 = new Customer(3, null, "username3", "firstname3","lastname3", "123","City", "company3");

            List<Customer> customerList = List.of(customer1,customer2,customer3);
            when(customerRepository.findAll()).thenReturn(customerList);

            List<Customer> result = customerService.getAll();
            assertEquals(result, customerList);
        }
    }
}
