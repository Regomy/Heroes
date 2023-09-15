package me.rejomy.heroes.database

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

abstract class DataBase {

    protected abstract val url: String
    abstract var statement: Statement
    abstract var connection: Connection

    fun set(name: String, group: String, level: Int) {
        statement.executeUpdate("INSERT OR REPLACE INTO users VALUES ('$name', '$group', '$level')")
    }

    fun executeQuery(sql: String): ResultSet {
        return statement.executeQuery(sql)
    }

    fun remove(name: String) { statement.executeUpdate("DELETE FROM users WHERE name='$name'") }

    abstract fun connection(): Connection

}