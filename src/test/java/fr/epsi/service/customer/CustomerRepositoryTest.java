package fr.epsi.service.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findAll(){
        Customer customer1 = new Customer(1, null, "username1", "firstname1","lastname1", "123","City", "company1");
        Customer customer2 = new Customer(2, null, "username2", "firstname2","lastname2", "123","City", "company2");
        Customer customer3 = new Customer(3, null, "username3", "firstname3","lastname3", "123","City", "company3");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(List.of(customer1, customer2, customer3));

    }
}
