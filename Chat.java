package lesson.ten.task.two;

import java.sql.Time;

public class Chat {
    private User user;
    private String massage;
    private Time time;

    public Chat(User user, String massage, Time time) {
        this.user = user;
        this.massage = massage;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "user=" + user +
                ", massage='" + massage + '\'' +
                ", time=" + time +
                '}';
    }
}
