package me.rejomy.heroes

import me.rejomy.heroes.command.Hero
import me.rejomy.heroes.database.DataBase
import me.rejomy.heroes.listener.Connection
import me.rejomy.heroes.listener.Fight
import me.rejomy.heroes.listener.Interact
import me.rejomy.heroes.util.EconomyManager
import me.rejomy.heroes.util.inventory.Newplayers
import me.rejomy.heroes.util.inventory.shop.Order
import me.rejomy.heroes.util.newplayersInv
import me.rejomy.heroes.util.shopOrderInv
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main
lateinit var db: DataBase

var users = HashMap<String, Array<String>>()
var nekro = HashMap<String, Inventory>()

class Main : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        INSTANCE = this
        EconomyManager.init()
        newplayersInv = Newplayers().openInventory()
        shopOrderInv = Order().openInventory()
        db = DataBase()
        db.setUsers()
        Bukkit.getPluginManager().registerEvents(me.rejomy.heroes.listener.Inventory(), this)
        Bukkit.getPluginManager().registerEvents(Connection(), this)
        Bukkit.getPluginManager().registerEvents(Interact(), this)
        Bukkit.getPluginManager().registerEvents(Fight(), this)
        getCommand("heroes").executor = Hero()
    }

    override fun onDisable() {
        super.onDisable()
    }

}