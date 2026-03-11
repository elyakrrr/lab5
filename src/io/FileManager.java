package io;

import exceptions.FileAccessException;
import exceptions.InvalidDataException;
import model.Person;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс для управления файловым вводом/выводом
 */
public class FileManager {
    private final String fileName;
    private final XMLParser xmlParser;

    /**
     * Конструктор FileManager
     * @param envVarName имя переменной окружения с именем файла
     * @throws FileAccessException если переменная окружения не установлена
     */
    public FileManager(String envVarName) throws FileAccessException {
        this.fileName = System.getenv(envVarName);
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new FileAccessException("Переменная окружения " + envVarName + " не установлена");
        }
        this.xmlParser = new XMLParser();
    }

    /**
     * Загружает коллекцию из файла
     * @return HashSet с загруженными объектами Person
     * @throws FileAccessException при ошибках доступа к файлу
     * @throws InvalidDataException при невалидных данных в файле
     */
    public HashSet<Person> loadCollection() throws FileAccessException, InvalidDataException {
        StringBuilder content = new StringBuilder();

        // Проверка прав доступа
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileAccessException("Файл не существует: " + fileName);
        }
        if (!file.canRead()) {
            throw new FileAccessException("Нет прав на чтение файла: " + fileName);
        }

        // Чтение файла через Scanner
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new FileAccessException("Файл не найден: " + fileName, e);
        } catch (SecurityException e) {
            throw new FileAccessException("Ошибка безопасности при чтении файла: " + fileName, e);
        }

        // Парсим XML
        if (content.length() == 0) {
            return new HashSet<>();
        }

        return xmlParser.parseToCollection(content.toString());
    }

    /**
     * Сохраняет коллекцию в файл
     * @param collection коллекция для сохранения
     * @throws FileAccessException при ошибках доступа к файлу
     */
    public void saveCollection(HashSet<Person> collection) throws FileAccessException {
        // Проверка прав доступа
        File file = new File(fileName);

        // Проверяем родительскую директорию
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new FileAccessException("Не удалось создать директорию: " + parentDir);
            }
        }

        // Проверяем права на запись
        if (file.exists() && !file.canWrite()) {
            throw new FileAccessException("Нет прав на запись в файл: " + fileName);
        }

        // Конвертируем коллекцию в XML
        String xmlContent = xmlParser.parseToXML(collection);

        // Запись через BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(xmlContent);
        } catch (IOException e) {
            throw new FileAccessException("Ошибка при записи в файл: " + fileName, e);
        }
    }

    /**
     * @return имя файла
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Проверяет существование файла
     * @return true если файл существует
     */
    public boolean fileExists() {
        return new File(fileName).exists();
    }

    /**
     * Проверяет доступность файла для чтения
     * @return true если файл доступен для чтения
     */
    public boolean canRead() {
        File file = new File(fileName);
        return file.exists() && file.canRead();
    }

    /**
     * Проверяет доступность файла для записи
     * @return true если файл доступен для записи
     */
    public boolean canWrite() {
        File file = new File(fileName);
        return !file.exists() || file.canWrite();
    }
}