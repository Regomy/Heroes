package me.rejomy.heroes.listener

import me.rejomy.heroes.users
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Fight: Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onFight(event: EntityDamageByEntityEvent) {
        if(event.isCancelled) return

        if(event.damager is Player) {
            val killer = event.damager as Player
            val kname = killer.name
            val kcont = users.containsKey(kname)
            if(kcont && users[kname]!![0] == "сила")
                event.damage = event.damage + 0.1 * users[kname]!![1].toInt()
        }

        if(event.entity is Player) {
            val player = event.entity as Player
            val name = player.name
            val cont = users.containsKey(name)
            if(cont && users[name]!![0] == "жизнь") {
                event.damage = event.damage - (1 * (users[name]!![1].toInt() / 2))
            }
        }
    }

}