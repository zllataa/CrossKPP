import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // Підключення до сервера
            Socket socket = new Socket("localhost", 12345); // Змініть на власний IP та порт сервера

            // Отримання введення від користувача
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введіть рядок тексту: ");
            String inputText = userInput.readLine();

            // Надсилання даних на сервер
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(inputText + "\n");
            out.flush();

            // Отримання відповіді від сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverResponse = in.readLine();

            // Виведення результату
            System.out.println("Результат: " + serverResponse);

            // Закриття з'єднання
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
