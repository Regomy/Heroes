package me.rejomy.heroes.listener

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.nekro
import me.rejomy.heroes.users
import me.rejomy.heroes.util.inventory.Upgrade
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Connection: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val name = player.name
        if(users.containsKey(name)) {
            nekro[name] = Upgrade(player).openInventory()
            if(users[name]!![0] == "смерть")
                player.healthScale = 12.0
            if(users[name]!![0] == "жизнь")
                player.healthScale = 22.0 + users[name]!![1].toInt()
            else if(users[name]!![0] == "порядок")
                Bukkit.getScheduler().scheduleSyncDelayedTask(INSTANCE, {
                    player.walkSpeed = 0.2F + 0.015F + users[name]!![1].toFloat() / 400
                }, 20)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val name = player.name
        nekro.remove(name)
    }

}