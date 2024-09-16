import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Порт для з'єднання

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                // Очікування команд від клієнта
                try {
                    while (true) {
                        String command = (String) inputStream.readObject();
                        System.out.println("Received command: " + command);

                        // Тут можна реалізувати логіку зміни вигляду елементів управління відповідно до команди

                        // Приклад: якщо отримано команду "ShowLabel", відправляємо команду "SHOW_LABEL" клієнту
                        if (command.equals("ShowLabel")) {
                            outputStream.writeObject("SHOW_LABEL");
                            outputStream.flush();
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    clientSocket.close();
                    outputStream.close();
                    inputStream.close();
                    System.out.println("Client disconnected");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
