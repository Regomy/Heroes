package me.rejomy.heroes.util.inventory.shop

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Order : InventoryBuilder("§0▷ Магазин магии", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)


        map[11] = createItemStack(" ", lore( arrayOf("&7" ,
                "&c ‣ &7Шлем &9Порядка &7даёт вам:   ",
                "&c ‣ &7Блокировку действий всех классов  ",
                "&7" ,
                "&c ‣ &7Работает только когда надет весь сет" ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_HELMET, 1)

        map[20] = createItemStack(" ", lore( arrayOf("&7",
                "&c ‣ &7Нагрудник &9Порядка &7даёт вам:   " ,
                "&c ‣ &7Блокировку действий всех классов  " ,
                "&7" ,
                "&c ‣ &7Работает только когда надет весь сет" ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_CHESTPLATE, 1)

        map[29] = createItemStack(" ", lore( arrayOf("&7" ,
                "&c ‣ &7Поножи &9Порядка &7дают вам:   " ,
                "&c ‣ &7Блокировку действий всех классов  " ,
                "&7" +
                "&c ‣ &7Работает только когда надет весь сет" ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   ",
                "&7") ), Material.DIAMOND_LEGGINGS, 1)

        map[38] = createItemStack(" ", lore( arrayOf("&7" ,
                "&c ‣ &7Ботинки &9Порядка &7дают вам:   " ,
                "&c ‣ &7Блокировку действий всех классов  " ,
                "&7" ,
                "&c ‣ &7Работает только когда надет весь сет" ,
                "&7" ,
                "&c ‣ &7Цена предмета &c5.999\$   " ,
                "&7") ), Material.DIAMOND_BOOTS, 1)


        map[33] = createItemStack(" ", lore( arrayOf("&7" ,
            "&c ‣ &7Яблоко &9Спасения &7даёт вам:   " ,
            "&c ‣ &7Блокировку всех вражеских атак  " ,
            "&c ‣ &7- в течение 5+ур/4 секунд  " ,
            "&7" ,
            "&c ‣ &7Действие предмета зависит от  " ,
            "&c ‣ &7уровня вашего героя.  " ,
            "",
            "&c ‣ &7Цена предмета &c4.000\$   " ,
            "&7") ), Material.GOLDEN_APPLE, 1)

        val lore = ArrayList<String>()
        lore.add("&7")
        lore.add("&c ‣ &7Лук &9Порядка &7даёт вам:   ")
        lore.add("&c ‣ &7Скорострельность")
        lore.add("&c ‣ &7Повышенный урон")
        lore.add("&c ‣ &7Повышенную отдачу")
        lore.add("&7")
        lore.add("&c ‣ &7Сила и прочность лука зависят от  ")
        lore.add("&c ‣ &7уровня вашего героя ")
        lore.add("&7")
        lore.add("&c ‣ &7Цена предмета &c12.700$   ")
        lore.add("&7")

        map[24] = createItemStack("XBOW", replaceColor( lore ), Material.BOW, 1)

        val SWORD = ArrayList<String>()
        SWORD.add("&7")
        SWORD.add("&c ‣ &7Меч &9Порядка &7даёт вам:   ")
        SWORD.add("&c ‣ &7Заморозку врага на 2+lvl/5 секунд   ")
        SWORD.add("&7")
        SWORD.add("&c ‣ &7Сила меча зависит от")
        SWORD.add("&c ‣ &7уровня вашего героя ")
        SWORD.add("&7")
        SWORD.add("&c ‣ &7Цена предмета &c7.000$   ")
        SWORD.add("&7")

        map[25] = createItemStack("XSWORD", replaceColor( SWORD ), Material.DIAMOND_SWORD, 1)

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