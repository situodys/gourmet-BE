package kw.ic.backend.domain.restaurant.dto.embbed;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Address {

    private String city;
    private String street;
    private String zipcode;

    @Builder
    public Address(String city, String street, String zipcode) {
        validate(city, street, zipcode);

        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private void validate(String city, String street, String zipcode) {
        isBlank(city);
        isBlank(street);
        isBlank(zipcode);
    }

    private void isBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public void changeCity(String city) {
        isBlank(city);
        this.city = city;
    }

    public void changeStreet(String street) {
        isBlank(street);
        this.street = street;
    }

    public void changeZipcode(String zipcode) {
        isBlank(zipcode);
        this.zipcode = zipcode;
    }
}
