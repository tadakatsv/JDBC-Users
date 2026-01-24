package org.example.app.repository;

import org.example.app.config.DBConn;
import org.example.app.entity.User;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository<User> {

    private final static String TABLE_USERS = "users";

    @Override
    public String create(User user) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "INSERT INTO " + TABLE_USERS +
                " (name, surname, email) VALUES(?, ?, ?)";
        // PreparedStatement - підготовлений вираз (оператор), щоб уникнути SQL-ін'єкцій
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            // Формування конкретних значень для певного заповнювача
            pstmt.setString(1, user.getSurname());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            // Виконання SQL-запиту
            pstmt.executeUpdate();
            // Повернення повідомлення при безпомилковому
            // виконанні SQL-запиту
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            // Колекція-контейнер для даних, які читаються з БД
            List<User> list = new ArrayList<>();
            // SQL-запит
            String sql = "SELECT id, firstName, lastName, phone, email FROM "
                    + TABLE_USERS;
            // Отримання набору даних з БД через виконання SQL-запиту
            ResultSet rs = stmt.executeQuery(sql);
            // Наповнення колекції-контейнера об'єктами з БД
            while (rs.next()) {
                list.add(new User(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("surname"),
                                rs.getString("email")
                        )
                );
            }
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (SQLException e) {
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        // Спершу перевіряємо наявність об'єкта в БД за таким id.
        // Якщо ні, повертаємо повідомлення про відсутність таких даних,
        // інакше оновлюємо відповідний об'єкт в БД
        if (readById(user.getId()).isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            // SQL-запит.
            // ? - заповнювач (placeholder) для параметра. Навіщо?
            // Захист від SQL-ін'єкцій.
            // Ефективність. Коли використовуємо підготовлені оператори
            // (PreparedStatement),
            // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
            // Використовується шаблон та просто підставляються в нього значення.
            String sql = "UPDATE " + TABLE_USERS +
                    " SET name = ?, surname = ?, email = ?" +
                    " WHERE id = ?";
            // PreparedStatement - підготовлений вираз, щоб уникнути SQL-ін'єкцій
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                // Формування конкретних значень для певного заповнювача
                pst.setString(1, user.getSurname());
                pst.setString(2, user.getName());
                pst.setString(3, user.getEmail());
                pst.setLong(5, user.getId());
                // Виконання SQL-запиту
                pst.executeUpdate();
                // Повернення повідомлення при безпомилковому
                // виконанні SQL-запиту
                return Constants.DATA_UPDATE_MSG;
            } catch (SQLException e) {
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        // Спершу перевіряємо наявність такого id в БД.
        // Якщо ні, повертаємо повідомлення про відсутність
        // таких даних в БД, інакше видаляємо відповідний об'єкт
        // із БД.
        if (!isIdExists(id)) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            // SQL-запит.
            // ? - заповнювач (placeholder) для параметра. Навіщо?
            // Захист від SQL-ін'єкцій.
            // Ефективність. Коли використовуємо підготовлені оператори
            // (PreparedStatement), базі даних не потрібно щоразу
            // аналізувати/компілювати SQL-запит.
            // Використовується шаблон та просто підставляються в нього
            // значення.
            String sql = "DELETE FROM " + TABLE_USERS +
                    " WHERE id = ?";
            // PreparedStatement - підготовлений вираз (оператор),
            // щоб уникнути SQL-ін'єкцій
            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                // Формування конкретних значень для певного заповнювача
                pst.setLong(1, id);
                // Виконання SQL-запиту
                pst.executeUpdate();
                // Повернення повідомлення при безпомилковому
                // виконанні SQL-запиту
                return Constants.DATA_DELETE_MSG;
            } catch (SQLException e) {
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        // SQL-запит.
        // ? - заповнювач (placeholder) для параметра. Навіщо?
        // Захист від SQL-ін'єкцій.
        // Ефективність. Коли використовуємо підготовлені оператори (PreparedStatement),
        // базі даних не потрібно щоразу аналізувати/компілювати SQL-запит.
        // Використовується шаблон та просто підставляються в нього значення.
        String sql = "SELECT id, firstName, lastName, phone, email FROM "
                + TABLE_USERS + " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("email")
            );
            // Повертаємо Optional-контейнер з об'єктом
            return Optional.of(user);
        } catch (SQLException e) {
            // Якщо помилка або такого об'єкту немає в БД,
            // повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    // Перевірка наявності певного id у БД
    private boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_USERS +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                // Очікуємо лише один результат
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
