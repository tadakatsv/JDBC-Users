package org.example.app.config;

import org.example.app.view.AppView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Конектор до БД.
public class DBConn {

    public static Connection connect() {

        Properties props = new Properties();
        Connection conn = null;
        try {
            // Формуємо потік (Stream) даних з конфігураційного файлу
            // src/main/resources/app.properties
            props.load(DBConn.class.getResourceAsStream("/app.properties"));
            // Реалізуємо з'єднання з БД по конфігураційному файлу
            conn = DriverManager.getConnection(
                    props.getProperty("dbDriver") + props.getProperty("dbName"),
                    props.getProperty("username"), props.getProperty("password"));
        } catch (SQLException | IOException e) {
            // Виведення повідомлення про помилки роботи
            // з БД або конфігураційним файлом
            new AppView().getOutput(e.getMessage());
        }
        return conn;
    }
}
