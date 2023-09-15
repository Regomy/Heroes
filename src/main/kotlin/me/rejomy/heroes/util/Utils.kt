package me.rejomy.heroes.util

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.util.inventory.implement.RemoveHeroConfirmInventory
import me.rejomy.heroes.util.inventory.implement.ClassSelectorInventory
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import kotlin.collections.ArrayList

val classSelectorInv = ClassSelectorInventory().openInventory()
val RemoveHeroConfirmInventory = RemoveHeroConfirmInventory().openInventory()

fun hasEmptySlot(inventoryHolder: InventoryHolder): Boolean {
    for (i in 0..inventoryHolder.inventory.size)
        if (inventoryHolder.inventory.getItem(i) == null || inventoryHolder.inventory.getItem(i).type == Material.AIR)
            return true
    return false
}

fun isDuel(player: Player): Boolean {
    return INSTANCE.duels != null && INSTANCE.duels!!.arenaManager.isInMatch(player)
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

fun toColor(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}

fun checkLore(lore: Iterable<String>, word: String): Boolean {
    for (line in lore)
        if (line.replace("§.", "").replace("&.", "").contains(word)) return true
    return false
}


fun toColor(list: Iterable<String>): List<String> {
    val customList = ArrayList<String>(list.toList())

    customList.replaceAll { ChatColor.translateAlternateColorCodes('&', it) }

    return customList
}

fun getPriceBook(level: Int): Int {
    return 1000 + (level - 1) * 5000;
}

fun getPriceSumBooks(level: Int): Int {
    return (2 * 1000 + 5000 * (level - 1) / 2) * level
}