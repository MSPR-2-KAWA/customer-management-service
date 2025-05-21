package fr.epsi.service.customer;

import fr.epsi.service.customer.dto.CustomerDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Nested
    class updateCustomerById{
        @Test
        void shouldUpdateCustomerAndReturnIt() throws Exception {
            Integer id = 1;
            Customer updatedCustomer = new Customer(id, LocalDateTime.now(), "newUsername", "newFirst", "newLast", "12345", "NewCity", "NewCompany");

            when(customerService.update(eq(id), any(CustomerDTO.class))).thenReturn(updatedCustomer);

             mockMvc.perform(put("/api/customers/1")
                            .contentType("application/json")
                            .content("""
                                    {
                                        "username": "newUsername",
                                        "firstName": "newFirst",
                                        "lastName": "newLast",
                                        "postalCode": "12345",
                                        "city": "NewCity",
                                        "companyName": "NewCompany"
                                    }
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username").value("newUsername"))
                    .andExpect(jsonPath("$.firstName").value("newFirst"))
                    .andExpect(jsonPath("$.lastName").value("newLast"))
                    .andExpect(jsonPath("$.postalCode").value("12345"))
                    .andExpect(jsonPath("$.city").value("NewCity"))
                    .andExpect(jsonPath("$.companyName").value("NewCompany"));
        }
        @Test
        void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {
            Integer id = 999;
            when(customerService.update(eq(id), any(CustomerDTO.class)))
                    .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer 999 not found"));

            mockMvc.perform(put("/api/customers/999")
                            .contentType("application/json")
                            .content("""
                                {
                                    "username": "username",
                                    "firstName": "first",
                                    "lastName": "last",
                                    "postalCode": "00000",
                                    "city": "City",
                                    "companyName": "Company"
                                }
                                """))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Customer 999 not found"));
        }
    }
}
