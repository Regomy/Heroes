package me.rejomy.heroes.util.inventory.shop

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Order : InventoryBuilder("§0▷ Магазин магии", 54, null) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Лук &9Порядка &7даёт вам:   ")
        lore.add("&c ‣ &7Скорострельность")
        lore.add("&7")
        lore.add("&c ‣ &7Сила лука зависит от")
        lore.add("&c ‣ &7вашего уровня героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c18.000$   ")
        lore.add("&7")

        map[24] = createItemStack("XBOW", replaceColor( lore ), Material.BOW, 1)

        return createInv(map)
    }
}