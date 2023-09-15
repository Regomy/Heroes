package me.rejomy.heroes.race

import me.rejomy.heroes.util.inventory.implement.shop.DeathInventory
import me.rejomy.heroes.util.inventory.implement.shop.LifeInventory
import me.rejomy.heroes.util.inventory.implement.shop.OrderInventory
import me.rejomy.heroes.util.inventory.implement.shop.PowerInventory
import org.bukkit.inventory.Inventory

enum class RaceType(val russianType: String, val shopInventory: Inventory) {

    ORDER("порядок", OrderInventory().openInventory()),
    POWER("сила", PowerInventory().openInventory()),
    DEATH("смерть", DeathInventory().openInventory()),
    LIFE("жизнь", LifeInventory().openInventory()),

}