package me.rejomy.heroes.listener

import me.rejomy.heroes.util.RaceHandle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent

class Death : Listener {

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {

        RaceHandle(event)

    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {

        RaceHandle(event)
    }

}