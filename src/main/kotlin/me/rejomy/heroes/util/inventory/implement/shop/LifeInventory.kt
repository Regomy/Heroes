package me.rejomy.heroes.util.inventory.implement.shop

import me.rejomy.heroes.util.inventory.InventoryBuilder
import me.rejomy.heroes.util.toColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class LifeInventory : InventoryBuilder("§0▷ Магазин магии", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        map[11] = createItemStack("§a§lLIFE", lore( arrayOf("&7" ,
                "&c ‣ &7Шлем &2Жизни &7даёт вам:   " ,
                "&c ‣ &7Защиту при смерти  " ,
                "&c ‣ &7Действует как тотем бессмертия  " ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_HELMET, 1)

        map[20] = createItemStack("§a§lLIFE", lore( arrayOf("&7" ,
                "&c ‣ &7Нагрудник &2Жизни &7даёт вам:   " ,
                "&c ‣ &7Защиту при смерти  ",
                "&c ‣ &7Действует как тотем бессмертия  " ,
                "&7",
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_CHESTPLATE, 1)

        map[29] = createItemStack("§a§lLIFE", lore( arrayOf("&7" ,
                "&c ‣ &7Поножи &2Жизни &7дают вам:   " ,
                "&c ‣ &7Защиту при смерти  " ,
                "&c ‣ &7Действует как тотем бессмертия  ",
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_LEGGINGS, 1)

        map[38] = createItemStack("§a§lLIFE", lore( arrayOf("&7",
                "&c ‣ &7Ботинки &2Жизни &7дают вам:   ",
                "&c ‣ &7Защиту при смерти  " ,
                "&c ‣ &7Действует как тотем бессмертия  " ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_BOOTS, 1)

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Клинок &2Жизни &7даёт вам:   ")
        lore.add("&c ‣ &7Восстановление хп, в размере  ")
        lore.add("&c ‣ &7нанесенного вами урона  ")
        lore.add("&7")
        lore.add("&c ‣ &7Сила клинка зависит от")
        lore.add("&c ‣ &7уровня вашего героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c8.200$   ")
        lore.add("&7")

        map[22] = createItemStack("XXL_SWORD", toColor( lore ), Material.DIAMOND_SWORD, 1)

        val loreexit = ArrayList<String>()
        loreexit.add("")
        loreexit.add("§5§lВернуться в меню")
        loreexit.add("§a§m--")
        loreexit.add("")
        loreexit.add("§a Нажмите§7 - §fчтобы открыть     ")
        loreexit.add("§f предыдущую страницу!")
        loreexit.add("")

        map[49]  = createItemStack("§7", toColor(loreexit), Material.BARRIER, 1)

        return createInv(map)
    }
}