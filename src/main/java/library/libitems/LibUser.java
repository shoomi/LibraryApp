package library.libitems;

public class LibUser implements User {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String telephone;


    public LibUser() {
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String dateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDate_of_birth(String date_of_birth) {
        this.dateOfBirth = date_of_birth;
    }

    @Override
    public String getTelephone() {
        return telephone;
    }

    @Override
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}