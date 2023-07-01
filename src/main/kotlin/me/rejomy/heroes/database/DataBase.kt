package me.rejomy.heroes.database

import me.rejomy.heroes.users
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class DataBase {

    private val url: String = "jdbc:sqlite:plugins/Heroes/users.db"
    private var statement: Statement
    private var connection: Connection

    init {
        Class.forName("org.sqlite.JDBC").newInstance()
        connection = connection()
        statement = connection.createStatement()
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ('name' TEXT, 'class' TEXT, 'level' TEXT)")
    }

    fun set(name: String, group: String, level: Int) {
        delete(name)
        statement.executeUpdate("INSERT INTO users VALUES ('$name', '$group', '$level')")
    }
    fun delete(name: String) { statement.executeUpdate("DELETE FROM users WHERE name='$name'") }

    private fun connection(): Connection { return DriverManager.getConnection(url) }

    fun getGroup(name: String): Int {
        val result: ResultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM users WHERE name = '$name'")
        return result.getInt(1)
    }

    fun getLevel(name: String): Int {
        val result: ResultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM users WHERE name = '$name'")
        return result.getInt(2)
    }

    fun setUsers() {
        val rs: ResultSet = statement.executeQuery("select * from users")
        while (rs.next()) {
            var array = arrayOf("", "")
            array[0] = rs.getString(2)
            array[1] = rs.getString(3)
            users[rs.getString(1)] = array
        }
    }

}