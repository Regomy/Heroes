package me.rejomy.heroes.listener

import me.rejomy.heroes.util.RaceHandle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent


class Interact : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {

        RaceHandle(event)

    }

}