package me.rejomy.heroes.util

import me.rejomy.heroes.users
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import kotlin.math.pow


lateinit var newplayersInv: Inventory
lateinit var ClearHeroConfirm: Inventory
lateinit var shopOrderInv: Inventory
lateinit var shopDeathInv: Inventory
lateinit var shopLifeInv: Inventory
lateinit var shopPowerInv: Inventory

fun hasEmptySlot(inventoryHolder: InventoryHolder): Boolean {
    for (i in 0..inventoryHolder.inventory.size)
        if (inventoryHolder.inventory.getItem(i) == null || inventoryHolder.inventory.getItem(i).type == Material.AIR)
            return true
    return false
}

fun removeOne(inventory: Inventory, item: ItemStack) {
    val size = inventory.size
    for (i in 0 until size) {
        var other = inventory.getItem(i)
        if (item.isSimilar(other)) {
            val amount = other!!.amount
            if (amount > 1) {
                other.amount = amount - 1
            } else {
                other = null
            }
            inventory.setItem(i, other)
            break
        }
    }
}

fun containsItem(inventoryHolder: InventoryHolder, material: Material): Boolean {
    for (i in 0..inventoryHolder.inventory.size)
        if (inventoryHolder.inventory.getItem(i) != null && inventoryHolder.inventory.getItem(i).type == material)
            return true
    return false
}

fun replaceColor(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}

fun checkLore(lore: Iterable<String>, word: String): Boolean {
    for (line in lore)
        if (line.replace("§.", "").replace("&.", "").contains(word)) return true
    return false
}

fun getLevel(name: String): Int {
    return if(users.containsKey(name)) users[name]!![1].toInt() else 0
}

fun replaceColor(colorCodes: Iterable<String>): List<String> {
    val customList = ArrayList<String>()
    for (i in colorCodes)
        customList.add(ChatColor.translateAlternateColorCodes('&', i))
    return customList
}

fun getPriceBook(player: Player): Int {
    val level = getLevel(player.name)
    return getPriceBook(level)
}

fun getPriceBook(level: Int): Int {
    return if(level < 8) 2000 * 1.5.pow(level).toInt() else (2000 * 1.5.pow(7).toInt()) + (2000 * 1.26.pow(level).toInt())
}

fun getPriceSumBooks(player: Player): Double {
    var sum = 0.0
    for(level in 1..getLevel(player.name))
        sum += getPriceBook(level)
    return sum
}