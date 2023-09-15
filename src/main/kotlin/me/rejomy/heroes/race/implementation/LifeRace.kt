package me.rejomy.heroes.race.implementation

import me.rejomy.heroes.race.Race
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class LifeRace : Race(RaceType.LIFE) {

    override fun handle(event: Event) {

        if (event is EntityDamageEvent) {

            val entity = event.entity

            val playerData = users[entity.name] ?: return

            if (!checkRaceType(playerData.type)) return

            event.damage = (20 * event.damage) / (20 + playerData.level)

        } else if (event is EntityDamageByEntityEvent) {

            val killer = event.damager
            val victim = event.entity

            val killerName = killer.name
            val victimName = victim.name

            val killerHeroData = users[killerName]
            val victimHeroData = users[victimName]

            if (victim is Player && victimHeroData != null && checkRaceType(victimHeroData.type)) {

                val level = victimHeroData.level

                if (random.nextInt(100) < level) {
                    victim.addPotionEffect(
                        PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE,
                            20 + level * 2,
                            1 + level / 10
                        )
                    )
                }

                for ((index, armor) in victim.inventory.armorContents.withIndex()) {
                    if (event.finalDamage >= victim.health && checkLore(
                            armor.itemMeta.lore,
                            "Действует как тотем бессмертия"
                        )
                    ) {
                        val newContent = victim.inventory.armorContents
                        newContent[index] = null
                        victim.inventory.armorContents = newContent
                        victim.playSound(victim.location, "mob.guardian.curse", 2.0f, 1.0f)
                        event.isCancelled = true
                        victim.health = victim.maxHealth
                        break
                    }
                }

            }

            if (killer is Player && killerHeroData != null && checkRaceType(killerHeroData.type)) {
                val itemInKillerHand = killer.itemInHand

                if (itemInKillerHand.type == Material.DIAMOND_SWORD
                    && itemInKillerHand.hasItemMeta()
                    && itemInKillerHand.itemMeta.hasLore()
                ) {
                    if (checkLore(itemInKillerHand.itemMeta.lore, "Восстановление хп, в размере")) {
                        val hpboost = (event.finalDamage / 40) * killerHeroData.level

                        killer.health =
                            if (killer.health + hpboost > killer.maxHealth) killer.maxHealth else killer.health + hpboost

                    }

                }

            }

        }

    }

}
