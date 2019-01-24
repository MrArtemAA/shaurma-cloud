package ru.artemaa.shaurma.entity.jpa;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "Shaurma_Order")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private Date placedAt;

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

    @ManyToMany(targetEntity = Shaurma.class)
    private List<Shaurma> shaurmas = new ArrayList<>();

    @ManyToOne
    private User user;

    public void addDesign(Shaurma shaurma) {
        shaurmas.add(shaurma);
    }

    void placedAt() {
        placedAt = new Date();
    }
}
