package me.rejomy.heroes.listener

import me.rejomy.heroes.*
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.util.*
import me.rejomy.heroes.util.inventory.implement.UpgradeHeroInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class InventoryListener : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onClick(event: InventoryClickEvent) {

        val player = event.whoClicked as Player
        val name = player.name

        val inventory = event.inventory

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
            val lore = itemMeta.lore
            val newLore = itemMeta.lore

            for (i in lore)
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

        if (!mainMenu && !classSelector && !shopItems && !clearHero && !tops) return

        var playerData = users[name]

        val item = inventory.getItem(event.slot) ?: return

        val lore = item.itemMeta?.lore

        if (classSelector) {

            when (item.type) {

                Material.IRON_BLOCK -> {
                    db.set(name, "жизнь", 1)
                    users[name] = PlayerData(RaceType.LIFE, 1)
                }

                Material.DIAMOND_BLOCK -> {
                    db.set(name, "порядок", 1)
                    users[name] = PlayerData(RaceType.ORDER, 1)
                }

                Material.REDSTONE_BLOCK -> {
                    db.set(name, "сила", 1)
                    users[name] = PlayerData(RaceType.POWER, 1)
                }

                Material.COAL_BLOCK -> {
                    db.set(name, "смерть", 1)
                    users[name] = PlayerData(RaceType.DEATH, 1)
                }

                else -> {}
            }

            if (users.containsKey(player.name)) {

                nekro[name] = UpgradeHeroInventory(name).openInventory()

                player.openInventory(nekro[name])

                HeroUtil.setAbilities(player)
            }

        } else if(lore != null && playerData != null) {

            if (shopItems) {
                if (checkLore(lore, "Цена предмета")) {
                    var num = lore[lore.size - 2]
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
                        if (hasEmptySlot(player))
                            player.inventory.addItem(citem)
                        else
                            player.location.world.dropItemNaturally(player.location, citem)
                    } else
                        player.sendMessage(
                            "&c ‣ &fУ вас недостаточно средств &7${
                                EconomyManager.getBalance(player).toInt()
                            }&f/&7$num&f.".replace("&", "§")
                        )
                } else if (checkLore(lore, "Вернуться в меню"))
                    player.openInventory(nekro[player.name])
            } else if (clearHero) {
                if (checkLore(item.itemMeta.lore, "Сбросить текущий класс")) {

                    if (playerData.level > 1) EconomyManager.giveMoney(player, getPriceSumBooks(playerData.level) / 2.0)

                    HeroUtil.removePlayerData(name)

                    player.openInventory(classSelectorInv)

                } else if (checkLore(item.itemMeta.lore, "Вернутся в меню")) {
                    player.openInventory(nekro[player.name])
                }
            } else if(mainMenu) {

                when(item.type) {

                    Material.BOOK -> {

                        var num = lore!![lore.size - 2]

                        num = Regex("§[0-9]").replace(num, "")
                        num = Regex("[^0-9]").replace(num, "")
                        if (EconomyManager.getBalance(player) >= num.toInt()) {

                            EconomyManager.takeMoney(player, num.toInt().toDouble())

                            playerData = PlayerData(playerData.type, playerData.level + 1)

                            db.set(name, playerData.type.russianType, player.level)

                            users[name] = playerData

                            nekro[name] = UpgradeHeroInventory(name).openInventory()

                            player.openInventory(nekro[name])

                            HeroUtil.setAbilities(player)

                            player.sendMessage(
                                "&c ‣ &fВаш уровень был увеличен &7${playerData.level}&f/&720&f!".replace(
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

                    }

                    Material.BARRIER -> player.openInventory(RemoveHeroConfirmInventory)
                    Material.FLOWER_POT_ITEM -> player.openInventory(playerData.type.shopInventory)
                    Material.GOLD_INGOT -> player.openInventory(topInventory)

                    else -> {}
                }

            }

            if (checkLore(lore, "Вернуться в меню")) {
                player.openInventory(nekro[player.name])
            }

        }

        event.isCancelled = true
    }

}