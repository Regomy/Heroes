package me.rejomy.heroes.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

val eatPlayers = HashMap<Player, Long>()

class ItemRegisterListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onConsume(event: PlayerInteractEvent) {
        val player = event.player

        if (event.item != null && event.item.type.isEdible)

            if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK)
                eatPlayers[player] = System.currentTimeMillis()
    }

}