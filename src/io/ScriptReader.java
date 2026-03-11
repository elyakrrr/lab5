package io;

import model.*;
import utils.Validator;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

/**
 * Класс для чтения команд из скрипта
 */
public class ScriptReader {
    private final Scanner scanner;
    private final Deque<String> scriptStack = new ArrayDeque<>();
    private static final int MAX_RECURSION_DEPTH = 10;

    public ScriptReader(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    public ScriptReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Проверяет рекурсию
     */
    public void checkRecursion(String scriptPath) throws IOException {
        if (scriptStack.contains(scriptPath)) {
            throw new IOException("Обнаружена рекурсия! Путь: " + scriptPath);
        }
        if (scriptStack.size() >= MAX_RECURSION_DEPTH) {
            throw new IOException("Превышена максимальная глубина рекурсии (" + MAX_RECURSION_DEPTH + ")");
        }
        scriptStack.push(scriptPath);
    }

    /**
     * Завершает выполнение скрипта
     */
    public void finishScript() {
        if (!scriptStack.isEmpty()) {
            scriptStack.pop();
        }
    }

    /**
     * Читает следующую строку из скрипта
     */
    public String readLine() {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    /**
     * Читает объект Person из скрипта
     */
    public Person readPersonFromScript() throws IOException {
        if (!scanner.hasNextLine()) {
            throw new IOException("Неожиданный конец файла при чтении Person");
        }

        try {
            // Имя
            String name = Validator.validateNotEmpty(readLine(), "Имя");

            // Координаты
            Coordinates coordinates = readCoordinatesFromScript();

            // Рост
            String heightStr = readLine();
            float height = Validator.validateFloat(heightStr, "Рост", 0f, false);

            // Дата рождения
            String birthdayStr = readLine();
            Date birthday = null;
            if (!birthdayStr.isEmpty()) {
                LocalDate localDate = Validator.validateDate(birthdayStr, "Дата рождения", true);
                birthday = localDate != null ? java.sql.Date.valueOf(localDate) : null;
            }

            // Цвет волос
            String hairColorStr = readLine();
            Color hairColor = hairColorStr.isEmpty() ? null : Validator.validateColor(hairColorStr, true);

            // Национальность
            String nationalityStr = readLine();
            Country nationality = nationalityStr.isEmpty() ? null : Validator.validateCountry(nationalityStr, true);

            // Локация
            Location location = readLocationFromScript();

            return new Person(name, coordinates, height, birthday, hairColor, nationality, location);

        } catch (IllegalArgumentException e) {
            throw new IOException("Ошибка валидации данных в скрипте: " + e.getMessage());
        }
    }

    /**
     * Читает координаты из скрипта
     */
    private Coordinates readCoordinatesFromScript() throws IOException {
        if (!scanner.hasNextLine()) {
            throw new IOException("Неожиданный конец файла при чтении Coordinates");
        }

        try {
            String xStr = readLine();
            String yStr = readLine();

            Integer x = Validator.validateInteger(xStr, "X координаты", -746, false);
            Integer y = Validator.validateInteger(yStr, "Y координаты", null, false);

            return new Coordinates(x, y);
        } catch (IllegalArgumentException e) {
            throw new IOException("Ошибка валидации координат: " + e.getMessage());
        }
    }

    /**
     * Читает локацию из скрипта
     */
    private Location readLocationFromScript() throws IOException {
        if (!scanner.hasNextLine()) {
            throw new IOException("Неожиданный конец файла при чтении Location");
        }

        String hasLocation = readLine();
        if (hasLocation.equals("null")) {
            return null;
        }

        try {
            String xStr = readLine();
            String yStr = readLine();
            String name = readLine();

            Float x = Validator.validateFloat(xStr, "X локации", null, false);
            Long y = Validator.validateLong(yStr, "Y локации", null, false);

            return new Location(x, y != null ? y : 0, name.isEmpty() ? null : name);
        } catch (IllegalArgumentException e) {
            throw new IOException("Ошибка валидации локации: " + e.getMessage());
        }
    }

    /**
     * Закрывает сканер
     */
    public void close() {
        scanner.close();
    }
}