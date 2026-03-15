package utils;

import model.Color;
import model.Country;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Класс для валидации вводимых данных
 */
public class Validator {

    /**
     * Проверяет, что строка не null и не пуста
     */
    public static String validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return value.trim();
    }

    /**
     * Проверяет целое число с ограничениями
     */
    public static Integer validateInteger(String value, String fieldName, Integer minValue, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }

        try {
            int intValue = Integer.parseInt(value.trim());

            if (minValue != null && intValue <= minValue) {
                throw new IllegalArgumentException(fieldName + " должно быть больше " + minValue);
            }

            return intValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " должно быть целым числом");
        }
    }

    /**
     * Проверяет число с плавающей точкой с ограничениями
     */
    public static Float validateFloat(String value, String fieldName, Float minValue, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }

        try {
            float floatValue = Float.parseFloat(value.trim());

            if (minValue != null && floatValue <= minValue) {
                throw new IllegalArgumentException(fieldName + " должно быть больше " + minValue);
            }

            return floatValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " должно быть числом");
        }
    }

    /**
     * Проверяет дату
     */
    public static LocalDate validateDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Дата должна быть в формате yyyy-MM-dd");
        }
    }

    /**
     * Проверяет enum Color (может быть null)
     */
    public static Color validateColor(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Color.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {

            StringBuilder available = new StringBuilder();
            Color[] colors = Color.values();

            for (int i = 0; i < colors.length; i++) {
                available.append(colors[i].name());
                if (i < colors.length - 1) {
                    available.append(", ");
                }
            }

            throw new IllegalArgumentException("Доступные цвета: " + available);
        }
    }

    /**
     * Проверяет enum Country (может быть null)
     */
    public static Country validateCountry(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Country.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {

            StringBuilder available = new StringBuilder();
            Country[] countries = Country.values();

            for (int i = 0; i < countries.length; i++) {
                available.append(countries[i].name());
                if (i < countries.length - 1) {
                    available.append(", ");
                }
            }

            throw new IllegalArgumentException("Доступные страны: " + available);
        }
    }
    /**
     * Проверяет long число
     */
    public static Long validateLong(String value, String fieldName, Long minValue, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }

        try {
            long longValue = Long.parseLong(value.trim());

            if (minValue != null && longValue <= minValue) {
                throw new IllegalArgumentException(fieldName + " должно быть больше " + minValue);
            }

            return longValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " должно быть целым числом");
        }
    }
}