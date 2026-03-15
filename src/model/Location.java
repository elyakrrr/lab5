package model;

import java.util.Objects;

/**
 * Класс, представляющий локацию
 */
public class Location {
    private Float x; // Поле не может быть null
    private long y;
    private String name; // Поле может быть null

    /**
     * Конструктор для создания локации
     * @param x координата X (не может быть null)
     * @param y координата Y
     * @param name название локации (может быть null)
     * @throws IllegalArgumentException если x равен null
     */
    public Location(Float x, long y, String name) {
        setX(x);
        this.y = y;
        this.name = name;
    }

    /**
     * Пустой конструктор для сериализации
     */
    public Location() {
    }

    /**
     * @return координата X
     */
    public Float getX() {
        return x;
    }

    /**
     * Устанавливает координату X
     * @param x координата X (не может быть null)
     * @throws IllegalArgumentException если x равен null
     */
    public void setX(Float x) {
        if (x == null) {
            throw new IllegalArgumentException("Поле x не может быть null");
        }
        this.x = x;
    }

    /**
     * @return координата Y
     */
    public long getY() {
        return y;
    }

    /**
     * Устанавливает координату Y
     * @param y координата Y
     */
    public void setY(long y) {
        this.y = y;
    }

    /**
     * @return название локации
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название локации
     * @param name название локации (может быть null)
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return y == location.y &&
                Objects.equals(x, location.x) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return String.format("Location {x=%.2f, y=%d, name='%s'}", x, y, name != null ? name : "null");
    }
}