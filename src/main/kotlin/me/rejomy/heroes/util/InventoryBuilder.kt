package me.rejomy.heroes.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

open class InventoryBuilder(
    private val name: String,
    private val slots: Int,
): ItemEditor() {

    fun customization(map: HashMap<Int, ItemStack>): HashMap<Int, ItemStack> {
        val glassGray = createItemStack("§7", null, Material.STAINED_GLASS_PANE, 1, 7)
        val glassBlue = createItemStack("§7", null, Material.STAINED_GLASS_PANE, 1, 11)
        for (i in 0..53) {
            when (i) {
                in 10..16 -> continue
                in 19..25 -> continue
                in 28..34 -> continue
                in 37..43 -> continue
            }
            if (i % 2 == 0) map[i] = glassGray
            else map[i] = glassBlue
        }
        return map
    }

    fun createInv(items: Map<Int, ItemStack>): Inventory {
        val inv = Bukkit.createInventory(null, slots, name.replace("&", "§"))

        for((slot, item) in items) {
            inv.setItem(slot, item)
        }

        return inv
    }

}