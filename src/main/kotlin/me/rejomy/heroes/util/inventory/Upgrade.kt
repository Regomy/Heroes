package me.rejomy.heroes.util.inventory

import me.rejomy.heroes.users
import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.getPriceBook
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Upgrade(private val player: Player) : InventoryBuilder("§0▷ Некромант", 54, null) {

    val pname: String = player.name

    fun openInventory(): Inventory {
        val map: HashMap<Int, ItemStack> = HashMap()
        val glassGray = createItemStack("§6", null, Material.STAINED_GLASS_PANE, 1, 7)
        val glassBlue = createItemStack("§6", null, Material.STAINED_GLASS_PANE, 1, 11)

        for (i in 0..53) {
            when (i) {
                in 10..16 -> continue
                in 19..25 -> {
                    if (i == 22) {
                        var name = ""
                        var lore = ArrayList<String>()
                        lore.add("")
                        when (users[pname]!![0]) {
                            "жизнь" -> {
                                name = "2Жизни"
                                lore.add("&c ‣ &7Увеличения здоровья")
                                lore.add("&c ‣ &7Увеличения скорости регенерации  ")
                            }

                            "порядок" -> {
                                name = "9Порядка"
                                lore.add("&c ‣ &7Увеличения скорость бега  ")
                            }

                            "сила" -> {
                                name = "4Силы"
                                lore.add("&c ‣ &7Повышения урона  ")
                            }
                        }
                        lore.add(1, "&c ‣ &7Нажмите на книгу &$name&7 для    ")
                        lore.add("")
                        lore.add("&c ‣ &7Ваш уровень: ${users[pname]!![1]}")
                        lore.add("&c ‣ &7Цена улучшения: ${getPriceBook(player)}")
                        lore.add("")
                        map[i] = createItemStack("§6Книга §$name", replaceColor(lore), Material.BOOK, 1)
                    }
                    continue
                }

                in 28..34 -> {
                    if (i == 33) {
                        var lore = ArrayList<String>()
                        lore.add("")
                        lore.add("§5§lОткрыть магазин вещей   ")
                        lore.add("§a§m--")
                        lore.add("")
                        lore.add("§a Нажмите§7 - §fчтобы открыть магазин     ")
                        lore.add("§f предметов для вашей способности!")
                        lore.add("")
                        map[i] = createItemStack("§7", replaceColor(lore), Material.FLOWER_POT_ITEM, 1)
                    }
                    continue
                }

                in 37..43 -> continue
                49 -> {
                    var lore = ArrayList<String>()
                    lore.add("")
                    lore.add("§5§lСбросить текущий класс")
                    lore.add("§a§m--")
                    lore.add("")
                    lore.add("§a Нажмите§7 - §fчтобы заново выбрать     ")
                    lore.add("§f класс, но вы потеряете уровень прокачки!")
                    lore.add("")
                    map[i] = createItemStack("§7", replaceColor(lore), Material.BARRIER, 1)
                    continue
                }
            }
            if (i % 2 == 0) map[i] = glassGray
            else map[i] = glassBlue
        }

        return createInv(map)
    }
}