/**
 * Точка входа в программу
 */
public class Main {
    public static void main(String[] args) {
        // Создаем и запускаем приложение
        App app = new App();

        try {
            // Инициализируем приложение
            app.initialize();

            // Запускаем основной цикл
            app.run();

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Завершаем приложение
            app.shutdown();
        }

        // Явный выход для гарантии завершения всех потоков
        System.exit(0);
    }
}