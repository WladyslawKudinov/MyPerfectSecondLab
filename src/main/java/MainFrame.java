import calculatestatistics.*;
import calculator.Calculator;
import read.ExcelReader;
import write.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class MainFrame {
    private static File selectedFile;

    public static void showFrame() throws IOException {
        JFrame frame = new JFrame("Excel Статистика");
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Имя листа:");
        JComboBox<String> sheetNames = new JComboBox<>();
        sheetNames.setPreferredSize(new Dimension(200, 25));

        JButton chooseButton = new JButton("Выбрать файл");
        JButton readButton = new JButton("Cчитать выбранный лист");
        JButton calculateButton = new JButton("Произвести статистические расчёты");
        JButton exportButton = new JButton("Экспортировать результаты");
        JButton exitButton = new JButton("Выйти");

        Calculator calculator = new Calculator();

        // Панель выбора файла и листа
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(3, 2, 10, 10));
        filePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        filePanel.add(new JLabel("Выбрать файл:"));
        filePanel.add(chooseButton);
        filePanel.add(label);
        filePanel.add(sheetNames);
        filePanel.add(readButton);

        // Панель кнопок управления
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(calculateButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(exitButton);

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(filePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = null;
                try {
                    fileChooser = new JFileChooser(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile());
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    List<String> names = null;
                    try {
                        names = ExcelReader.getSheetNames(selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    sheetNames.removeAllItems();
                    for (String name : names) sheetNames.addItem(name);
                }
            }
        });

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = (String) sheetNames.getSelectedItem();
                try {
                    if (selectedFile == null) {
                        throw new IllegalArgumentException("Файл не выбран.");
                    }
                    calculator.read(selectedFile.getAbsolutePath(), name);
                    JOptionPane.showMessageDialog(frame, "Вариант считан", "Статус", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculator.calculateAll();
                calculator.getAllResults();
                JOptionPane.showMessageDialog(frame, "Расчёты готовы", "Статус", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Экспорт расчётов...");
                    calculator.write();
                    JOptionPane.showMessageDialog(frame, "Файл OutputStatistics.xlsx экспортирован", "Статус", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    System.err.println("Ошибка при экспорте файла: " + ex.getMessage());
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Вы уверены, что хотите выйти?", "Подтверждение выхода", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(500, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
