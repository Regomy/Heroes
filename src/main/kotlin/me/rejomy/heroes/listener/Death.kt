package me.rejomy.heroes.listener

import me.rejomy.heroes.users
import me.rejomy.heroes.util.newplayersInv
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import java.util.Random

val items = HashMap<String, ArrayList<ItemStack>>()

class Death : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity.player
        val name = player.name
        if (!users.containsKey(name) || users[name]!![0] != "смерть" || Random().nextInt(100) < 20 + users[name]!![1].toInt()) return
        val drops = ArrayList<ItemStack>()
        for(drop in event.drops) {
            if(drop != null)
                drops.add(drop)
        }

        if (drops.size == 1) {
            val item = ArrayList<ItemStack>()
            item.add(drops[0])
            items[name] = item
            event.drops.remove(drops[0])
            player.inventory.remove(drops[0])
        } else if (drops.size == 2) {
            val drop = Random().nextInt(2)
            val item = ArrayList<ItemStack>()
            item.add(drops[drop])
            items[name] = item
            event.drops.remove(drops[drop])
            player.inventory.remove(drops[drop])
        } else {
            val chance = 30 + users[name]!![1].toInt()
            val size = ((drops.size * chance) / 100).toInt()

            val item = ArrayList<ItemStack>()
            for (amount in 1..size) {
                val random = Random().nextInt(drops.size)
                item.add(drops[random])
                event.drops.remove(drops[random])
                player.inventory.remove(drops[random])
            }
            items[name] = item
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val name = player.name
        if(items.containsKey(player.name)) {
            for(i in 0 until items[name]!!.size)
                player.inventory.setItem(i, items[name]!![i])
            items.remove(name)
        }
    }

}