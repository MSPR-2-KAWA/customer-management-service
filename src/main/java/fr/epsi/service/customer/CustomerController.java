package fr.epsi.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    @GetMapping("/customer")
    public String HelloWorld() {
        return "Hello world";
    }
}
