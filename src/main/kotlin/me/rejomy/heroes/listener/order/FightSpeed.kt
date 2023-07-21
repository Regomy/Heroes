package me.rejomy.heroes.listener.order

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.users
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.Random

val orderFight = HashMap<Player, Long>()

fun checkOrderSpeedFight(player: Player) {
    if(orderFight.containsKey(player)) {
        val name = player.name
        orderFight[player] = System.currentTimeMillis()
        Bukkit.getScheduler().scheduleSyncDelayedTask(INSTANCE, {
            if(orderFight.containsKey(player) && System.currentTimeMillis() - orderFight[player]!! > 5000) {
                player.walkSpeed = 0.2F + 0.015F + users[name]!![1].toFloat() / 400
                orderFight.remove(player)
            }
        }, 110)

        if(Random().nextInt(150) + 1 < 10 + users[name]!![1].toInt()) {
            val level = users[name]!![1].toInt()
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 40 + level * 2, if(level < 11) 0 else 1))
        }

    } else {
        orderFight[player] = System.currentTimeMillis()
        player.walkSpeed = .2F
    }
}