package me.rejomy.heroes.util.inventory

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Newplayers : InventoryBuilder("§0▷ Выбор класса", 54, null) {

    fun openInventory(): Inventory {
        val map: HashMap<Int, ItemStack> = HashMap()
        val glassGray = createItemStack("", null, Material.STAINED_GLASS_PANE, 1, 7)
        val glassBlue = createItemStack("", null, Material.STAINED_GLASS_PANE, 1, 11)

        for (i in 0..53) {
            when (i) {
                in 10..16 -> continue
                in 19..25 -> {
                    if (i == 20) {
                        var lore = ArrayList<String>()
                        lore.add("")
                        lore.add("&c ‣ &7Магия &2Жизни &7даёт вам:")
                        val name = "2Жизнь"
                        lore.add("&c ‣ &7Дополнительное здоровье")
                        lore.add("&c ‣ &7Увеличенную скорость регенерации  ")
                        lore.add("&c ‣ &7Уникальные предметы и оружия  ")
                        lore.add("")
                        map[i] = createItemStack("§$name", replaceColor(lore), Material.IRON_BLOCK, 1)
                        continue
                    } else if (i == 22) {
                        var lore = ArrayList<String>()
                        lore.add("")
                        lore.add("&c ‣ &7Магия &9Порядка &7даёт вам:")
                        var name = "9Порядок"
                        lore.add("&c ‣ &7Увеличенную скорость бега  ")
                        lore.add("&c ‣ &7Уникальные предметы и оружия  ")
                        lore.add("")
                        map[i] = createItemStack("§$name", replaceColor(lore), Material.DIAMOND_BLOCK, 1)
                        continue
                    } else if (i == 24) {
                        var lore = ArrayList<String>()
                        lore.add("")
                        lore.add("&c ‣ &7Магия &4Силы &7даёт вам:")
                        var name = "4Сила"
                        lore.add("&c ‣ &7Увеличенный урон  ")
                        lore.add("&c ‣ &7Уникальные предметы и оружия  ")
                        lore.add("")
                        map[i] = createItemStack("§$name", replaceColor(lore), Material.REDSTONE_BLOCK, 1)
                        continue
                    } else
                        continue
                }

                in 28..34 -> continue
                in 37..44 -> continue
            }
            if (i % 2 == 0) map[i] = glassGray
            else map[i] = glassBlue
        }
        return createInv(map)
    }
}