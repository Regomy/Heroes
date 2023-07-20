package me.rejomy.heroes.listener

import me.rejomy.heroes.duels
import me.rejomy.heroes.listener.order.checkOrderSpeedFight
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import me.rejomy.heroes.util.getLevel
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.floor

class Fight : Listener {

    var lastEks = HashMap<Player, Long>()

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onFight(event: EntityDamageByEntityEvent) {
        if (event.entity is Player) {
            if (cancelDmg.contains(event.entity)) {
                event.isCancelled = true
                return
            }
        }
        if (event.damager is Arrow && (event.damager as Arrow).shooter is Player) {
            val player = (event.damager as Arrow).shooter as Player

            if (duels != null && duels!!.arenaManager.isInMatch(player)) return

            if (cancelDmg.contains(player)) {
                event.isCancelled = true
                return
            }

            if (users.containsKey(player.name)) {
                val group = users[player.name]!![0]

                if (group == "сила")
                    event.damage = event.damage + 0.5 + 0.1 * users[player.name]!![1].toInt()
                else if (group == "порядок") {

                    val item = player.itemInHand
                    if (item.type == Material.BOW && item.hasItemMeta() && item.itemMeta.hasLore() && checkLore(
                            item.itemMeta.lore,
                            "Скорострельность"
                        )
                    ) {
                        event.damage = event.damage + 1 * users[player.name]!![1].toInt()
                    }

                }
            }
        }

        if (event.damager !is Player || event.entity !is Player) return

        val killer = event.damager as Player
        val kname = killer.name
        val player = event.entity as Player
        val pname = player.name
        if (duels != null && (duels!!.arenaManager.isInMatch(player) || duels!!.arenaManager.isInMatch(killer))) return

        if (cancelDmg.contains(killer)) {
            event.isCancelled = true
            return
        }

        if (users.containsKey(player.name)) {
            if (users[player.name]!![0] == "порядок") {
                checkOrderSpeedFight(player)
                var strength = 0
                for (armor in player.inventory.armorContents) {
                    if (armor != null && armor.hasItemMeta() && armor.itemMeta.hasLore() && checkLore(
                            armor.itemMeta.lore,
                            "локировку действий всех классов"
                        )
                    ) {
                        strength += 1
                        if (strength == 4)
                            return
                    }
                }
            } else if (users[player.name]!![0] == "смерть") {
                val level = getLevel(pname)
                for (armor in player.inventory.armorContents) {
                    if (armor.hasItemMeta() && armor.itemMeta.hasLore()) {
                        if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить слабость"
                            ) && Random().nextInt(500) < level
                        )
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.WEAKNESS,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить слепоту"
                            ) && Random().nextInt(500) < level
                        )
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.BLINDNESS,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить отравление"
                            ) && Random().nextInt(500) < level
                        )
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.POISON,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                        else if (checkLore(
                                armor.itemMeta.lore,
                                "Шанс уровень% наложить медлительность"
                            ) && Random().nextInt(500) < level
                        )
                            killer.addPotionEffect(
                                PotionEffect(
                                    PotionEffectType.SLOW,
                                    40 + level * 2,
                                    1 + level / 10
                                )
                            )
                    }
                }
                event.damage = event.damage * 1.6
                if (Random().nextInt(100) <= 80 + level)
                    return
            } else if (users[pname]!![0] == "жизнь") {
                if (Random().nextInt(100) < getLevel(pname))
                    player.addPotionEffect(
                        PotionEffect(
                            PotionEffectType.DAMAGE_RESISTANCE,
                            20 + getLevel(pname) * 2,
                            1 + getLevel(pname) / 10
                        )
                    )
            }
        }

        var ekskalibur = -1
        var order_sword = -1
        var balda_sword_strength = -1
        var kun_sword_death = -1
        var kosa_sword_death = -1
        var dubina_sword_power = -1

        if (users.containsKey(event.damager.name)) {
            val level = users[kname]!![1].toInt()

            if (killer.itemInHand?.type == Material.DIAMOND_SWORD) {
                val item = killer.itemInHand
                if (item.itemMeta != null && item.itemMeta.hasLore()) {
                    if (users[killer.name]!![0] == "сила") {
                        if (checkLore(item.itemMeta.lore, "Экскалибур"))
                            ekskalibur = level
                        else if (checkLore(item.itemMeta.lore, "Балда"))
                            balda_sword_strength = level
                        else if (checkLore(item.itemMeta.lore, "Дубина"))
                            dubina_sword_power = level
                    } else if (checkLore(
                            item.itemMeta.lore,
                            "Восстановление хп, в размере"
                        ) && users[killer.name]!![0] == "жизнь"
                    ) {
                        val hpboost = (event.finalDamage / 40) * level
                        killer.health =
                            if (killer.health + hpboost > killer.maxHealth) killer.maxHealth else killer.health + hpboost
                    } else if (checkLore(
                            item.itemMeta.lore,
                            "Заморозку врага на"
                        ) && users[killer.name]!![0] == "порядок"
                    ) {
                        order_sword = level
                    } else if (users[killer.name]!![0] == "смерть") {
                        if (checkLore(item.itemMeta.lore, "Кунь"))
                            kun_sword_death = level
                        else if (checkLore(item.itemMeta.lore, "Коса"))
                            kosa_sword_death = level
                    }
                }
            }

            if (users[kname]!![0] == "сила")
                event.damage = event.damage + 0.5 + 0.1 * level
        }

        if (event.entity is Player) {
            val player = event.entity as Player
            val name = player.name

            var dmg = false
            for ((content, armor) in player.inventory.armorContents.withIndex()) {
                if (armor != null && armor.hasItemMeta() && armor.itemMeta.hasLore()) {
                    if (users.containsKey(event.entity.name) && users[player.name]!![0] == "сила") {
                        if (checkLore(armor.itemMeta.lore, "Меч соперника ломается быстрее")) {

                            if (!dmg && Random().nextInt(100) <= getLevel(player.name)) {
                                dmg = true
                                killer.damage(event.finalDamage)
                            }

                            if (killer.itemInHand != null && killer.itemInHand.type.name.contains(
                                    "SWORD"
                                )
                            ) {
                                val item = killer.itemInHand
                                if ((item.durability + 2).toShort() >= item.type.maxDurability) {
                                    player.itemInHand.type = Material.AIR
                                } else {
                                    if (getLevel(player.name) > 0)
                                        item.durability =
                                            (item.durability + 1 + floor((getLevel(player.name) / 10).toDouble())).toInt()
                                                .toShort()
                                    killer.itemInHand = item
                                }
                            }
                        }
                    } else if (users.containsKey(event.entity.name) && users[player.name]!![0] == "жизнь") {
                        if (event.finalDamage >= player.health && checkLore(
                                armor.itemMeta.lore,
                                "Действует как тотем бессмертия"
                            )
                        ) {
                            val newContent = player.inventory.armorContents
                            newContent[content] = null
                            player.inventory.armorContents = newContent
                            player.playSound(player.location, "mob.guardian.curse", 2.0f, 1.0f)
                            event.isCancelled = true
                            player.health = player.maxHealth
                            break
                        }
                    }
                }
            }

            if (ekskalibur > 0) {
                if (!lastEks.containsKey(player) || lastEks.containsKey(killer) && System.currentTimeMillis() - lastEks[killer]!! > 10000)
                    if ((lastEks.containsKey(killer) && System.currentTimeMillis() - lastEks[killer]!! > 10000 || !lastEks.containsKey(
                            killer
                        ))
                        && player.inventory.armorContents.isNotEmpty() && Random().nextInt(150).toDouble() <= ekskalibur
                    ) {

                        val arm = player.inventory.armorContents
                        val chance2 = Random().nextInt(arm.size)
                        if (arm[chance2] == null || arm[chance2].type == Material.AIR) return

                        val ekspa = killer.itemInHand
                        val itemDur = (arm[chance2].type.maxDurability - arm[chance2].durability)

                        if (ekspa.durability + itemDur >= Material.DIAMOND_SWORD.maxDurability) {

                            killer.playSound(player.location, Sound.ANVIL_BREAK, 2f, 2f)
                            killer.inventory.setItem(killer.inventory.heldItemSlot, null)
                            event.isCancelled = true
                            if (arm[chance2].durability + (Material.DIAMOND_SWORD.maxDurability - ekspa.durability) < arm[chance2].type.maxDurability)
                                arm[chance2].durability =
                                    (arm[chance2].durability + (Material.DIAMOND_SWORD.maxDurability - ekspa.durability)).toShort()
                            else arm[chance2] = null
                        } else {
                            ekspa.durability = (ekspa.durability + itemDur).toShort()
                            player.playSound(player.location, Sound.ANVIL_BREAK, 2f, 2f)
                            killer.itemInHand = ekspa
                            var itemStack = ItemStack(Material.AIR)
                            arm[chance2] = itemStack
                        }
                        player.inventory.armorContents = arm
                        lastEks[killer] = System.currentTimeMillis()
                    }
            } else if (order_sword > 0) {
                if (order_sword >= Random().nextInt(250))
                    player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, (2 + getLevel(kname) / 5) * 20, 1 + getLevel(kname) / 4))
            } else if (balda_sword_strength > 0) {
                if (Random().nextInt(100) < getLevel(killer.name)) {
                    var multi = 2
                    if (killer.itemInHand.enchantments.containsKey(Enchantment.KNOCKBACK))
                        multi += killer.itemInHand.enchantments[Enchantment.KNOCKBACK]!!
                    val vector = killer.location.direction.multiply(multi + getLevel(kname) / 10)
                    player.velocity = vector
                }
            } else if (kun_sword_death > 0) {
                if ((!eatPlayers.containsKey(player) || eatPlayers.containsKey(player) && System.currentTimeMillis() - eatPlayers[player]!! >= 1600)
                    && Random().nextInt(400) < if (getLevel(kname) > 10) 10 else getLevel(kname)
                ) {
                    for (inv in player.inventory) {
                        if (inv == null) continue
                        if (inv.type == Material.GOLDEN_APPLE) {
                            var isFull = true
                            for (kinv in killer.inventory) {
                                if (kinv == null)
                                    isFull = false
                            }
                            if (!isFull) {
                                val tinv = inv.clone()
                                tinv.amount = 1
                                killer.inventory.addItem(tinv)
                                killer.itemInHand.durability = (killer.itemInHand.durability + 150).toShort()

                                if (inv.amount == 1)
                                    player.inventory.remove(inv)
                                else
                                    inv.amount = inv.amount - 1

                                break
                            }
                        }
                    }
                }
            } else if (kosa_sword_death > 0) {
                for (entity in player.world.getNearbyEntities(player.location, 2.0, 1.0, 2.0)) {
                    if (entity is LivingEntity) {
                        if (entity == killer || entity == player) continue
                        entity.velocity = killer.location.direction.multiply(1)
                        entity.damage(event.finalDamage)
                        killer.itemInHand.durability = (player.itemInHand.durability + 6).toShort()
                    }
                }
            } else if (dubina_sword_power > 0) {
                if (Random().nextInt(50) < getLevel(kname)) {
                    event.damage *= 2
                    player.itemInHand.durability = (player.itemInHand.durability + 2).toShort()
                }
            }

            if (users.containsKey(name) && users[name]!![0] == "жизнь")
                event.damage = (20 * event.damage) / (20 + getLevel(name))
        }
    }
}