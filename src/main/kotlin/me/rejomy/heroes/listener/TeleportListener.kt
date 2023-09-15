package me.rejomy.heroes.listener

import me.rejomy.heroes.users
import me.rejomy.heroes.util.RaceHandle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class TeleportListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onTeleport(event: PlayerTeleportEvent) {

        RaceHandle(event)

    }

}