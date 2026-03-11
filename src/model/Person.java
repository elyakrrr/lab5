package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, представляющий человека
 * Реализует сортировку по умолчанию по имени
 */
public class Person implements Comparable<Person> {
    private static int nextId = 1;

    private Integer id; // Поле не может быть null, > 0, уникальное, генерируется автоматически
    private String name; // Поле не может быть null, не пустое
    private Coordinates coordinates; // Поле не может быть null
    private java.time.LocalDate creationDate; // Поле не может быть null, генерируется автоматически
    private float height; // Значение поля должно быть больше 0
    private java.util.Date birthday; // Поле может быть null
    private Color hairColor; // Поле может быть null
    private Country nationality; // Поле может быть null
    private Location location; // Поле может быть null

    /**
     * Конструктор для создания человека
     * @param name имя (не null, не пустое)
     * @param coordinates координаты (не null)
     * @param height рост (> 0)
     * @param birthday дата рождения (может быть null)
     * @param hairColor цвет волос (может быть null)
     * @param nationality национальность (может быть null)
     * @param location локация (может быть null)
     */
    public Person(String name, Coordinates coordinates, float height,
                  Date birthday, Color hairColor, Country nationality, Location location) {
        // Автоматическая генерация id и creationDate
        this.id = generateId();
        this.creationDate = LocalDate.now();

        setName(name);
        setCoordinates(coordinates);
        setHeight(height);
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    /**
     * Пустой конструктор для сериализации
     */
    public Person() {
        this.id = generateId();
        this.creationDate = LocalDate.now();
    }

    /**
     * Генерирует уникальный ID
     * @return новый ID
     */
    private static synchronized int generateId() {
        return nextId++;
    }

    /**
     * Сбрасывает генератор ID (для тестирования)
     */
    public static void resetIdGenerator() {
        nextId = 1;
    }

    /**
     * Устанавливает следующий ID (для загрузки из файла)
     * @param id максимальный ID из загруженных элементов
     */
    public static void setNextId(int id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    // Геттеры и сеттеры с валидацией

    public Integer getId() {
        return id;
    }

    /**
     * Устанавливает ID (используется только при загрузке из файла)
     * @param id ID (должен быть > 0, не null)
     */
    public void setId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Поле id не может быть null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Значение поля id должно быть больше 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя
     * @param name имя (не null, не пустое)
     * @throws IllegalArgumentException если имя null или пустое
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть null или пустым");
        }
        this.name = name.trim();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты
     * @param coordinates координаты (не null)
     * @throws IllegalArgumentException если coordinates null
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает дату создания (используется только при загрузке из файла)
     * @param creationDate дата создания (не null)
     */
    public void setCreationDate(LocalDate creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Дата создания не может быть null");
        }
        this.creationDate = creationDate;
    }

    public float getHeight() {
        return height;
    }

    /**
     * Устанавливает рост
     * @param height рост (должен быть > 0)
     * @throws IllegalArgumentException если height <= 0
     */
    public void setHeight(float height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Рост должен быть больше 0");
        }
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int compareTo(Person other) {
        // Сортировка по умолчанию - по имени
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Float.compare(person.height, height) == 0 &&
                Objects.equals(id, person.id) &&
                Objects.equals(name, person.name) &&
                Objects.equals(coordinates, person.coordinates) &&
                Objects.equals(creationDate, person.creationDate) &&
                Objects.equals(birthday, person.birthday) &&
                hairColor == person.hairColor &&
                nationality == person.nationality &&
                Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, height, birthday, hairColor, nationality, location);
    }

    @Override
    public String toString() {
        return String.format(
                "Person{id=%d, name='%s', coordinates=%s, creationDate=%s, height=%.2f, birthday=%s, hairColor=%s, nationality=%s, location=%s}",
                id, name, coordinates, creationDate, height,
                birthday != null ? birthday : "null",
                hairColor != null ? hairColor : "null",
                nationality != null ? nationality : "null",
                location != null ? location : "null"
        );
    }
}