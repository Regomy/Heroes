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
import kotlin.math.floor


class Interact : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val name = player.name
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            val item = player.itemInHand
            if(users.containsKey(name) && item != null && item.itemMeta != null && item.itemMeta.lore != null && item.type == Material.BOW
                && checkLore(item.itemMeta.lore, "Скорострельность")
                && users[name]!![0] == "порядок" && containsItem(player, Material.ARROW)
            ) {

                val level = (users[name]!![1]).toInt()

                if((item.durability + (4 - floor( (level/10).toDouble() ).toInt())).toShort() >= 384) {
                    player.inventory.setItem(player.inventory.heldItemSlot, null)
                    event.isCancelled = true
                    return
                }

                removeOne(player.inventory, ItemStack(Material.ARROW))
                val arrow = player.launchProjectile(Arrow::class.java)
                arrow.velocity.multiply(2.0 + level/20)
                arrow.knockbackStrength = arrow.knockbackStrength + (level / 4)
                item.durability = (item.durability + (4 - floor( (level/10).toDouble() ).toInt())).toShort()
                player.itemInHand = item
                event.isCancelled = true
            }

        }
    }

}