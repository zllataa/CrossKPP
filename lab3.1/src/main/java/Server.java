import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // Створення серверного сокету
            ServerSocket serverSocket = new ServerSocket(6000); // ласний порт

            System.out.println("Сервер очікує підключення...");

            while (true) {
                // Приймання з'єднання від клієнта
                Socket clientSocket = serverSocket.accept();
                System.out.println("Підключено клієнта з адресою " + clientSocket.getInetAddress());

                // Обробка запиту клієнта
                processRequest(clientSocket);

                // Закриття з'єднання з клієнтом
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processRequest(Socket clientSocket) throws IOException {
        // Отримання даних від клієнта
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String clientData = in.readLine();

        // Обробка отриманих даних (поміняти перший та останній елементи)
        String resultData = clientData.charAt(clientData.length() - 1) + clientData.substring(1, clientData.length() - 1) + clientData.charAt(0);

        // Відправлення відповіді на клієнта
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out.write(resultData + "\n");
        out.flush();
    }
}
