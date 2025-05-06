package fr.epsi.service.customer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockitoBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Nested
    class getAllCustomers{
        @Test
        void shouldReturnListOfCustomers() throws Exception {
            Customer customer1 = new Customer(1, null, "username1", "firstname1","lastname1", "123","City", "company1");
            Customer customer2 = new Customer(2, null, "username2", "firstname2","lastname2", "123","City", "company2");
            Customer customer3 = new Customer(3, null, "username3", "firstname3","lastname3", "123","City", "company3");

            List<Customer> customerList = List.of(customer1,customer2,customer3);

            when(customerService.getAll()).thenReturn(customerList);

            mockMvc.perform(get("/api/customers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].username").value("username1"))
                    .andExpect(jsonPath("$[1].username").value("username2"))
                    .andExpect(jsonPath("$[2].username").value("username3"));
        }
    }

    @Nested
    class getCustomersById{
        @Test
        void shouldReturnCustomer() throws Exception {
            Customer customer1 = new Customer(1, null, "username1", "firstname1","lastname1", "123","City", "company1");
            when(customerService.getById(1)).thenReturn(customer1);

            mockMvc.perform(get("/api/customers/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("username1"));
        }
        @Test
        void shouldReturnNotFound() throws Exception {
            when(customerService.getById(999)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer 999 not found"));

            mockMvc.perform(get("/api/customers/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Customer 999 not found"));
        }
    }
}
