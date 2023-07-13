package models;

public class User {

    private String email;
    private String name;
    private String passwordHash;
    private String phoneNumber;
    private String city;
    private String role;

    public User() {
    }

    public User(String email, String name, String passwordHash, String phoneNumber, String city, String role) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
