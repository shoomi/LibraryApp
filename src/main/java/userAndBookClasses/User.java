package userAndBookClasses;

public class User {

    private String login;
    private String firstName;
    private String lastName;
    private String dateOfBirthirth;
    private String telephone;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String dateOfBirthirth() {
        return dateOfBirthirth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.dateOfBirthirth = date_of_birth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}