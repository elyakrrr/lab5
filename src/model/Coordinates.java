package model;

import java.util.Objects;

/**
 * Класс, представляющий координаты
 */
public class Coordinates {
    private Integer x; // Значение поля должно быть больше -746, Поле не может быть null
    private Integer y; // Поле не может быть null

    /**
     * Конструктор для создания координат
     * @param x координата X (должна быть > -746, не null)
     * @param y координата Y (не может быть null)
     * @throws IllegalArgumentException если x или y равны null, или x <= -746
     */
    public Coordinates(Integer x, Integer y) {
        setX(x);
        setY(y);
    }

    /**
     * Пустой конструктор для сериализации
     */
    public Coordinates() {
    }

    /**
     * @return координата X
     */
    public Integer getX() {
        return x;
    }

    /**
     * Устанавливает координату X
     * @param x координата X (должна быть > -746, не null)
     * @throws IllegalArgumentException если x равен null или x <= -746
     */
    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("Поле x не может быть null");
        }
        if (x <= -746) {
            throw new IllegalArgumentException("Значение поля x должно быть больше -746");
        }
        this.x = x;
    }

    /**
     * @return координата Y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Устанавливает координату Y
     * @param y координата Y (не может быть null)
     * @throws IllegalArgumentException если y равен null
     */
    public void setY(Integer y) {
        if (y == null) {
            throw new IllegalArgumentException("Поле y не может быть null");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Coordinates {x=%d, y=%d}", x, y);
    }
}