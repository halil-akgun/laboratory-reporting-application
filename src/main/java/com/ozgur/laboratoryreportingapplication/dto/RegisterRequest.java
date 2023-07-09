package com.ozgur.laboratoryreportingapplication.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ozgur.laboratoryreportingapplication.dto.annotation.NotAdmin;
import com.ozgur.laboratoryreportingapplication.dto.annotation.UniqueHospitalIdNumber;
import com.ozgur.laboratoryreportingapplication.dto.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterRequest {

/*
    UniqueUsername and UniqueHospitalIdNumber checks could not be made when a request did not pass
    other validations. In this way, the user will not have to correct the form for the 2nd time.

    For example: surname=null and the entered username exists in the db. In this case, if there is no
    UniqueUsername annotation, only the error message about the username will be displayed.
*/

    @UniqueUsername
    @NotAdmin
    @NotBlank(message = "This field cannot be left blank")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters.")
    @Size(min = 2, max = 20, message = "Username '${validatedValue}' must be between {min} and {max} chars long")
    private String username;

    @NotBlank(message = "This field cannot be left blank")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    @Size(min = 2, max = 20, message = "Name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    @NotBlank(message = "This field cannot be left blank")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters.")
    @Size(min = 2, max = 20, message = "Surname '${validatedValue}' must be between {min} and {max} chars long")
    private String surname;

    @UniqueHospitalIdNumber
    @NotBlank(message = "This field cannot be left blank")
    @Pattern(regexp = "\\d+", message = "Hospital ID Number must consist of digits only.")
    @Size(min = 7, max = 7, message = "Hospital ID Number '${validatedValue}' must be {max} chars long")
    private String hospitalIdNumber;

    @NotBlank(message = "This field cannot be left blank")
    @Size(min = 8, max = 30, message = "Password '${validatedValue}' must be between {min} and {max} chars long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, uppercase letter and digit.")
    private String password;

}