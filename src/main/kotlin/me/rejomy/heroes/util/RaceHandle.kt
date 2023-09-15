package me.rejomy.heroes.util

import me.rejomy.heroes.race.RaceManager
import org.bukkit.event.Event

class RaceHandle(event: Event) {

    init {

        val races = RaceManager.getRaces()

        for(race in races) {

            race.handle(event)

            if(race.isCancelled) {
                break
            }

        }

    }

}
