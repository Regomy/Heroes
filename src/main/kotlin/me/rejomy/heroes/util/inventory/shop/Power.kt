package me.rejomy.heroes.util.inventory.shop

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Power : InventoryBuilder("§0▷ Магазин магии", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        map[11] = createItemStack(" ", lore(arrayOf("&7" ,
                "&c ‣ &7Шлем &4Силы &7даёт вам:   " ,
                "&c ‣ &7Меч соперника ломается быстрее " ,
                "&c ‣ &7Шанс уровень% отразить удар ",
                "&7" ,
                "&c ‣ &7Сила предмета зависит от" ,
                "&c ‣ &7количества вещей" ,
                "&c ‣ &7уровня вашего героя " ,
                "&7" ,
                "&c ‣ &7Цена предмета &c8.999\$   " ,
                "&7")), Material.DIAMOND_HELMET, 1)

        map[20] = createItemStack(" ", lore(arrayOf("&7" ,
                "&c ‣ &7Нагрудник &4Силы &7даёт вам:   " ,
                "&c ‣ &7Меч соперника ломается быстрее " ,
                "&c ‣ &7Шанс уровень% отразить удар " ,
                "&7" ,
                "&c ‣ &7Сила предмета зависит от" ,
                "&c ‣ &7количества вещей" ,
                "&c ‣ &7уровня вашего героя " ,
                "&7",
                "&c ‣ &7Цена предмета &c8.999\$   " ,
                "&7") ), Material.DIAMOND_CHESTPLATE, 1)

        map[29] = createItemStack(" ", lore(arrayOf("&7" ,
                "&c ‣ &7Поножи &4Силы &7дают вам:   " ,
                "&c ‣ &7Меч соперника ломается быстрее " ,
                "&c ‣ &7Шанс уровень% отразить удар " ,
                "&7" ,
                "&c ‣ &7Сила предмета зависит от" ,
                "&c ‣ &7количества вещей" ,
                "&c ‣ &7уровня вашего героя " ,
                "&7" ,
                "&c ‣ &7Цена предмета &c8.999\$   " ,
                "&7")), Material.DIAMOND_LEGGINGS, 1)

        map[38] = createItemStack(" ", lore(arrayOf("&7" ,
                "&c ‣ &7Ботинки &4Силы &7дают вам:   " ,
                "&c ‣ &7Меч соперника ломается быстрее " ,
                "&c ‣ &7Шанс уровень% отразить удар " ,
                "&7" ,
                "&c ‣ &7Сила предмета зависит от" ,
                "&c ‣ &7количества вещей" ,
                "&c ‣ &7уровня вашего героя ",
                "&7" ,
                "&c ‣ &7Цена предмета &c8.999\$   ",
                "&7")), Material.DIAMOND_BOOTS, 1)

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Экскалибур &4Силы &7даёт вам:   ")
        lore.add("&c ‣ &7Возможность ломать броню соперника   ")
        lore.add("&7")
        lore.add("&c ‣ &7Сила меча зависит от")
        lore.add("&c ‣ &7уровня вашего героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c16.000$   ")
        lore.add("&7")
        map[22] = createItemStack("EKSPA_LIBURRR", replaceColor( lore ), Material.DIAMOND_SWORD, 1)

        map[23] = createItemStack(" ", lore(arrayOf("&7" ,
            "&c ‣ &7Балда &4Силы &7даёт вам:   " ,
            "&c ‣ &7Шанс level% откинуть игрока " ,
            "&c ‣ &7с мощью в level%. " ,
            "&7" ,
            "&c ‣ &7Сила предмета зависит от" ,
            "&c ‣ &7уровня вашего героя ",
            "&7" ,
            "&c ‣ &7Цена предмета &c8.999\$   ",
            "&7")), Material.DIAMOND_SWORD, 1)

        map[31] = createItemStack(" ", lore(arrayOf("&7" ,
            "&c ‣ &7Дубина &4Силы &7даёт вам:   " ,
            "&c ‣ &7Шанс level% нанести x2 урон " ,
            "&7" ,
            "&c ‣ &7Сила предмета зависит от" ,
            "&c ‣ &7уровня вашего героя ",
            "&7" ,
            "&c ‣ &7Цена предмета &c22.999\$   ",
            "&7")), Material.IRON_SWORD, 1)

        val loreexit = ArrayList<String>()
        loreexit.add("")
        loreexit.add("§5§lВернуться в меню")
        loreexit.add("§a§m--")
        loreexit.add("")
        loreexit.add("§a Нажмите§7 - §fчтобы открыть     ")
        loreexit.add("§f предыдущую страницу!")
        loreexit.add("")

        map[49]  = createItemStack("§7", replaceColor(loreexit), Material.BARRIER, 1)

        return createInv(map)
    }
}