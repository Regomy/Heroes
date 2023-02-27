package me.rejomy.heroes.listener

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
            if(users[name]!![0] == "жизнь")
                player.healthScale = player.healthScale + (users[name]!![1].toInt() * 2)
            if(users[name]!![0] == "порядок") {
                val speed = 1 + users[name]!![1].toFloat() / 10
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "speed walk $speed $name")
            }
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val name = player.name
        nekro.remove(name)
    }

}