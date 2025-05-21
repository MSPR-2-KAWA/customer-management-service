package fr.epsi.service.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor
public class CustomerDTO {

    String username;
    String firstName;
    String lastName;
    String postalCode;
    String city;
    String companyName;

}
