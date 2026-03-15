package io;

import model.*;
import utils.Validator;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Класс для чтения команд из скрипта
 */
public class ScriptReader implements AutoCloseable {
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
            String name = Validator.validateNotEmpty(readLine(), "Имя");
            Coordinates coordinates = readCoordinatesFromScript();

            String heightStr = readLine();
            float height = Validator.validateFloat(heightStr, "Рост", 0f, false);

            String birthdayStr = readLine();
            LocalDate localDate = Validator.validateDate(birthdayStr);
            Date birthday = localDate != null ? java.sql.Date.valueOf(localDate) : null;

            String hairColorStr = readLine();
            Color hairColor = Validator.validateColor(hairColorStr);

            String nationalityStr = readLine();
            Country nationality = Validator.validateCountry(nationalityStr);

            Location location = readLocationFromScript();

            return new Person(name, coordinates, height, birthday, hairColor, nationality, location);

        } catch (IllegalArgumentException e) {
            throw new IOException("Ошибка валидации данных в скрипте: " + e.getMessage());
        }
    }

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

    private Location readLocationFromScript() throws IOException {
        if (!scanner.hasNextLine()) {
            throw new IOException("Неожиданный конец файла при чтении Location");
        }

        String hasLocation = readLine();
        if (hasLocation == null || hasLocation.equals("null")) {
            return null;
        }

        try {
            String xStr = readLine();
            String yStr = readLine();
            String name = readLine();

            Float x = Validator.validateFloat(xStr, "X локации", null, false);
            Long y = Validator.validateLong(yStr, "Y локации", null, false);

            return new Location(x, y != null ? y : 0,
                    (name == null || name.isEmpty() || "null".equals(name)) ? null : name);
        } catch (IllegalArgumentException e) {
            throw new IOException("Ошибка валидации локации: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}