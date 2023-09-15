package me.rejomy.heroes.listener

import me.realized.duels.api.event.match.MatchEndEvent
import me.realized.duels.api.event.match.MatchStartEvent
import me.rejomy.heroes.util.HeroUtil
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class DuelsMatchListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMatchStart(event: MatchStartEvent) {
        val players = event.players

        HeroUtil.clearAbilities(players[0])

        HeroUtil.clearAbilities(players[1])
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onMatchEnd(event: MatchEndEvent) {

        if(Bukkit.getPlayer(event.winner) != null)
            HeroUtil.setAbilities(Bukkit.getPlayer(event.winner))

        if(Bukkit.getPlayer(event.loser) != null)
            HeroUtil.setAbilities(Bukkit.getPlayer(event.loser))

    }

}