package me.rejomy.heroes.listener

import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import me.rejomy.heroes.util.containsItem
import me.rejomy.heroes.util.removeOne
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack


class Interact : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val name = player.name
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            val item = player.itemInHand
            if(item != null && item.itemMeta != null && item.itemMeta.lore != null && item.type == Material.BOW
                && checkLore(item.itemMeta.lore, "Скорострельность")
                && checkLore(item.itemMeta.lore, name)
                && users[name]!![0] == "порядок" && containsItem(player, Material.ARROW)
            ) {
                if(item.durability >= item.type.maxDurability) {
                    player.itemInHand = ItemStack(Material.AIR)
                    return
                }
                removeOne(player.inventory, ItemStack(Material.ARROW))
                val arrow = player.launchProjectile(Arrow::class.java)
                val level = (users[name]!![1]).toInt()
                if(level > 1) {
                    val vec = arrow.velocity
                    vec.multiply(1 + (level / 10))
                    arrow.velocity = vec
                    arrow.knockbackStrength = arrow.knockbackStrength + (level / 10)
                }

                item.durability = (item.durability + 3).toShort()
                player.itemInHand = item
                event.isCancelled = true
            }

        }
    }

}