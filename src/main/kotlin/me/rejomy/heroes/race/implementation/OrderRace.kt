package me.rejomy.heroes.race.implementation

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.race.Race
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import me.rejomy.heroes.util.containsItem
import me.rejomy.heroes.util.isDuel
import me.rejomy.heroes.util.removeOne
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.math.floor

class OrderRace : Race(RaceType.ORDER) {

    private val orderFight = HashMap<Player, Long>()

    override fun handle(event: Event) {

        if (event is EntityDamageByEntityEvent) {

            var killer = event.damager
            val victim = event.entity

            val killerName = killer.name
            val victimName = victim.name

            var killerHeroData = users[killerName]
            val victimHeroData = users[victimName]

            if (victim is Player && victimHeroData != null && checkRaceType(victimHeroData.type)
                && !isDuel(victim)
            ) {

                val level = victimHeroData.level

                if (orderFight.containsKey(victim)) {
                    orderFight[victim] = System.currentTimeMillis()

                    Bukkit.getScheduler().scheduleSyncDelayedTask(INSTANCE, {

                        if (orderFight.containsKey(victim) && System.currentTimeMillis() - orderFight[victim]!! > 5000) {

                            victim.walkSpeed = 0.2F + 0.015F + victimHeroData.level / 400
                            orderFight.remove(victim)

                        }

                    }, 110)

                    if (random.nextInt(150) + 1 < 10 + victimHeroData.level) {
                        victim.addPotionEffect(
                            PotionEffect(
                                PotionEffectType.SPEED, 40 + level * 2,
                                if (level < 11) 0 else 1
                            )
                        )
                    }

                } else {
                    orderFight[victim] = System.currentTimeMillis()
                    victim.walkSpeed = .2F
                }

                var strength = 0

                for (armor in victim.inventory.armorContents) {
                    if (armor != null && armor.hasItemMeta() && armor.itemMeta.hasLore() && checkLore(
                            armor.itemMeta.lore,
                            "локировку действий всех классов"
                        )
                    ) {
                        strength += 1
                    }
                }

                if (strength == 4) {
                    isCancelled = true
                }

            }

            if (killer is Player && killerHeroData != null && checkRaceType(killerHeroData.type)
                && !isDuel(killer)
            ) {

                val itemInKillerHand = killer.itemInHand

                if (itemInKillerHand.type == Material.DIAMOND_SWORD
                    && itemInKillerHand.hasItemMeta()
                    && itemInKillerHand.itemMeta.hasLore()
                ) {

                    if (victim is LivingEntity && checkLore(
                            itemInKillerHand.itemMeta.lore,
                            "Заморозку врага на"
                        )
                    ) {

                        if (killerHeroData.level >= random.nextInt(250)) {
                            victim.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.SLOW,
                                    (2 + killerHeroData.level / 5) * 20,
                                    1 + killerHeroData.level / 4
                                )
                            )
                        }

                    }
                }

            } else if (killer is Arrow && killer.shooter is Player) {
                killer = killer.shooter as Player
                killerHeroData = users[killerName] ?: return

                if (INSTANCE.duels != null && isDuel(killer)
                    || checkRaceType(killerHeroData.type)
                ) return

                //"сила" -> event.damage = event.damage+0.1+0.1 * users[killerName]!![1].toInt()

                val item = killer.itemInHand
                if (item.type == Material.BOW && item.hasItemMeta()
                    && item.itemMeta.hasLore()
                    && checkLore(item.itemMeta.lore, "Скорострельность")
                ) {
                    event.damage = event.damage + 1 + killerHeroData.level / 10
                }

            }

        } else if (event is PlayerInteractEvent) {

            if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return

            val player = event.player
            val name = player.name

            val heroData = users[name] ?: return

            if (!checkRaceType(heroData.type)) return

            val itemInHand = player.itemInHand

            if (itemInHand.hasItemMeta() && itemInHand.itemMeta.hasLore()
                && itemInHand.type == Material.BOW
                && checkLore(itemInHand.itemMeta.lore, "Скорострельность")
                && containsItem(player, Material.ARROW)
            ) {

                if ((itemInHand.durability + 3).toShort() >= 384) {
                    player.inventory.setItem(player.inventory.heldItemSlot, null)
                    event.isCancelled = true
                    return
                }

                removeOne(player.inventory, ItemStack(Material.ARROW))
                val arrow = player.launchProjectile(Arrow::class.java)
                arrow.velocity.multiply(2.0 + heroData.level / 20)
                arrow.knockbackStrength = arrow.knockbackStrength + 2
                itemInHand.durability =
                    (itemInHand.durability + (4 - floor((heroData.level / 10).toDouble()).toInt())).toShort()
                player.itemInHand = itemInHand
                event.isCancelled = true
            }

        } else if(event is PlayerTeleportEvent) {

            val player = event.player

            val userData = users[player.name]?: return

            if(event.to.world == event.from.world || !checkRaceType(userData.type)) return

            player.walkSpeed = 0.2F + 0.015F + userData.level / 400

        }

    }

}