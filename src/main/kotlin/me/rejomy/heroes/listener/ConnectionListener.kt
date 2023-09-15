package me.rejomy.heroes.listener

import me.rejomy.heroes.nekro
import me.rejomy.heroes.util.HeroUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectionListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        HeroUtil.setAbilities(player)

    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val name = player.name

        nekro.remove(name)

        HeroUtil.clearAbilities(player)

    }

}