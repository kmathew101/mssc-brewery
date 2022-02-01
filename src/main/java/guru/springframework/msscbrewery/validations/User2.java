package guru.springframework.msscbrewery.validations;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * In this example we are using @GroupSequence on our bean class to redefine the default group.
 */
@GroupSequence({User2.class, GroupUserName.class, GroupAddress.class})
public class User2 {
    @NotNull(groups = GroupUserName.class)
    String firstName;
    @NotNull(groups = GroupUserName.class)
    String lastName;

    @NotNull(groups = GroupAddress.class)
    String streetAddress;
    @NotNull(groups = GroupAddress.class)
    String country;
    @NotNull(groups = GroupAddress.class)
    @Size(min = 5, groups = GroupAddress.class)
    String zipCode;

    @NotNull
    String userId;

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress () {
        return streetAddress;
    }

    public void setStreetAddress (String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry () {
        return country;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public String getZipCode () {
        return zipCode;
    }

    public void setZipCode (String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }
}