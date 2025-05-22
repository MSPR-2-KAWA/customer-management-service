package fr.epsi.service.customer;

import fr.epsi.service.customer.dto.CustomerDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Nested
    class getAll {
        @Test
        void shouldReturnListOfCustomers() {
            Customer customer1 = new Customer(1, null, "username1", "firstname1", "lastname1", "123", "City", "company1");
            Customer customer2 = new Customer(2, null, "username2", "firstname2", "lastname2", "123", "City", "company2");
            Customer customer3 = new Customer(3, null, "username3", "firstname3", "lastname3", "123", "City", "company3");

            List<Customer> customerList = List.of(customer1, customer2, customer3);
            when(customerRepository.findAll()).thenReturn(customerList);

            List<Customer> result = customerService.getAll();
            assertEquals(result, customerList);
        }
    }

    @Nested
    class getById {
        @Test
        void shouldReturnCustomer() {
            Customer customer1 = new Customer(1, null, "username1", "firstname1", "lastname1", "123", "City", "company1");

            when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));

            Customer result = customerService.getById(1);

            assertEquals(result, customer1);
        }
        @Test
        void shouldReturnNotFound() {

            when(customerRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> {
                customerService.getById(1);
            });
        }
    }

    @Nested
    class update{
        @Test
        void shouldUpdateCustomer() {

            Integer id = 1;
            Customer existingCustomer = new Customer(id, LocalDateTime.now(), "oldUsername", "oldFirst", "oldLast", "00000", "OldCity", "OldCompany");
            CustomerDto updateDto = new CustomerDto("newUsername", "newFirst", "newLast", "12345", "NewCity", "NewCompany");
            Customer expectedCustomer = new Customer(id, existingCustomer.getCreatedAt(), "newUsername", "newFirst", "newLast", "12345", "NewCity", "NewCompany");

            when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
            when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);

            Customer result = customerService.update(id, updateDto);

            assertEquals(expectedCustomer.getId(), result.getId());
            assertEquals(expectedCustomer.getCreatedAt(), result.getCreatedAt());
            assertEquals(expectedCustomer.getUsername(), result.getUsername());
            assertEquals(expectedCustomer.getFirstName(), result.getFirstName());
            assertEquals(expectedCustomer.getLastName(), result.getLastName());
            assertEquals(expectedCustomer.getPostalCode(), result.getPostalCode());
            assertEquals(expectedCustomer.getCity(), result.getCity());
            assertEquals(expectedCustomer.getCompanyName(), result.getCompanyName());
        }
    }

    @Nested
    class create{
        @Test
        void shouldCreateCustomer() {

            Integer id = 1;
            Customer expectedCustomer = new Customer(id, LocalDateTime.now(), "newUsername", "newFirst", "newLast", "12345", "NewCity", "NewCompany");
            CustomerDto createCustomerDto = new CustomerDto("newUsername", "newFirst", "newLast", "12345", "NewCity", "NewCompany");
            Customer expectSave = new Customer(
                    createCustomerDto.getUsername(),
                    createCustomerDto.getFirstname(),
                    createCustomerDto.getLastname(),
                    createCustomerDto.getPostalCode(),
                    createCustomerDto.getCity(),
                    createCustomerDto.getCompanyName()
            );


            when(customerRepository.save(expectSave)).thenReturn(expectedCustomer);

            Customer result = customerService.create(createCustomerDto);

            assertEquals(expectedCustomer.getId(), result.getId());
            assertEquals(expectedCustomer.getCreatedAt(), result.getCreatedAt());
            assertEquals(expectedCustomer.getUsername(), result.getUsername());
            assertEquals(expectedCustomer.getFirstName(), result.getFirstName());
            assertEquals(expectedCustomer.getLastName(), result.getLastName());
            assertEquals(expectedCustomer.getPostalCode(), result.getPostalCode());
            assertEquals(expectedCustomer.getCity(), result.getCity());
            assertEquals(expectedCustomer.getCompanyName(), result.getCompanyName());
        }
    }
    @Nested
    class delete{
        @Test
        void shouldDeleteCustomer() {
            Integer id = 1;
            Customer existingCustomer = new Customer(id, LocalDateTime.now(), "oldUsername", "oldFirst", "oldLast", "00000", "OldCity", "OldCompany");

            when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));

            customerService.delete(1);

            verify(customerRepository, times(1)).delete(existingCustomer);
        }
    }
}
