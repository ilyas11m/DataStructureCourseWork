import java.io.*;
import java.util.Scanner;

public class ConsoleApp {
    private Institute institute;
    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleApp() {
        institute = new Institute("ИКТЗИ");
    }

    public void start() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Добавить здание");
            System.out.println("2. Добавить аудиторию");
            System.out.println("3. Удалить здание");
            System.out.println("4. Удалить аудиторию");
            System.out.println("5. Найти здание");
            System.out.println("6. Найти аудиторию");
            System.out.println("7. Изменить имя института");
            System.out.println("8. Сохранить в файл");
            System.out.println("9. Загрузить из файла");
            System.out.println("10. Очистить данные");
            System.out.println("11. Вывести список зданий и аудиторий");
            System.out.println("0. Выход");
            System.out.print("Введите номер действия: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addBuilding();
                case 2 -> addAuditorium();
                case 3 -> deleteBuilding();
                case 4 -> deleteAuditorium();
                case 5 -> findBuilding();
                case 6 -> findAuditorium();
                case 7 -> changeInstituteName();
                case 8 -> saveToFile();
                case 9 -> loadFromFile();
                case 10 -> clearData();
                case 11 -> printList();
                case 0 -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Некорректный ввод, попробуйте снова.");
            }
        }
    }

    private void addBuilding() {
        System.out.print("Введите номер здания: ");
        int buildingNumber = scanner.nextInt();
        scanner.nextLine();
        institute.add(buildingNumber);
        System.out.println("Здание добавлено.");
    }

    private void addAuditorium() {
        System.out.print("Введите номер здания: ");
        int buildingNumber = scanner.nextInt();
        System.out.print("Введите номер аудитории: ");
        int classroomNumber = scanner.nextInt();
        System.out.print("Введите число мест: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        Building building = institute.find(buildingNumber);
        if (building != null) {
            building.add(classroomNumber, capacity);
            System.out.println("Аудитория добавлена.");
        } else {
            System.out.println("Здание не найдено.");
        }
    }

    private void deleteBuilding() {
        System.out.print("Введите номер здания для удаления: ");
        int buildingNumber = scanner.nextInt();
        scanner.nextLine();

        Building building = institute.find(buildingNumber);
        if (building != null) {
            institute.delete(buildingNumber);
            System.out.println("Здание удалено.");
        } else {
            System.out.println("Здание не найдено.");
        }
    }

    private void deleteAuditorium() {
        System.out.print("Введите номер здания: ");
        int buildingNumber = scanner.nextInt();
        System.out.print("Введите номер аудитории: ");
        int auditoriumNumber = scanner.nextInt();
        System.out.print("Введите вместимость аудитории: ");
        int auditoriumCapacity = scanner.nextInt();
        scanner.nextLine();

        Building building = institute.find(buildingNumber);
        if (building != null) {
            Auditorium auditorium = building.find(auditoriumNumber, auditoriumCapacity);
            if (auditorium != null) {
                building.delete(auditoriumNumber, auditoriumCapacity);
                System.out.println("Аудитория удалена.");
            } else {
                System.out.println("Аудитория не найдена.");
            }
        } else {
            System.out.println("Здание не найдено.");
        }
    }

    private void findBuilding() {
        System.out.print("Введите номер здания для поиска: ");
        int buildingNumber = scanner.nextInt();
        scanner.nextLine();

        Building building = institute.find(buildingNumber);
        if (building != null) {
            System.out.println("Здание найдено: " + building );
        } else {
            System.out.println("Здание не найдено.");
        }
    }

    private void findAuditorium() {
        System.out.print("Введите номер аудитории: ");
        int auditoriumNumber = scanner.nextInt();
        System.out.print("Введите вместимость аудитории: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        Building current = institute.getHead();
        while (current != null) {
            Auditorium auditorium = current.find(auditoriumNumber, capacity);
            if (auditorium != null) {
                System.out.println("Аудитория найдена в здании № " + current.getBuildingNumber());
                return;
            }
            current = current.getNext();
        }
        System.out.println("Аудитория не найдена.");
    }


    private void changeInstituteName() {
        System.out.print("Введите новое название института: ");
        String newName = scanner.nextLine();
        institute.setInstituteName(newName);
        System.out.println("Название института изменено.");
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            Building currentBuilding = institute.getHead();
            while (currentBuilding != null) {
                writer.write(currentBuilding.getBuildingNumber() + "\n");

                Auditorium currentAuditorium = currentBuilding.getHead();
                while (currentAuditorium != null) {
                    writer.write(currentAuditorium.getAuditoriumNumber() + " " + currentAuditorium.getAuditoriumCapacity() + "\n");
                    currentAuditorium = currentAuditorium.getNext();
                }

                writer.write("-\n");
                currentBuilding = currentBuilding.getNext();
            }
            System.out.println("Данные сохранены в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            institute = new Institute("ИКТЗИ");
            String line;
            Building currentBuilding = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("-")) {
                    currentBuilding = null;
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 1) {
                        int buildingNumber = Integer.parseInt(parts[0]);
                        institute.add(buildingNumber);
                        currentBuilding = institute.find(buildingNumber);
                    } else if (parts.length == 2 && currentBuilding != null) {
                        int auditoriumNumber = Integer.parseInt(parts[0]);
                        int capacity = Integer.parseInt(parts[1]);
                        currentBuilding.add(auditoriumNumber, capacity);
                    }
                }
            }
            System.out.println("Данные загружены из файла.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка при загрузке из файла: " + e.getMessage());
        }
    }

    private void printList() {
        Institute currentInstitute = institute;
        if (currentInstitute == null) {
            System.out.println("Список институтов пуст.");
            return;
        }
        else {
            System.out.println("Список институтов, зданий и аудиторий:");
        }

        while (currentInstitute != null) {
            System.out.println("\nИнститут: " + currentInstitute.getInstituteName());
            Building currentBuilding = currentInstitute.getHead();
            if (currentBuilding == null) {
                System.out.println("  Нет зданий.");
            } else {
                while (currentBuilding != null) {
                    System.out.println("  Здание № " + currentBuilding.getBuildingNumber());

                    Auditorium currentAuditorium = currentBuilding.getHead();
                    if (currentAuditorium == null) {
                        System.out.println("    Нет аудиторий.");
                    } else {
                        while (currentAuditorium != null) {
                            System.out.println("    Аудитория № " + currentAuditorium.getAuditoriumNumber() +
                                    " (Вместимость: " + currentAuditorium.getAuditoriumCapacity() + ")");
                            currentAuditorium = currentAuditorium.getNext();
                        }
                    }
                    currentBuilding = currentBuilding.getNext();
                }
            }
            currentInstitute = currentInstitute.getNext();
        }
    }

    private void clearData() {
        institute.destruct();
        System.out.println("Все данные удалены.");
    }

    public static void main(String[] args) {
        new ConsoleApp().start();
    }
}
