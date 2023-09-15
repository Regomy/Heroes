package me.rejomy.heroes.race

import org.bukkit.event.Event
import kotlin.random.Random

abstract class Race(val type: RaceType) {

    var isCancelled = false

    val random = Random

    abstract fun handle(event: Event)

    fun checkRaceType(type: RaceType): Boolean {
        return type == this.type
    }

}
