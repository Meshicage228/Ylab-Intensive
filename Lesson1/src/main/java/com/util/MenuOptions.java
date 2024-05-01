package com.util;

/**
 * Класс, содержащий все меню-опции приложения
 */
public class MenuOptions {
    /**
     * Начальная меню-опция
     */
    public static String enterMenu = """
            1. Вход
            2. Регистрация
            3. Завершить программу
            """;
    /**
     * Меню-опция для управления тренировками
     */
    public static String userMenu = """
            1. Добавить тренировку
            2. Редактировать тренировку
            3. Просмотреть тренировки
            4. Статистика тренировок
            5. Тренировки всех пользователей
            6. Назад
            """;

    /**
     * Меню-опция для изменения выбранной тренировки
     */
    public static String workoutEditorMenu = """
            1. Новое название
            2. Новая дата
            3. Новая длительность
            4. Другие каллории
            5. Редактировать описание
            6. Удалить
            7. Назад
            """;
}