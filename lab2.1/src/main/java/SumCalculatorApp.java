import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SumCalculatorApp extends JFrame {

    private JLabel infoLabel;
    private JCheckBox threadCheckBox;

    private volatile boolean isThreadRunning = false;

    public SumCalculatorApp() {
        setTitle("Сума ряду");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        infoLabel = new JLabel("Сума ряду: ");
        threadCheckBox = new JCheckBox("Вирахувати суму");

        threadCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (threadCheckBox.isSelected()) {
                    startCalculationThread();
                } else {
                    stopCalculationThread();
                }
            }
        });

        setLayout(new FlowLayout());
        add(infoLabel);
        add(threadCheckBox);
    }

    private void startCalculationThread() {
        if (!isThreadRunning) {
            isThreadRunning = true;
            new Thread(new SumCalculator()).start();
        }
    }

    private void stopCalculationThread() {
        isThreadRunning = false;
    }

    private class SumCalculator implements Runnable {
        @Override
        public void run() {
            double sum = 0.0;
            double epsilon = 1e-5;
            int n = 1;

            while (isThreadRunning) {
                double term = 1.0 / Math.pow(n, 4);
                sum += term;

                if (term < epsilon) {
                    break;
                }

                n++;
            }

            final double finalSum = sum;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    infoLabel.setText("Сума ряду: " + finalSum);
                    threadCheckBox.setSelected(false);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SumCalculatorApp().setVisible(true);
            }
        });
    }
}
