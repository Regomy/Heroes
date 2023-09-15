package me.rejomy.heroes.listener

import me.rejomy.heroes.util.HeroUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent

class Enchant: Listener {

    @EventHandler
    fun onEnchant(event: EnchantItemEvent) {

        val item = event.item

        if(!HeroUtil.isHeroItem(item)) return

        val itemMeta = item.itemMeta

        val lore = itemMeta.lore

        lore.removeIf { it.contains("›") }

        for ((key, value) in event.enchantsToAdd) {
            lore[lore.size - 1] = " §6› §7${key.name} §6$value"
        }

        itemMeta.lore = lore

        item.itemMeta = itemMeta

    }

}