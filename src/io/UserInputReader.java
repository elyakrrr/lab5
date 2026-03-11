package io;

import model.*;
import utils.Validator;

import java.util.Date;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Класс для чтения и создания объектов Person из пользовательского ввода
 */
public class UserInputReader {
    private final Scanner scanner;

    public UserInputReader() {
        this.scanner = new Scanner(System.in);
    }

    public UserInputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Читает объект Person из консоли
     */
    public Person readPerson() {
        System.out.println("\n=== Создание нового человека ===");

        // Имя (не может быть null, не пустое)
        String name = readName();

        // Координаты (не могут быть null)
        Coordinates coordinates = readCoordinates();

        // Рост (> 0)
        float height = readHeight();

        // Дата рождения (может быть null)
        Date birthday = readBirthday();

        // Цвет волос (может быть null)
        Color hairColor = readHairColor();

        // Национальность (может быть null)
        Country nationality = readNationality();

        // Локация (может быть null)
        Location location = readLocation();

        // ID и creationDate генерируются автоматически в конструкторе
        return new Person(name, coordinates, height, birthday, hairColor, nationality, location);
    }

    /**
     * Читает имя
     */
    private String readName() {
        while (true) {
            try {
                System.out.print("Введите имя (не может быть пустым): ");
                String input = scanner.nextLine().trim();
                return Validator.validateNotEmpty(input, "Имя");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает координаты
     */
    private Coordinates readCoordinates() {
        System.out.println("\n--- Ввод координат ---");

        Integer x = readCoordinateX();
        Integer y = readCoordinateY();

        return new Coordinates(x, y);
    }

    /**
     * Читает координату X
     */
    private Integer readCoordinateX() {
        while (true) {
            try {
                System.out.print("Введите координату X (целое число, больше -746): ");
                String input = scanner.nextLine().trim();
                return Validator.validateInteger(input, "X", -746, false);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает координату Y
     */
    private Integer readCoordinateY() {
        while (true) {
            try {
                System.out.print("Введите координату Y (целое число): ");
                String input = scanner.nextLine().trim();
                return Validator.validateInteger(input, "Y", null, false);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает рост
     */
    private float readHeight() {
        while (true) {
            try {
                System.out.print("Введите рост (число > 0): ");
                String input = scanner.nextLine().trim();
                return Validator.validateFloat(input, "Рост", 0f, false);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает дату рождения
     */
    private Date readBirthday() {
        while (true) {
            try {
                System.out.print("Введите дату рождения (в формате yyyy-MM-dd, или пустую строку для null): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return null;
                }

                // Конвертируем LocalDate в Date (упрощенно)
                LocalDate localDate = Validator.validateDate(input, "Дата рождения", true);
                return localDate != null ? java.sql.Date.valueOf(localDate) : null;

            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает цвет волос
     */
    private Color readHairColor() {
        while (true) {
            try {
                System.out.println("Доступные цвета волос: GREEN, RED, BLUE, ORANGE, BROWN");
                System.out.print("Введите цвет волос (или пустую строку для null): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return null;
                }

                return Validator.validateColor(input, true);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает национальность
     */
    private Country readNationality() {
        while (true) {
            try {
                System.out.println("Доступные страны: THAILAND, SOUTH_KOREA, JAPAN");
                System.out.print("Введите национальность (или пустую строку для null): ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return null;
                }

                return Validator.validateCountry(input, true);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает локацию
     */
    private Location readLocation() {
        while (true) {
            System.out.println("\n--- Ввод локации ---");
            System.out.print("Хотите ввести локацию? (y/n, по умолчанию n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("n") || answer.isEmpty()) {
                return null;
            }

            if (!answer.equals("y")) {
                System.out.println("Пожалуйста, введите y или n");
                continue;
            }

            try {
                Float x = readLocationX();
                long y = readLocationY();
                String name = readLocationName();

                return new Location(x, y, name);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка при вводе локации: " + e.getMessage());
                System.out.print("Хотите попробовать снова? (y/n): ");
                if (!scanner.nextLine().trim().toLowerCase().equals("y")) {
                    return null;
                }
            }
        }
    }

    /**
     * Читает X локации
     */
    private Float readLocationX() {
        while (true) {
            try {
                System.out.print("Введите X локации (число, не может быть null): ");
                String input = scanner.nextLine().trim();
                return Validator.validateFloat(input, "X локации", null, false);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает Y локации
     */
    private long readLocationY() {
        while (true) {
            try {
                System.out.print("Введите Y локации (целое число): ");
                String input = scanner.nextLine().trim();
                Long value = Validator.validateLong(input, "Y локации", null, false);
                return value != null ? value : 0;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает имя локации
     */
    private String readLocationName() {
        System.out.print("Введите название локации (может быть пустым): ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    /**
     * Читает ID из консоли
     */
    public Integer readId(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Validator.validateInteger(input, "ID", 0, false);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    /**
     * Читает подтверждение действия
     */
    public boolean readConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            }
            if (input.equals("n")) {
                return false;
            }

            System.out.println("Пожалуйста, введите y или n");
        }
    }
}