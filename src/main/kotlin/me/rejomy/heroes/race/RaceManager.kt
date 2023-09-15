package me.rejomy.heroes.race

import me.rejomy.heroes.race.implementation.DeathRace
import me.rejomy.heroes.race.implementation.LifeRace
import me.rejomy.heroes.race.implementation.OrderRace
import me.rejomy.heroes.race.implementation.PowerRace

class RaceManager {

    companion object{

        private val races = ArrayList<Race>()

        fun getRaces(): ArrayList<Race> {
            return races
        }

    }

    init {

        races.add(DeathRace())
        races.add(PowerRace())
        races.add(LifeRace())
        races.add(OrderRace())

    }

}
