package me.rejomy.heroes.util.inventory.implement

import me.rejomy.heroes.users
import me.rejomy.heroes.util.inventory.InventoryBuilder
import me.rejomy.heroes.util.toColor
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class TopInventory : InventoryBuilder("§0▷ Топ по прокачке", 54) {

    fun openInventory(): Inventory {
        val map: HashMap<Int, ItemStack> = HashMap()
        customization(map)

        val slots = arrayOf(10..16, 19..25, 28..34, 37..43)

        val level = ArrayList<Int>()
        val player = ArrayList<String>()

        val cloneUsers = users.toMutableMap()

        for (i in 0..28) {
            var tname = "null**"
            var tcount = 0
            cloneUsers.forEach { (name, heroData) ->
                if (tcount < heroData.level) {
                    tcount = heroData.level
                    tname = name
                }
            }
            cloneUsers.remove(tname)
            player.add(tname)
            level.add(tcount)
        }

        var pos = 1

        for (set in slots) {
            for (i in set) {
                if (player[0] == "null**") break
                map[i] = createItemStack(
                    " ", lore(
                        arrayOf(
                            "&7",
                            "&c ‣ &7${player[0]} - &a#${pos}   ",
                            "&c ‣ &7Уровень прокачки - §e${level[0]} ",
                            "&c ‣ &7Раса - §e${users[player[0]]!!.type.russianType} ",
                            "&7"
                        )
                    ), getPlayerSkull(player[0]), 1
                )
                player.removeAt(0)
                level.removeAt(0)
                pos++
            }
        }

        map[49] = createItemStack(
            "§7", arrayOf(
                "",
                "§5§lВернуться в меню",
                "§a§m--",
                "",
                "§a Нажмите§7 - §fчтобы открыть     ",
                "§f предыдущую страницу!",
                ""
            ), Material.BARRIER, 1
        )

        return createInv(map)
    }

    // Создаем функцию, которая возвращает ItemStack с головой игрока
    private fun getPlayerSkull(playerName: String): ItemStack {
        val itemStack = ItemStack(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal.toShort())

        // Получаем метаданные предмета (ItemMeta)
        val itemMeta = itemStack.itemMeta as SkullMeta

        // Устанавливаем имя игрока для головы
        itemMeta.owner = playerName

        // Применяем метаданные к предмету
        itemStack.itemMeta = itemMeta

        return itemStack
    }

}