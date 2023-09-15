package me.rejomy.heroes.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

class SQLite : DataBase() {

    override val url = "jdbc:sqlite:plugins/Heroes/users.db"
    override lateinit var connection: Connection
    override lateinit var statement: Statement

    init {
        Class.forName("org.sqlite.JDBC").newInstance()

        connection = connection()

        statement = connection.createStatement()

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ('name' TEXT PRIMARY KEY, 'class' TEXT, 'level' TEXT)")
    }

    override fun connection(): Connection {
        return DriverManager.getConnection(url)
    }


}
