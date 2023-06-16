package com.kuliah.restcontroller.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String fname;
    private String lname;
    private String username;
    private String email;
    private String password;
    private String msg;
}
