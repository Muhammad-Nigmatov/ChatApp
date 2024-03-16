package lesson.ten.task.two;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ChatApp {

    static {
        String properties = ChatApp.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", properties);
    }
    private static final Logger logger = Logger.getLogger("New logger");
    private static final File file = new File("src/lesson/ten/task/two/Users.txt");
    private static final File fileMessages = new File("src/lesson/ten/task/two/Messages.txt");

    public static void main(String[] args) throws Exception {
        entering();
    }

    private static void entering() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Authorization\n2. Registration\n3. Exit\n-> ");
        int a = scanner.nextInt();
        switch (a) {
            case 1 -> authorization();
            case 2 -> registration();
            case 3 -> System.exit(1);
        }
    }

    private static void registration() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("login: ");
        String e = scanner.nextLine();
        System.out.print("password: ");
        String p = scanner.nextLine();
        verification(e, p);
    }

    private static void authorization() throws Exception {
        ArrayList<User> users = users();
        Scanner scanner = new Scanner(System.in);
        System.out.print("login: ");
        String e = scanner.nextLine();
        System.out.print("password: ");
        String p = scanner.nextLine();
        for (User user : users) {
            if (user.getEmail().equals(e) && user.getPassword().equals(p)) {
                logger.log(Level.INFO, "enter-> Email: %s".formatted(user.getEmail()));
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Welcome to chat app");
                chatting(user.getEmail());
            }
        }
        System.out.println("Wrong email or password");
        entering();
    }

    private static ArrayList<User> users() throws IOException {
        FileReader fileReader = new FileReader(file);
        StringBuilder a = new StringBuilder();
        int i;
        while ((i = fileReader.read()) != -1) {
            a.append((char) i);
        }
        fileReader.close();
        ArrayList<User> users = new ArrayList<>();
        String[] s = String.valueOf(a).split("\\n");
        for (String string : s) {
            String[] b = string.split(",");
            users.add(new User(b[0], b[1], b[2]));
        }
        return users;
    }

    private static void chatting(String email) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.print("1. Message\n2. Incoming messages\n3. Log out\n4. Exit\n-> ");
            int a = scanner.nextInt();
            switch (a) {
                case 1 -> writeMessage(email);
                case 2 -> incomingMessages();
                case 3 -> entering();
                case 4 -> {
                    logger.log(Level.INFO, "exit-> Email: %s".formatted(email));
                    TimeUnit.SECONDS.sleep(1);
                    System.exit(1);
                }
            }
        }
    }

    private static void writeMessage(String email) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        FileWriter fileWriter = new FileWriter(fileMessages, true);
        fileWriter.append(email).append(",").append(String.valueOf(LocalTime.now())).append(",").append(message).append("\n");
        fileWriter.close();
    }

    private static void incomingMessages() throws IOException {
        FileReader fileReader = new FileReader(fileMessages);
        StringBuilder a = new StringBuilder();
        int i;
        while ((i = fileReader.read()) != -1) {
            a.append((char) i);
        }
        fileReader.close();
        String[] s = String.valueOf(a).split("\\n");
        for (String string : s) {
            String[] b = string.split(",");
            System.out.printf("User: %s -- %s\n%s".formatted(b[0], b[1], b[2]));
        }
    }

    private static void verification(String e, String p) throws Exception {
        if (e == null)
            throw new RuntimeException("Login can not be null!");
        Pattern pattern = Pattern.compile("([\\w\\d]+)@([\\w-]+)\\.(\\w{2,4})");
        if (!(pattern.matcher(e).matches())) {
            System.out.println("Wrong email!");
            registration();
        } else {
            User user = new User(e, p, String.valueOf(LocalDateTime.now()));
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append(e).append(",").append(p).append(",").append(String.valueOf(user.getTime())).append("\n");
            fileWriter.close();
            logger.log(Level.INFO, "user created-> Email: %s".formatted(user.getEmail()));
            TimeUnit.SECONDS.sleep(1);
            entering();
        }
    }
}
