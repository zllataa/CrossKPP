import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIProgram {
    private static JFrame frame;
    private static JLabel label;

    public static void main(String[] args) {
        // Створення головного вікна
        frame = new JFrame("GUI Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout(10, 10)); // Додано відступи між компонентами
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Додано рамку

        // Панель для елементів управління
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setBorder(BorderFactory.createEtchedBorder()); // Додано рамку
        JCheckBox maximizeSwitch = new JCheckBox("Вікно - максимізоване");
        JCheckBox labelVisibilitySwitch = new JCheckBox("Мітка - видима");

        maximizeSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMaximize(maximizeSwitch.isSelected());
            }
        });

        labelVisibilitySwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabelVisibility(labelVisibilitySwitch.isSelected());
            }
        });

        leftPanel.add(maximizeSwitch);
        leftPanel.add(labelVisibilitySwitch);

        // Мітка
        label = new JLabel("Мітка");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVisible(false);

        // Панель для кольору мітки
        JPanel rightPanel = new JPanel(new GridLayout(4, 1));
        rightPanel.setBorder(BorderFactory.createEtchedBorder()); // Додано рамку
        JLabel colorLabel = new JLabel("Колір шрифту мітки");
        rightPanel.add(colorLabel);

        ButtonGroup colorGroup = new ButtonGroup();
        JRadioButton standardColorButton = new JRadioButton("Стандартний");
        JRadioButton redColorButton = new JRadioButton("Червоний");
        JRadioButton blueColorButton = new JRadioButton("Синій");

        standardColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabelColor(Color.BLACK);
            }
        });

        redColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabelColor(Color.RED);
            }
        });

        blueColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabelColor(Color.BLUE);
            }
        });

        colorGroup.add(standardColorButton);
        colorGroup.add(redColorButton);
        colorGroup.add(blueColorButton);

        rightPanel.add(standardColorButton);
        rightPanel.add(redColorButton);
        rightPanel.add(blueColorButton);

        // Додавання елементів до головного вікна
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(label, BorderLayout.SOUTH); // Переміщено під перший блок

        // Налаштування вікна та відображення його
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void updateMaximize(boolean isMaximized) {
        frame.setExtendedState(isMaximized ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
    }

    private static void updateLabelVisibility(boolean isVisible) {
        label.setVisible(isVisible);
    }

    private static void updateLabelColor(Color color) {
        label.setForeground(color);
    }
}
