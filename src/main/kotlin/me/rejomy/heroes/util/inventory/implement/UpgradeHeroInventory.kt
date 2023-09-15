package me.rejomy.heroes.util.inventory.implement

import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import me.rejomy.heroes.util.inventory.InventoryBuilder
import me.rejomy.heroes.util.getPriceBook
import me.rejomy.heroes.util.toColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class UpgradeHeroInventory(val name: String) : InventoryBuilder("§0▷ Некромант", 54) {

    fun openInventory(): Inventory {

        val map: HashMap<Int, ItemStack> = HashMap()

        customization(map)

        val heroData = users[name]!!

        val level = heroData.level

        if (heroData.level != 20) {

            var race = ""

            val lore = ArrayList<String>()

            lore.add("")

            when (heroData.type) {

                RaceType.LIFE -> {
                    race = "2Жизни"

                    lore.add("&c ‣ &7Увеличения здоровья на 1 хп за уровень   ")
                    lore.add("&c ‣ &7Шанс $level% получить сопротивление   ")
                    lore.add("&c ‣ &7Увеличения скорости регенерации  ")

                }

                RaceType.ORDER -> {
                    race = "9Порядка"

                    lore.add("&c ‣ &7Увеличения скорость бега на 1.0 ")

                }

                RaceType.POWER -> {

                    race = "4Силы"

                    lore.add("&c ‣ &7Повышения урона на 0.1 за уровень ")
                }

                RaceType.DEATH -> {

                    race = "8Смерти"

                    lore.add("&c ‣ &7Шанс ${20 + level}% сохранить ${30 + level}% инвентаря.")
                    lore.add("&c ‣ &7Шанс ${80 + level}% на блокировку магии врага.")
                }

            }

            lore.add(1, "&c ‣ &7Нажмите на книгу &$race&7 для    ")

            lore.add("")

            lore.add("&c ‣ &7Ваш уровень: $level")

            lore.add("&c ‣ &7Цена улучшения: ${getPriceBook(level)}")

            lore.add("")

            map[22] = createItemStack("§6Книга §$race", toColor(lore), Material.BOOK, 1)
        }

        map[33] = createItemStack(
            "§7",
            arrayOf(
                "", "§5§lОткрыть магазин вещей   ", "§a§m--", "", "§a Нажмите§7 - §fчтобы открыть магазин     ",
                "§f предметов для вашей способности!", ""
            ), Material.FLOWER_POT_ITEM, 1
        )

        map[29] = createItemStack(
            "§7",
            arrayOf(
                "",
                "§5§lТоп по прокачке",
                "§a§m--",
                "§a Нажмите§7 - §fчтобы открыть топ     ",
                "§f Обновление происходит с перезагрузкой.    ",
                ""
            ),
            Material.GOLD_INGOT,
            1
        )

        map[49] = createItemStack(
            "§7",
            arrayOf(
                "",
                "§5§lСбросить текущий класс",
                "§a§m--",
                "§a Нажмите§7 - §fчтобы заново выбрать     ",
                "§f класс, но вы потеряете уровень прокачки     ",
                "§f и половину затраченного на прокачку баланса!     ",
                ""
            ),
            Material.BARRIER,
            1
        )

        return createInv(map)
    }
}