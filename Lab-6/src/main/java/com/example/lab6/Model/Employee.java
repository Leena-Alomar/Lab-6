package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message= "The ID Cannot Be Empty !")
    @Size(min=2,message ="The ID Should Be With Min Length of 2 ")
    private String id;
    @NotEmpty(message= "The Name Cannot Be Empty !")
    @Size(min=4,message ="The Name Should Be With Min Length of 4 ")
    @Pattern(regexp = "^[a-zA-Z]*$" ,message="The Name Must Contains Only Charaters")
    private String name;
    @Email(message = "The Email Must Be A Valid Email")
    private String email;
    @Pattern(regexp = "^05\\d{8}",message="The Phone Number Must Be Size Of 10 and It Should Start With 05")
    private String phoneNumber;
    @NotNull(message="The Age Cannot Be Null")
    @Pattern(regexp = "^[0-9]+$", message = "The Age Must Be a Number")
    @Min(value = 25,message = "The Age Must Be At Least 25")
    private int age;
    @NotEmpty(message="The Position Must Not Be Empty")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "The Position Must Be supervisor or coordinator ")
    private String position;
    @AssertFalse(message = "The Value Must Be False")
    private boolean onLeave;
    @NotNull(message = "The Hire Date Cannot Be Null")
    @PastOrPresent(message = "The Hire Date Must Be on The Past or Present")
    @JsonFormat
    private LocalDate hireDate;
    @NotNull(message = "The Annual Leave Cannot Be Null")
    @Positive(message = "The Value Must Be Positive")
    private int AnnualLeave;
}
