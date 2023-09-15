package me.rejomy.heroes.listener

import me.rejomy.heroes.util.RaceHandle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class DamageListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onFight(event: EntityDamageByEntityEvent) {

        RaceHandle(event)

    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {

        RaceHandle(event)

    }

}