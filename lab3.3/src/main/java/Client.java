import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static JFrame frame;
    private static JLabel label;
    private static JButton button;

    public static void main(String[] args) {
        frame = new JFrame("Client Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        label = new JLabel("This is a label");
        button = new JButton("Click me");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Дії, які виконуються при натисканні кнопки
                System.out.println("Button clicked");
            }
        });

        // Додавання контекстного меню для кнопки
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Show Label");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommandToServer("ShowLabel");
            }
        });
        popupMenu.add(menuItem);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(button, e.getX(), e.getY());
                }
            }
        });

        frame.add(label);
        frame.add(button);

        frame.setVisible(true);

        // Підключення до сервера
        connectToServer();
    }

    private static void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Очікування команд від сервера
            try {
                while (true) {
                    String command = (String) inputStream.readObject();
                    System.out.println("Received command from server: " + command);

                    // Логіка відображення елементів управління відповідно до команди
                    if (command.equals("SHOW_LABEL")) {
                        showLabel();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                socket.close();
                outputStream.close();
                inputStream.close();
                System.out.println("Disconnected from server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommandToServer(String command) {
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Відправка команди серверу
            outputStream.writeObject(command);
            outputStream.flush();

            socket.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showLabel() {
        // Логіка відображення мітки
        label.setVisible(true);
    }
}
