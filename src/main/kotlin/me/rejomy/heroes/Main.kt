package me.rejomy.heroes

import me.realized.duels.api.Duels
import me.rejomy.heroes.command.HeroCommand
import me.rejomy.heroes.database.DataBase
import me.rejomy.heroes.database.SQLite
import me.rejomy.heroes.listener.*
import me.rejomy.heroes.listener.DuelsMatchListener
import me.rejomy.heroes.util.HeroUtil
import me.rejomy.heroes.util.inventory.implement.TopInventory
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main
lateinit var db: DataBase

var users = HashMap<String, PlayerData>()
var nekro = HashMap<String, Inventory>()

val topInventory = TopInventory().openInventory()

class Main : JavaPlugin() {

    var duels: Duels? = null

    override fun onLoad() {
        INSTANCE = this
        saveDefaultConfig()

        db = SQLite()
        HeroUtil.loadUsersIntoMap()
    }

    override fun onEnable() {

        duels = Bukkit.getServer().pluginManager.getPlugin("Duels") as Duels?

        if(duels != null) {
            Bukkit.getPluginManager().registerEvents(DuelsMatchListener(), this)
        }

        Bukkit.getPluginManager().registerEvents(InventoryListener(), this)
        Bukkit.getPluginManager().registerEvents(ConnectionListener(), this)
        Bukkit.getPluginManager().registerEvents(Death(), this)
        Bukkit.getPluginManager().registerEvents(Interact(), this)
        Bukkit.getPluginManager().registerEvents(DamageListener(), this)
        Bukkit.getPluginManager().registerEvents(Enchant(), this)
        Bukkit.getPluginManager().registerEvents(TeleportListener(), this)

        getCommand("heroes").executor = HeroCommand()

    }

    override fun onDisable() {
        users.clear()
    }

}