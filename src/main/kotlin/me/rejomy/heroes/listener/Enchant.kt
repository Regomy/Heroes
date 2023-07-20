package me.rejomy.heroes.listener

import me.rejomy.heroes.util.checkLore
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent

class Enchant: Listener {

    @EventHandler
    fun onEnchant(event: EnchantItemEvent) {
        val item = event.item
        if(!item.hasItemMeta() || !item.itemMeta.hasLore() ||
            !checkLore(item.itemMeta.lore, "Жизн") && !checkLore(item.itemMeta.lore, "Поряд")
            && !checkLore(item.itemMeta.lore, "Смерт") && !checkLore(item.itemMeta.lore, "Сил")) return
        val itemMeta = item.itemMeta

        if(item.type == Material.BOW) {
            event.enchanter.sendMessage("Вы не можете зачаровать данный предмет!")
            event.isCancelled = true
            return
        }

        val checklore = itemMeta.lore
        val newLore = itemMeta.lore
        for(i in checklore) {
            if(i.contains("›"))
                newLore.remove(i)
        }

        for ((key, value) in event.enchantsToAdd) {
            newLore[newLore.size - 1] = " §6› §7${key.name} §6$value"
        }

        itemMeta.lore = newLore
        item.itemMeta = itemMeta

    }

}