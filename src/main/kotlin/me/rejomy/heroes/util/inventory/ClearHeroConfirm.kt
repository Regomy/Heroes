package me.rejomy.heroes.util.inventory

import me.rejomy.heroes.util.InventoryBuilder
import me.rejomy.heroes.util.replaceColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class ClearHeroConfirm : InventoryBuilder("§0▷ Сброс характеристик героя:", 54) {

    fun openInventory(): Inventory {
        var map: HashMap<Int, ItemStack> = HashMap()
        map = customization(map)

        val deny = ArrayList<String>()
        deny.add("")
        deny.add("§5§lВернутся в меню")
        deny.add("§a§m--")
        deny.add("")
        deny.add("§a Нажмите§7 - §fчтобы отказатся     ")
        deny.add("§f от сброса класса!")
        deny.add("")
        map[21]  = createItemStack("§7", replaceColor(deny), Material.BARRIER, 1)

        val access = ArrayList<String>()
        access.add("")
        access.add("§5§lСбросить текущий класс")
        access.add("§a§m--")
        access.add("")
        access.add("§a Нажмите§7 - §fчтобы сбросить     ")
        access.add("§f свою прокачку героя!")
        access.add("§f Вы получите 50% суммы,")
        access.add("§f потраченной на прокачку.")
        access.add("")
        map[23]  = createItemStack("§7", replaceColor(access), Material.BEDROCK, 1)

        return createInv(map)
    }

}