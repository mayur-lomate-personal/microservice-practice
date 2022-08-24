package com.mayur.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CustomerRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
}
