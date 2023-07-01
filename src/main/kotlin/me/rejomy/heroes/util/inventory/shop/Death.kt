package me.rejomy.heroes.util.inventory.shop

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Death : InventoryBuilder("§0▷ Магазин магии", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        map[11] = createItemStack(
            " ", lore(
                arrayOf(
                    "&7",
                    "&c ‣ &7Шлем &8Смерти &7даёт вам:   ",
                    "&c ‣ &7Шанс уровень% наложить слабость ",
                    "&c ‣ &7на атакующего вас игрока. ",
                    "&7",
                    "&c ‣ &7Сила предмета зависит от",
                    "&c ‣ &7уровня вашего героя ",
                    "&7",
                    "&c ‣ &7Цена предмета &c6.000\$   ",
                    "&7"
                )
            ), Material.DIAMOND_HELMET, 1
        )

        map[20] = createItemStack(
            " ", lore(
                arrayOf(
                    "&7",
                    "&c ‣ &7Нагрудник &8Смерти &7даёт вам:   ",
                    "&c ‣ &7Шанс уровень% наложить слепоту ",
                    "&c ‣ &7на атакующего вас игрока. ",
                    "&7",
                    "&c ‣ &7Сила предмета зависит от",
                    "&c ‣ &7уровня вашего героя ",
                    "&7",
                    "&c ‣ &7Цена предмета &c3.000\$   ",
                    "&7"
                )
            ), Material.DIAMOND_CHESTPLATE, 1
        )

        map[29] = createItemStack(
            " ", lore(
                arrayOf(
                    "&7",
                    "&c ‣ &7Поножи &8Смерти &7дают вам:   ",
                    "&c ‣ &7Шанс уровень% наложить отравление ",
                    "&c ‣ &7на атакующего вас игрока. ",
                    "&7",
                    "&c ‣ &7Сила предмета зависит от",
                    "&c ‣ &7количества вещей",
                    "&c ‣ &7уровня вашего героя ",
                    "&7",
                    "&c ‣ &7Цена предмета &c4.000\$   ",
                    "&7"
                )
            ), Material.DIAMOND_LEGGINGS, 1
        )

        map[38] = createItemStack(
            " ", lore(
                arrayOf(
                    "&7",
                    "&c ‣ &7Ботинки &8Смерти &7дают вам:   ",
                    "&c ‣ &7Шанс уровень% наложить медлительность ",
                    "&c ‣ &7на атакующего вас игрока. ",
                    "&7",
                    "&c ‣ &7Сила предмета зависит от",
                    "&c ‣ &7количества вещей",
                    "&c ‣ &7уровня вашего героя ",
                    "&7",
                    "&c ‣ &7Цена предмета &c5.999\$   ",
                    "&7"
                )
            ), Material.DIAMOND_BOOTS, 1
        )

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Кунь &8Смерти &7даёт вам:   ")
        lore.add("&c ‣ &7Возможность своровать 1 чарку   ")
        lore.add("&c ‣ &7с шансом level% у соперника в бою.   ")
        lore.add("&7")
        lore.add("&c ‣ &7Сила меча зависит от")
        lore.add("&c ‣ &7уровня вашего героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c16.000$   ")
        lore.add("&7")
        map[22] = createItemStack("KUN OF DEATH", replaceColor(lore), Material.DIAMOND_SWORD, 1)

        map[23] = createItemStack(
            " ", lore(
                arrayOf(
                    "&7",
                    "&c ‣ &7Коса &8Смерти &7даёт вам:   ",
                    "&c ‣ &7Возможность бить соперников ",
                    "&c ‣ &7возле основной цели. ",
                    "&7",
                    "&c ‣ &7Сила предмета зависит от",
                    "&c ‣ &7уровня вашего героя ",
                    "&7",
                    "&c ‣ &7Цена предмета &c42.100\$   ",
                    "&7"
                )
            ), Material.DIAMOND_SWORD, 1
        )

        val loreexit = ArrayList<String>()
        loreexit.add("")
        loreexit.add("§5§lВернуться в меню")
        loreexit.add("§a§m--")
        loreexit.add("")
        loreexit.add("§a Нажмите§7 - §fчтобы открыть     ")
        loreexit.add("§f предыдущую страницу!")
        loreexit.add("")

        map[49] = createItemStack("§7", replaceColor(loreexit), Material.BARRIER, 1)

        return createInv(map)
    }
}