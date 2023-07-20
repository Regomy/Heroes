package me.rejomy.heroes.listener.duel

import me.realized.duels.api.event.match.MatchEndEvent
import me.realized.duels.api.event.match.MatchStartEvent
import me.rejomy.heroes.users
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class MatchListener : Listener {

    fun clearAbilities(player: Player) {
        if (!users.containsKey(player.name)) return
        player.walkSpeed = 0.2F
        player.healthScale = 20.0
        player.health = player.maxHealth
    }

    fun setAbilities(player: Player) {
        if (!users.containsKey(player.name)) return
        when (users[player.name]!![0]) {
            "смерть" -> player.healthScale = 12.0
            "жизнь" -> player.healthScale = 22.0 + users[player.name]!![1].toInt()
            "порядок" -> player.walkSpeed = 0.2F + 0.015F + users[player.name]!![1].toFloat() / 400
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMatchStart(event: MatchStartEvent) {
        val players = event.players
        clearAbilities(players[0])
        clearAbilities(players[1])
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMatchEnd(event: MatchEndEvent) {
        if(Bukkit.getPlayer(event.winner) != null)
            setAbilities(Bukkit.getPlayer(event.winner))
        if(Bukkit.getPlayer(event.loser) != null)
            setAbilities(Bukkit.getPlayer(event.loser))
    }

}