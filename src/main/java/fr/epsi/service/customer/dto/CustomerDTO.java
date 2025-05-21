package fr.epsi.service.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor
public class CustomerDTO {

    String username;
    String firstname;
    String lastname;
    String postalCode;
    String city;
    String companyName;

}
