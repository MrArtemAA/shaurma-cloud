package ru.artemaa.shaurma.model;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("shaurmaorder")
public class Order {
    @PrimaryKey
    private UUID id = UUIDs.timeBased();

    private Date placedAt = new Date();

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]1[0-2])(/)([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<ShaurmaUDT> shaurmas = new ArrayList<>();

    public void addDesign(ShaurmaUDT shaurma) {
        shaurmas.add(shaurma);
    }

    @Column("user")
    private UserUDT user;
}
