package me.rejomy.heroes.listener

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.users
import me.rejomy.heroes.util.checkLore
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent

val cancelDmg = ArrayList<Player>()
class ItemConsumeListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onConsume(event: PlayerItemConsumeEvent) {
        val player = event.player
        val name = player.name
        if(!users.containsKey(name) || users[name]!![0] != "порядок") return
        val item = event.item
        if(item.hasItemMeta() && item.itemMeta.hasLore() && checkLore(item.itemMeta.lore, "Спасения")) {
            if(cancelDmg.contains(player)) {
                player.sendMessage("§9Порядок §8- §7На Вас уже наложены чары спасения.")
                event.isCancelled = true
                return
            }
            cancelDmg.add(player)
            Bukkit.getScheduler().scheduleAsyncDelayedTask(INSTANCE, {
                cancelDmg.remove(player)
            }, ((5 + users[name]!![1].toInt() / 4) * 20).toLong())
        }
    }

}