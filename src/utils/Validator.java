package utils;

import model.Color;
import model.Country;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
     * Проверяет число с плавающей точкой double
     */
    public static Double validateDouble(String value, String fieldName, Double minValue, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }

        try {
            double doubleValue = Double.parseDouble(value.trim());

            if (minValue != null && doubleValue <= minValue) {
                throw new IllegalArgumentException(fieldName + " должно быть больше " + minValue);
            }

            return doubleValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " должно быть числом");
        }
    }

    /**
     * Проверяет дату
     */
    public static LocalDate validateDate(String value, String fieldName, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }

        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(fieldName + " должно быть в формате yyyy-MM-dd");
        }
    }

    /**
     * Проверяет enum Color
     */
    public static Color validateColor(String value, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException("Цвет волос не может быть пустым");
        }

        try {
            return Color.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            String available = Arrays.stream(Color.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Доступные цвета: " + available);
        }
    }

    /**
     * Проверяет enum Country
     */
    public static Country validateCountry(String value, boolean canBeNull) {
        if (value == null || value.trim().isEmpty()) {
            if (canBeNull) {
                return null;
            }
            throw new IllegalArgumentException("Национальность не может быть пустой");
        }

        try {
            return Country.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            String available = Arrays.stream(Country.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
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