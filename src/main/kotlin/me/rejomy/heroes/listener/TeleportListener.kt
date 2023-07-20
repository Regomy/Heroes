package me.rejomy.heroes.listener

import me.rejomy.heroes.duels
import me.rejomy.heroes.users
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class TeleportListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onTeleport(event: PlayerTeleportEvent) {
        val player = event.player
        if(!users.containsKey(player.name)) return
        if(event.to.world == event.from.world) return
        when(users[player.name]!![0]) {
            "порядок" -> player.walkSpeed = 0.2F + 0.015F + users[player.name]!![1].toFloat() / 400
        }
    }

}