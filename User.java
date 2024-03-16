package lesson.ten.task.two;

import java.io.Serializable;
import java.time.LocalTime;

public class User implements Serializable {
    private final String email;
    private final String password;
    private final String time;

    public User(String email, String password, String time) {
        this.email = email;
        this.password = password;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", time=" + time +
                '}';
    }
}
