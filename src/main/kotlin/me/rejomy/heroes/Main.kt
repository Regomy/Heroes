package me.rejomy.heroes

import me.realized.duels.api.Duels
import me.rejomy.heroes.command.Hero
import me.rejomy.heroes.database.DataBase
import me.rejomy.heroes.listener.*
import me.rejomy.heroes.listener.duel.MatchListener
import me.rejomy.heroes.util.*
import me.rejomy.heroes.util.inventory.ClearHeroConfirm
import me.rejomy.heroes.util.inventory.Newplayers
import me.rejomy.heroes.util.inventory.Top
import me.rejomy.heroes.util.inventory.shop.Death
import me.rejomy.heroes.util.inventory.shop.Life
import me.rejomy.heroes.util.inventory.shop.Order
import me.rejomy.heroes.util.inventory.shop.Power
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

lateinit var INSTANCE: Main
lateinit var db: DataBase

var users = HashMap<String, Array<String>>()
var nekro = HashMap<String, Inventory>()
lateinit var top: Inventory
var duels: Duels? = null

class Main : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        INSTANCE = this
        duels = Bukkit.getServer().pluginManager.getPlugin("Duels") as Duels
        if(duels != null)
            Bukkit.getPluginManager().registerEvents(MatchListener(), this)
        EconomyManager.init()
        newplayersInv = Newplayers().openInventory()
        shopOrderInv = Order().openInventory()
        shopPowerInv = Power().openInventory()
        shopLifeInv = Life().openInventory()
        shopDeathInv = Death().openInventory()
        ClearHeroConfirm = ClearHeroConfirm().openInventory()
        db = DataBase()
        db.setUsers()
        Bukkit.getPluginManager().registerEvents(Inventory(), this)
        Bukkit.getPluginManager().registerEvents(Connection(), this)
        Bukkit.getPluginManager().registerEvents(me.rejomy.heroes.listener.Death(), this)
        Bukkit.getPluginManager().registerEvents(Interact(), this)
        Bukkit.getPluginManager().registerEvents(Fight(), this)
        Bukkit.getPluginManager().registerEvents(ItemConsumeListener(), this)
        Bukkit.getPluginManager().registerEvents(Enchant(), this)
        Bukkit.getPluginManager().registerEvents(TeleportListener(), this)
        Bukkit.getPluginManager().registerEvents(ItemRegisterListener() , this)
        getCommand("heroes").executor = Hero()
        top = Top().openInventory()
    }

}