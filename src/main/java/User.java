import java.util.Map;

/*
Объект куда будем маппить данные с ресурса https://reqres.in/
*/

public class User
{
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarSource;

    public User(Map data) {
        this.id = (Integer) data.get("id");
        this.email = (String) data.get("email");
        this.firstName = (String) data.get("first_name");
        this.lastName = (String) data.get("last_name");
        this.avatarSource = (String) data.get("avatar");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAvatarSource() {
        return avatarSource;
    }

    public void setAvatarSource(String avatarSource) {
        this.avatarSource = avatarSource;
    }

    //Метод для проверки полей объекта на notNull
    public boolean checkData () {
        if (id != 0 & email != null & firstName != null & lastName != null & avatarSource != null){
            return true;
        } else {
            return false;
        }
    }

    //Сравниваем два объекта типа User
    public boolean userEquals (User user){
        if (id == user.getId()
                & email.equals(user.getEmail())
                & firstName.equals(user.getFirstName())
                & lastName.equals(user.getLastName())
                & avatarSource.equals(user.getAvatarSource())){
            return true;
        } else {
            return false;
        }
    }
}
