/**
 * Точка входа в программу
 */
public class Main {
    public static void main(String[] args) {
        App app = new App();
        try {
            app.initialize();
            app.run();
        } catch (Exception e) {
            System.err.println("Критическая ошибка при инициализации или работе приложения: " + e.getMessage());
        } finally {
            app.shutdown();
        }
    }
}