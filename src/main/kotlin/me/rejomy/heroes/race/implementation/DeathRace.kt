package me.rejomy.heroes.race.implementation

import me.rejomy.heroes.PlayerData
import me.rejomy.heroes.race.Race
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.streams.toList

class DeathRace : Race(RaceType.DEATH) {

    val items = HashMap<String, List<ItemStack>>()

    override fun handle(event: Event) {

        if (event is PlayerDeathEvent) {

            val player = event.entity.player
            val name = player.name
            val random = Random()

            val heroData = users[name] ?: return

            if (random.nextInt(100) >= 20 + heroData.level) {
                return
            }

            // Check if it's possible to save inventory
            saveInventoryOnDeath(name, random, event, heroData)

        } else if (event is PlayerRespawnEvent) {

            val player = event.player
            val name = player.name

            if (!items.containsKey(name)) {
                return
            }

            // Set player items on respawn
            for (i in 0 until items[name]!!.size) {
                player.inventory.setItem(i, items[name]!![i])
            }

            items.remove(name)

        } else if (event is EntityDamageByEntityEvent) {

            val killer = event.damager
            val victim = event.entity

            val killerName = killer.name
            val victimName = victim.name

            val killerHeroData = users[killerName]
            val victimHeroData = users[victimName]

            if (victim is Player && victimHeroData != null && checkRaceType(victimHeroData.type)) {

                val level = victimHeroData.level

                if (killer is LivingEntity) {

                    for (armor in victim.inventory.armorContents) {

                        if (!armor.hasItemMeta() || !armor.itemMeta.hasLore()) continue

                        if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить слабость"
                            ) && random.nextInt(500) < level
                        ) {
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.WEAKNESS,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        } else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить слепоту"
                            ) && random.nextInt(500) < level
                        ) {
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.BLINDNESS,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        } else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить отравление"
                            ) && random.nextInt(500) < level
                        ) {
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.POISON,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        } else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить медлительность"
                            ) && random.nextInt(500) < level
                        ) {
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.SLOW,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        }
                    }
                }

                event.damage *= 1.6

                if (random.nextInt(100) <= 80 + level) {
                    isCancelled = true
                }

            }

            if (killer is Player && killerHeroData != null && checkRaceType(killerHeroData.type)) {
                val itemInKillerHand = killer.itemInHand

                if (checkLore(itemInKillerHand.itemMeta.lore, "Коса")) {

                    for (entity in victim.world.getNearbyEntities(victim.location, 2.0, 1.0, 2.0)) {

                        if (entity is LivingEntity) {

                            if (entity == killer || entity == victim) continue

                            entity.velocity = killer.location.direction.multiply(1)

                            entity.damage(event.damage)

                            killer.itemInHand.durability =
                                (itemInKillerHand.durability + (event.damage / 2).toInt()).toShort()

                        }

                    }

                }

            }

        }

    }

    private fun saveInventoryOnDeath(name: String, random: Random, event: PlayerDeathEvent, heroData: PlayerData) {

        val drops = ArrayList(event.drops.stream().filter { it != null }.toList())

        // If player has only one item then keep it
        if (drops.size == 1) {
            items[name] = listOf(drops[0])
            event.drops.remove(drops[0])
            return
        }

        // If player has only two items then keep one of them
        else if (drops.size == 2) {
            val randomIndex = random.nextInt(2)
            items[name] = listOf(drops[randomIndex])
            event.drops.remove(drops[randomIndex])
            return
        }


        // If player has multiple items then keep 30 + level% of them
        val percentage = 30 + heroData.level
        val size = drops.size * percentage / 100

        val newItems = ArrayList<ItemStack>(drops.size)

        repeat(size) {
            val randomIndex = random.nextInt(drops.size)
            newItems.add(drops[randomIndex])

            event.drops.remove(drops[randomIndex])
            drops.removeAt(randomIndex)
        }

        items[name] = newItems

    }

}
