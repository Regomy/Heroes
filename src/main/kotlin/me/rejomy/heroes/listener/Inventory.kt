package me.rejomy.heroes.listener

import me.rejomy.heroes.db
import me.rejomy.heroes.nekro
import me.rejomy.heroes.users
import me.rejomy.heroes.util.EconomyManager
import me.rejomy.heroes.util.emptySlot
import me.rejomy.heroes.util.inventory.Upgrade
import me.rejomy.heroes.util.inventory.shop.Order
import me.rejomy.heroes.util.replaceColor
import me.rejomy.heroes.util.shopOrderInv
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class Inventory: Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if(event.isCancelled) return
        val player = event.whoClicked as Player
        val name = player.name
        val inventory = event.inventory?: return

        val n = inventory.name == "§0▷ Некромант"
        val v = inventory.name == "§0▷ Выбор класса"
        val s = inventory.name == "§0▷ Магазин магии"

        if(!v && !n && !s || event.slot < 0) return
        val item = inventory.getItem(event.slot) ?: return
        val book = item.type == Material.BOOK
        var list = item.itemMeta.lore

        if(n) {
            if(book) {
                var num = list[list.size - 2]
                num = Regex("§[0-9]").replace(num, "")
                num = Regex("[^0-9]").replace(num, "")
                if(EconomyManager.getBalance(player) >= num.toInt()) {
                    EconomyManager.takeMoney(player, num.toInt().toDouble())
                    val levup = users[name]!![1].toInt() + 1
                    db.set(name, users[name]!![0], levup)
                    users[name] = arrayOf(users[name]!![0], levup.toString())
                    player.closeInventory()
                    nekro[name] = Upgrade(player).openInventory()
                    if(users[name]!![0] == "жизнь")
                        player.healthScale = player.healthScale + (users[name]!![1].toInt() * 2)
                    if(users[name]!![0] == "порядок") {
                        val speed = 1 + users[name]!![1].toFloat() / 10
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "speed walk $speed $name")
                    }
                }
            } else if(item.type == Material.BARRIER) {
                db.delete(name)
                users.remove(name)
                nekro.remove(name)
                player.closeInventory()
            } else if(item.type == Material.FLOWER_POT_ITEM) {
                if(users[name]!![0] == "порядок") {
                    player.openInventory(shopOrderInv)
                }
            }
        } else if(v) {
            when(item.type) {
                Material.IRON_BLOCK -> {
                    db.set(name, "жизнь", 1)
                    users[name] = arrayOf("жизнь", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    player.healthScale = player.healthScale + (users[name]!![1].toInt() * 2)
                    player.closeInventory()
                }
                Material.DIAMOND_BLOCK -> {
                    db.set(name, "порядок", 1)
                    users[name] = arrayOf("порядок", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    val speed = 1 + users[name]!![1].toFloat() / 10
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "speed walk $speed $name")
                    player.closeInventory()
                }
                Material.REDSTONE_BLOCK -> {
                    db.set(name, "сила", 1)
                    users[name] = arrayOf("сила", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    player.closeInventory()
                }

                else -> {}
            }
        } else if(s) {
            var num = list[list.size - 2]
            num = Regex("§[0-9]").replace(num, "")
            num = Regex("[^0-9]").replace(num, "")
            if(EconomyManager.getBalance(player) >= num.toInt()) {
                EconomyManager.takeMoney(player, num.toInt().toDouble())
                val citem = ItemStack(item)
                val im = citem.itemMeta
                var array = im.lore
                for(i in 1..2)
                    array.removeAt(array.size - 1)
                array.add("§c ‣ §7Владелец предмета: §e${player.name}  ")
                array.add("§7")
                im.lore = array
                citem.itemMeta = im
                if(emptySlot(player))
                    player.inventory.addItem(citem)
                else
                    player.location.world.dropItemNaturally(player.location, citem)
            }
        }
        event.isCancelled = true
    }

}