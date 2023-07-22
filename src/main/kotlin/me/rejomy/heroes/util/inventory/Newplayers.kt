package me.rejomy.heroes.util.inventory

import me.rejomy.heroes.util.InventoryBuilder
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class Newplayers : InventoryBuilder("§0▷ Выбор класса", 54) {

    fun openInventory(): Inventory {
        val map: HashMap<Int, ItemStack> = HashMap()
        customization(map)

        map[19] = createItemStack(
            "§2Жизнь", lore(
                arrayOf(
                    "",
                    "&c ‣ &7Магия &2Жизни &7даёт вам:",
                    "&c ‣ &7Дополнительное здоровье 1хп + 0.5 за лвл",
                    "&c ‣ &7Увеличенную регенерацию  ",
                    "&c ‣ &7Шанс level% получить сопротивление  ",
                    "&c ‣ &7Уникальные предметы и оружия  ",
                    ""
                )
            ), Material.IRON_BLOCK, 1
        )

        map[21] = createItemStack(
            "§9Порядок", lore(
                arrayOf(
                    "", "&c ‣ &7Магия &9Порядка &7даёт вам:",
                    "&c ‣ &7Увеличенную скорость бега на 5% + 5% за лвл  ", "&c ‣ &7в бою шанс 10+лвл% получить ускорение. ", "&c ‣ &7Уникальные предметы и оружия  ", ""
                )
            ), Material.DIAMOND_BLOCK, 1
        )

        map[23] = createItemStack(
            "§4Сила", lore(
                arrayOf(
                    "", "&c ‣ &7Магия &4Силы &7даёт вам:",
                    "&c ‣ &7Увеличенный урон на 0.1 + 0.1 за лвл ",
                    "&c ‣ &7Броня врага ломается быстрее ",
                    "&c ‣ &7Уникальные предметы и оружия  ", ""
                )
            ), Material.REDSTONE_BLOCK, 1
        )

        map[25] = createItemStack(
            "§8Смерть", lore(
                arrayOf(
                    "", "&c ‣ &7Магия &8Смерти &7даёт вам:", "&c ‣ &7Шанс 20+уровень%  ",
                    "&c ‣ &7сохранить 30+уровень% инвентаря.  ", "&c ‣ &7Шанс игнора всех магий 80+lvl%.", ""
                )
            ), Material.COAL_BLOCK, 1
        )

        return createInv(map)
    }
}