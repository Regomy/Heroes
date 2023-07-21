package me.rejomy.heroes.listener

import me.rejomy.heroes.users
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.streams.toList

val items = HashMap<String, ArrayList<ItemStack>>()
private val random = Random()

class Death : Listener {

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity.player
        val name = player.name
        if (!users.containsKey(name) || users[name]!![0] != "смерть" || random.nextInt(100) < 20 + users[name]!![1].toInt()) return
        val drops = ArrayList(event.drops.stream().filter { it != null }.toList())


        // If player has only one item then keep it
        if (drops.size == 1) {
            items[name] = Collections.nCopies(1, drops[0]) as ArrayList<ItemStack>
            event.drops.remove(drops[0])
            player.inventory.remove(drops[0])
            return
        }
        // If player has only two items then keep one of them
        else if (drops.size == 2) {
            val randomIndex = random.nextInt(2)
            items[name] = Collections.nCopies(1, drops[randomIndex]) as ArrayList<ItemStack>
            event.drops.remove(drops[randomIndex])
            player.inventory.remove(drops[randomIndex])
            return
        }


        // If player has multiple items then keep 30 + level% of them
        val chance = 30 + users[name]!![1].toInt()
        val size = drops.size * chance / 100

        val newItems = ArrayList<ItemStack>(drops.size)
        for (amount in 1..size) {
            val randomIndex = random.nextInt(drops.size)
            newItems.add(drops[randomIndex])
            drops.removeAt(randomIndex)
        }
        event.drops.removeAll(newItems)
        player.inventory.forEach {
            if(newItems.contains(it))
                it.type = Material.AIR
        }
        items[name] = newItems
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val name = player.name
        if (!items.containsKey(name)) {
            return
        }

        // Set player items on respawn
        for(i in 0 until items[name]!!.size) {
            player.inventory.setItem(i, items[name]!![i])
        }
        items.remove(name)
    }

}