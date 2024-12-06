import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MainFrame {
    Institute institute;

    Object[][] data = new Object[0][5];

    public MainFrame() {
        institute = new Institute("ИКТЗИ");

        data = getData();

        String[] columnNames = {"№", "Институт", "Номер здания", "Номер аудитории", "Число мест"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(1000, 700));

        JFrame frame = new JFrame("Институт");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(false);

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.darkGray);
        tablePanel.add(tableScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.setPreferredSize(new Dimension(900, 200));

        buttons(buttonPanel, table);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(Color.darkGray);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private void buttons(JPanel buttonPanel, JTable table) {

        //КНОПКА ДОБАВЛЕНИЯ ЗДАНИЯ
        JButton addBuildingButton = new JButton("Добавить здание");
        addBuildingButton.addActionListener(e -> {
            String buildingNumberInput = JOptionPane.showInputDialog(null, "Введите номер здания:");
            if (buildingNumberInput != null && !buildingNumberInput.isEmpty()) {
                institute.add(Integer.parseInt(buildingNumberInput));
                data = getData();
                updateTable(table);
            }
        });
        buttonPanel.add(addBuildingButton);

        //КНОПКА ДОБАВЛЕНИЯ АУДИТОРИИ
        JButton addAuditoriumButton = new JButton("Добавить аудиторию");
        addAuditoriumButton.addActionListener(_ -> {
            String buildingNumberInput = JOptionPane.showInputDialog(null, "Введите номер здания для добавления аудитории:");
            String classroomNumberInput = JOptionPane.showInputDialog(null, "Введите номер аудитории:");
            String capacityInput = JOptionPane.showInputDialog(null, "Введите число мест в аудитории:");
            if (buildingNumberInput != null && classroomNumberInput != null && capacityInput != null) {
                Building building = institute.find(Integer.parseInt(buildingNumberInput));
                if (building != null) {
                    building.add(Integer.parseInt(classroomNumberInput), Integer.parseInt(capacityInput));
                    data = getData();
                    updateTable(table);
                } else {
                    JOptionPane.showMessageDialog(null, "Здание с таким номером не найдено.");
                }
            }
        });
        buttonPanel.add(addAuditoriumButton);

        //КНОПКА УДАЛЕНИЯ ЗДАНИЯ
        JButton deleteBuildingButton = new JButton("Удалить здание");
        deleteBuildingButton.addActionListener(_ -> {
            String buildingNumberDelete = JOptionPane.showInputDialog(null, "Введите номер здания, которе хотите удалить");
            if (buildingNumberDelete != null && !buildingNumberDelete.isEmpty()) {
                try {
                    institute.delete(Integer.parseInt(buildingNumberDelete));
                    data = getData();
                    updateTable(table);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Здание с номером: " + buildingNumberDelete + " отсутствует!");
                }
            }
        });
        buttonPanel.add(deleteBuildingButton);

        //КНОПКА УДАЛЕНИЯ АУДИТОРИИ
        JButton deleteAuditoriumButton = new JButton("Удалить аудиторию");
        deleteAuditoriumButton.addActionListener(_ -> {
            try {

                String buildingNumberInput = JOptionPane.showInputDialog(null, "Введите номер здания:");
                if (buildingNumberInput == null || buildingNumberInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Номер здания не может быть пустым.");
                    return;
                }

                int buildingNumber = Integer.parseInt(buildingNumberInput);
                Building building = institute.find(buildingNumber);

                if (building == null) {
                    JOptionPane.showMessageDialog(null, "Здание с таким номером не найдено.");
                    return;
                }

                String auditoriumNumberInput = JOptionPane.showInputDialog(null, "Введите номер аудитории для удаления:");
                if (auditoriumNumberInput == null || auditoriumNumberInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Номер аудитории не может быть пустым.");
                    return;
                }

                String auditoriumCapacityInput = JOptionPane.showInputDialog(null, "Введите вместимость удаляемой аудитории:");
                if (auditoriumCapacityInput == null || auditoriumCapacityInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Вместимость аудитории не может быть пустой!");
                    return;
                }

                int auditoriumCapacity = Integer.parseInt(auditoriumCapacityInput);
                int auditoriumNumber = Integer.parseInt(auditoriumNumberInput);

                building.delete(auditoriumNumber, auditoriumCapacity);
                data = getData();
                updateTable(table);

                JOptionPane.showMessageDialog(null, "Аудитория успешно удалена.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Введите корректное числовое значение.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ошибка: " + ex.getMessage());
            }
        });
        buttonPanel.add(deleteAuditoriumButton);

        //КНОПКА ПОИСКА ЗДАНИЯ
        JButton findBuildingsButton = new JButton("Найти здание");
        findBuildingsButton.addActionListener(_ -> {
            try {

                String buildingNumberInput = JOptionPane.showInputDialog(null, "Введите номер здания");
                if (buildingNumberInput == null || buildingNumberInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Номер здания не может быть пустым.");
                    return;
                }

                int findingBuildingNumber = Integer.parseInt(buildingNumberInput);
                Building differentBuilding = institute.getHead();
                StringBuilder stringBuilder = new StringBuilder();
                boolean found = false;
                while (differentBuilding != null) {

                    if (findingBuildingNumber == differentBuilding.getBuildingNumber()) {

                        Building targetBuilding = institute.find(findingBuildingNumber);
                        if (targetBuilding != null) {
                            stringBuilder.append("Найдено здание с номером: ").append(findingBuildingNumber).append("\n");
                            found = true;
                        }

                        differentBuilding = differentBuilding.getNext();
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(null, stringBuilder);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        buttonPanel.add(findBuildingsButton);

        //КНОПКА ПОИСКА АУДИТОРИИ
        JButton findAuditoriumButton = new JButton("Найти аудиторию");
        findAuditoriumButton.addActionListener(_ -> {
            try {

                String auditoriumNumberInput = JOptionPane.showInputDialog(null, "Введите номер аудитории");
                if (auditoriumNumberInput == null || auditoriumNumberInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Номер аудитории не может быть пустым.");
                    return;
                }

                String capacityNumberInput = JOptionPane.showInputDialog(null, "Введите вместимость аудитории");
                if (capacityNumberInput == null || capacityNumberInput.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Аудитория не может быть пустой.");
                    return;
                }
                int auditoriumNumber = Integer.parseInt(auditoriumNumberInput);
                int capacity = Integer.parseInt(capacityNumberInput);

                Building differentBuilding = institute.getHead();
                StringBuilder stringBuilder = new StringBuilder();
                boolean found = false;

                while (differentBuilding != null) {
                    Auditorium targetAuditorium = differentBuilding.find(auditoriumNumber, capacity);

                    if (targetAuditorium != null) {
                        stringBuilder.append("Найдена аудитория : ").append(targetAuditorium).append(" В здании: ").append(differentBuilding.getBuildingNumber()).append("\n");
                        found = true;
                    }

                    differentBuilding = differentBuilding.getNext();
                }

                if (found) {
                    JOptionPane.showMessageDialog(null, stringBuilder);
                }
            }
            catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Аудитория отсутствует!");
            }
        });
        buttonPanel.add(findAuditoriumButton);

        //КНОПКА ИЗМЕНЕНИЯ ИМЕНИ ИНСТИТУТА
        JButton changeInstituteName = new JButton("Изменить имя института");
        changeInstituteName.addActionListener(_ -> {
            JTextField textField = new JTextField();
            JOptionPane.showMessageDialog(null, textField);
            String instituteName = textField.getText();
            institute.setInstituteName(instituteName);
            data = getData();
            updateTable(table);
        });
        buttonPanel.add(changeInstituteName);

        //КНОПКА ОЧИСТКИ
        JButton deleteButton = new JButton("Очистка");
        deleteButton.addActionListener(_ -> {
            institute.destruct();
            data = getData();
            updateTable(table);
        });
        buttonPanel.add(deleteButton);
    }

    private Object[][] getData() {
        int rows = 0;
        Institute currentInstitute = institute;
        while (currentInstitute != null) {
            Building currentBuilding = currentInstitute.getHead();
            while (currentBuilding != null) {
                if (currentBuilding.getHead() == null) {
                    rows++;
                } else {
                    Auditorium currentAuditorium = currentBuilding.getHead();
                    while (currentAuditorium != null) {
                        rows++;
                        currentAuditorium = currentAuditorium.getNext();
                    }
                }
                currentBuilding = currentBuilding.getNext();
            }
            currentInstitute = currentInstitute.getNext();
        }

        Object[][] data = new Object[rows][5];
        int row = 0;

        currentInstitute = institute;
        while (currentInstitute != null) {
            Building currentBuilding = currentInstitute.getHead();
            while (currentBuilding != null) {
                Auditorium currentAuditorium = currentBuilding.getHead();
                if (currentAuditorium == null) {
                    data[row][0] = row + 1;
                    data[row][1] = currentInstitute.getInstituteName();
                    data[row][2] = currentBuilding.getBuildingNumber();
                    data[row][3] = "Нет аудиторий";
                    data[row][4] = "";
                    row++;
                } else {
                    while (currentAuditorium != null) {
                        data[row][0] = row + 1;
                        data[row][1] = currentInstitute.getInstituteName();
                        data[row][2] = currentBuilding.getBuildingNumber();
                        data[row][3] = currentAuditorium.getAuditoriumNumber();
                        data[row][4] = currentAuditorium.getAuditoriumCapacity();
                        row++;
                        currentAuditorium = currentAuditorium.getNext();
                    }
                }
                currentBuilding = currentBuilding.getNext();
            }
            currentInstitute = currentInstitute.getNext();
        }
        return data;
    }

    private void updateTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new String[]{"№", "Институт", "Номер здания", "Номер аудитории", "Число мест"});
    }

    private void setData(Object[][] data, int row) {
        if (data == null) {
            return;
        }
        String instituteName = "None";
        institute.destruct();
        for (int i = 0; i < row; i++) {
            instituteName = (String) data[i][1];
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

