package me.rejomy.heroes.race.implementation

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.race.Race
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import kotlin.math.floor

class PowerRace : Race(RaceType.POWER) {
    override fun handle(event: Event) {

        if (event is EntityDamageByEntityEvent) {

            val victim = event.entity
            var killer = event.damager

            val killerName = killer.name
            val victimName = victim.name

            var killerHeroData = users[killerName]
            val victimHeroData = users[victimName]

            if (victim is Player && victimHeroData != null && checkRaceType(victimHeroData.type)) {

                for (armor in victim.inventory.armorContents) {

                    if (armor == null || !armor.hasItemMeta() || !armor.itemMeta.hasLore()
                        || !checkLore(armor.itemMeta.lore, "Меч соперника ломается быстрее")
                    ) continue

                    if (killer is Player) {

                        if (random.nextInt(100) <= victimHeroData.level) {
                            killer.damage(event.finalDamage)
                        }

                        if (killer.itemInHand != null && killer.itemInHand.type.name.contains("SWORD")) {
                            val item = killer.itemInHand
                            if ((item.durability + 2).toShort() >= item.type.maxDurability) {
                                victim.itemInHand.type = Material.AIR
                            } else {
                                item.durability = (item.durability + 1 + floor(
                                    (victimHeroData.level / 10).toDouble()
                                )).toInt().toShort()
                                killer.itemInHand = item
                            }
                        }

                        break

                    }
                }

            }

            if (killer is Player && killerHeroData != null && checkRaceType(killerHeroData.type) && victim is LivingEntity) {

                if (!checkRaceType(killerHeroData.type) ||
                    INSTANCE.duels != null && INSTANCE.duels!!.arenaManager.isInMatch(killer)
                ) return

                if (victim.maxHealth / 2 < victim.health && random.nextInt(100) + 1 < 20 + killerHeroData.level) {
                    event.damage *= 1.1 + killerHeroData.level / 10
                }

                event.damage += 0.01 * killerHeroData.level

            } else if (killer is Arrow && killer.shooter is Player) {

                killer = killer.shooter as Player

                killerHeroData = users[killerName] ?: return

                if (INSTANCE.duels != null && INSTANCE.duels!!.arenaManager.isInMatch(killer)
                    || checkRaceType(killerHeroData.type)
                ) return

                event.damage += killerHeroData.level / 10

            }

        }

    }

}
