package me.rejomy.heroes.util.inventory.implement

import me.rejomy.heroes.util.inventory.InventoryBuilder
import me.rejomy.heroes.util.toColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RemoveHeroConfirmInventory : InventoryBuilder("§0▷ Сброс характеристик героя:", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        map[21] = createItemStack(
            "§7", arrayOf(
                "",
                "§5§lВернутся в меню",
                "§a§m--",
                "",
                "§a Нажмите§7 - §fчтобы отказатся       ",
                "§f от сброса класса!",
                ""
            ), Material.BARRIER, 1
        )

        map[23] = createItemStack(
            "§7", arrayOf(
                "",
                "§5§lСбросить текущий класс",
                "§a§m--",
                "",
                "§a Нажмите§7 - §fчтобы сбросить     ",
                "§f свою прокачку героя!",
                "§f Вы получите 50% суммы,",
                "§f потраченной на прокачку.",
                ""
            ), Material.BEDROCK, 1
        )

        return createInv(map)
    }

}