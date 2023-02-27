package me.rejomy.heroes.util.inventory.shop

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Power : InventoryBuilder("§0▷ Магазин магии", 54, null) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Экскалибур &4Силы &7даёт вам:   ")
        lore.add("&c ‣ &7Возможность ломать броню соперника   ")
        lore.add("&7")
        lore.add("&c ‣ &7Сила меча зависит от")
        lore.add("&c ‣ &7уровня вашего героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c25.000$   ")
        lore.add("&7")

        map[22] = createItemStack("EKSPA_LIBURRR", replaceColor( lore ), Material.DIAMOND_SWORD, 1)

        return createInv(map)
    }
}