package me.rejomy.heroes.util.inventory

import me.rejomy.heroes.users
import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.getPriceBook
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Upgrade(private val player: Player) : InventoryBuilder("§0▷ Некромант", 54) {

    val pname: String = player.name

    fun openInventory(): Inventory {
        val map: HashMap<Int, ItemStack> = HashMap()
        customization(map)

        if(users[pname]!![1].toInt() == 20) {

        } else {
            var name = ""
            val lore = ArrayList<String>()
            lore.add("")
            val level = users[pname]!![1].toInt()
            when (users[pname]!![0]) {
                "жизнь" -> {
                    name = "2Жизни"
                    lore.add("&c ‣ &7Увеличения здоровья на 1 хп за уровень   ")
                    lore.add("&c ‣ &7Шанс $level% получить сопротивление   ")
                    lore.add("&c ‣ &7Увеличения скорости регенерации  ")
                }

                "порядок" -> {
                    name = "9Порядка"
                    lore.add("&c ‣ &7Увеличения скорость бега на 1.0 ")
                }

                "сила" -> {
                    name = "4Силы"
                    lore.add("&c ‣ &7Повышения урона на 0.1 за уровень ")
                }

                "смерть" -> {
                    name = "8Смерти"
                    lore.add("&c ‣ &7Шанс ${20 + level}% сохранить ${30 + level}% инвентаря.")
                    lore.add("&c ‣ &7Шанс ${80 + level}% на блокировку магии врага.")
                }

            }
            lore.add(1, "&c ‣ &7Нажмите на книгу &$name&7 для    ")
            lore.add("")
            lore.add("&c ‣ &7Ваш уровень: ${users[pname]!![1]}")
            lore.add("&c ‣ &7Цена улучшения: ${getPriceBook(player)}")
            lore.add("")
            map[22] = createItemStack("§6Книга §$name", replaceColor(lore), Material.BOOK, 1)
        }

        map[33] = createItemStack(
            "§7", lore(
                arrayOf(
                    "", "§5§lОткрыть магазин вещей   ", "§a§m--", "", "§a Нажмите§7 - §fчтобы открыть магазин     ",
                    "§f предметов для вашей способности!", ""
                )
            ), Material.FLOWER_POT_ITEM, 1
        )

        map[29] = createItemStack(
            "§7",
            lore(
                arrayOf(
                    "",
                    "§5§lТоп по прокачке",
                    "§a§m--",
                    "§a Нажмите§7 - §fчтобы открыть топ     ",
                    "§f Обновление происходит с перезагрузкой.    ",
                    ""
                )
            ),
            Material.GOLD_INGOT,
            1
        )

        map[49] = createItemStack(
            "§7",
            lore(
                arrayOf(
                    "",
                    "§5§lСбросить текущий класс",
                    "§a§m--",
                    "§a Нажмите§7 - §fчтобы заново выбрать     ",
                    "§f класс, но вы потеряете уровень прокачки     ",
                    "§f и половину затраченного на прокачку баланса!     ",
                    ""
                )
            ),
            Material.BARRIER,
            1
        )

        return createInv(map)
    }
}