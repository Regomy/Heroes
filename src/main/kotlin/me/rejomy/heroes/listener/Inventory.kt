package me.rejomy.heroes.listener

import me.rejomy.heroes.db
import me.rejomy.heroes.nekro
import me.rejomy.heroes.top
import me.rejomy.heroes.users
import me.rejomy.heroes.util.*
import me.rejomy.heroes.util.inventory.Upgrade
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import kotlin.math.pow

class Inventory : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onClick(event: InventoryClickEvent) {
        if (event.isCancelled) return
        val player = event.whoClicked as Player
        val name = player.name
        val inventory = event.inventory ?: return

        if (inventory.type == InventoryType.ANVIL) {
            if (event.slot < 0 || event.slot != 2) return
            val item = inventory.getItem(event.slot) ?: return
            if (!item.hasItemMeta() || !item.itemMeta.hasLore() || !checkLore(
                    item.itemMeta.lore,
                    "уровня вашего героя"
                )
            ) return
            if (item.type == Material.BOW) {
                player.sendMessage("Вы не можете совмещать супер предметы с другими!")
                player.closeInventory()
                event.isCancelled = true
                return
            }

            val itemMeta = item.itemMeta
            val checklore = itemMeta.lore
            val newLore = itemMeta.lore

            for (i in checklore)
                if (i.contains("›"))
                    newLore.remove(i)

            for ((key, value) in item.enchantments)
                newLore.add(newLore.size - 1, " §6› §7${key.name} §6$value")

            itemMeta.lore = newLore
            item.itemMeta = itemMeta

        }

        val mainMenu = inventory.name == "§0▷ Некромант"
        val classSelector = inventory.name == "§0▷ Выбор класса"
        val shopItems = inventory.name == "§0▷ Магазин магии"
        val clearHero = inventory.name == "§0▷ Сброс характеристик героя:"
        val tops = inventory.name == "§0▷ Топ по прокачке"
        if (!classSelector && !mainMenu && !shopItems && !clearHero && !tops || event.slot < 0) return

        val item = inventory.getItem(event.slot) ?: return
        val book = item.type == Material.BOOK
        val list = item.itemMeta.lore

        if (mainMenu) {

            if (book) {
                var num = list[list.size - 2]
                num = Regex("§[0-9]").replace(num, "")
                num = Regex("[^0-9]").replace(num, "")
                if (EconomyManager.getBalance(player) >= num.toInt()) {
                    EconomyManager.takeMoney(player, num.toInt().toDouble())
                    val levup = users[name]!![1].toInt() + 1
                    db.set(name, users[name]!![0], levup)
                    users[name] = arrayOf(users[name]!![0], levup.toString())
                    nekro[name] = Upgrade(player).openInventory()
                    player.openInventory(nekro[name])
                    if (users[name]!![0] == "жизнь")
                        player.healthScale = 22.0 + users[name]!![1].toInt()
                    if (users[name]!![0] == "порядок") {
                        player.walkSpeed = 0.2F + 0.02F + users[name]!![1].toFloat() / 200
                    }
                    player.sendMessage(
                        "&c ‣ &fВаш уровень был увеличен &7${users[name]!![1]}&f/&720&f!".replace(
                            "&",
                            "§"
                        )
                    )
                } else {
                    player.sendMessage(
                        "&c ‣ &fУ вас недостаточно средств &7${
                            EconomyManager.getBalance(player).toInt()
                        }&f/&7$num&f.".replace("&", "§")
                    )
                }
            } else if (item.type == Material.BARRIER)

                player.openInventory(ClearHeroConfirm)
            else if (item.type == Material.FLOWER_POT_ITEM)

                when (users[name]!![0]) {
                    "порядок" -> player.openInventory(shopOrderInv)
                    "сила" -> player.openInventory(shopPowerInv)
                    "жизнь" -> player.openInventory(shopLifeInv)
                    "смерть" -> player.openInventory(shopDeathInv)
                }
            else if (item.type == Material.GOLD_INGOT)
                player.openInventory(top)

        } else if (classSelector) {
            when (item.type) {
                Material.IRON_BLOCK -> {
                    db.set(name, "жизнь", 1)
                    users[name] = arrayOf("жизнь", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    player.healthScale = 24.0
                    player.openInventory(nekro[name])
                }

                Material.DIAMOND_BLOCK -> {
                    db.set(name, "порядок", 1)
                    users[name] = arrayOf("порядок", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    player.walkSpeed = 0.2F + 0.02F
                    player.openInventory(nekro[name])
                }

                Material.REDSTONE_BLOCK -> {
                    db.set(name, "сила", 1)
                    users[name] = arrayOf("сила", "0")
                    nekro[name] = Upgrade(player).openInventory()
                    player.openInventory(nekro[name])
                }

                Material.COAL_BLOCK -> {
                    db.set(name, "смерть", 1)
                    users[name] = arrayOf("смерть", "0")
                    player.healthScale = 12.0
                    nekro[name] = Upgrade(player).openInventory()
                    player.openInventory(nekro[name])
                }

                else -> {}
            }
        } else if (shopItems && item.itemMeta.hasLore()) {
            if (checkLore(list, "Цена предмета")) {
                var num = list[list.size - 2]
                num = Regex("§[0-9]").replace(num, "")
                num = Regex("[^0-9]").replace(num, "")
                if (EconomyManager.getBalance(player) >= num.toInt()) {
                    EconomyManager.takeMoney(player, num.toInt().toDouble())
                    val citem = ItemStack(item)
                    val im = citem.itemMeta
                    val array = im.lore
                    for (i in 1..2)
                        array.removeAt(array.size - 1)
                    //array.add("§7")
                    im.lore = array
                    citem.itemMeta = im
                    if (emptySlot(player))
                        player.inventory.addItem(citem)
                    else
                        player.location.world.dropItemNaturally(player.location, citem)
                } else
                    player.sendMessage(
                        "&c ‣ &fУ вас недостаточно средств &7${
                            EconomyManager.getBalance(player).toInt()
                        }&f/&7$num&f.".replace("&", "§")
                    )
            } else if (checkLore(list, "Вернуться в меню"))
                player.openInventory(nekro[player.name])
        } else if (clearHero && item.hasItemMeta() && item.itemMeta.hasLore()) {
            if (checkLore(item.itemMeta.lore, "Сбросить текущий класс")) {
                val level = getLevel(name).toDouble()
                if (level > 0) EconomyManager.giveMoney(player, 2000 * 1.5.pow(level).toInt().toDouble())
                if (users[name]!![0] == "порядок")
                    player.walkSpeed = 0.2F
                else if (users[name]!![0] == "жизнь" || users[name]!![0] == "смерть")
                    player.healthScale = 20.0
                db.delete(name)
                users.remove(name)
                nekro.remove(name)
                player.openInventory(newplayersInv)
            } else if (checkLore(item.itemMeta.lore, "Вернутся в меню"))
                player.openInventory(nekro[player.name])
        } else if (tops)
            if (item.hasItemMeta() && item.itemMeta.hasLore() && checkLore(list, "Вернуться в меню"))
                player.openInventory(nekro[player.name])

        event.isCancelled = true
    }

}