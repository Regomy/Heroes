package me.rejomy.heroes.util

import me.rejomy.heroes.PlayerData
import me.rejomy.heroes.db
import me.rejomy.heroes.nekro
import me.rejomy.heroes.race.RaceType
import me.rejomy.heroes.users
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object HeroUtil {

    fun isHeroItem(item: ItemStack): Boolean {

        if (!item.hasItemMeta() || !item.itemMeta.hasLore()) return false

        val lore = item.itemMeta.lore

        for (line in lore) {

            for (race in RaceType.values()) {

                if (line.contains(race.russianType, true)) {

                    return true

                }

            }

        }

        return false

    }

    fun loadUsersIntoMap() {
        val resultSet = db.executeQuery("select * from users")

        while (resultSet.next()) {

            users[resultSet.getString(1)] = PlayerData(
                RaceType.values().first {
                    it.russianType == resultSet.getString(2)
                },
                resultSet.getString(3).toInt()
            )

        }

    }

    fun removePlayerData(playerName: String) {

        db.remove(playerName)
        users.remove(playerName)
        nekro.remove(playerName)

        val player = Bukkit.getPlayer(playerName) ?: return

        clearAbilities(player)

    }

    fun clearAbilities(player: Player) {
        if (!users.containsKey(player.name)) return

        player.walkSpeed = 0.2F
        player.healthScale = 20.0
        player.health = player.maxHealth

    }

    fun setAbilities(player: Player) {
        val heroData = users[player.name] ?: return

        when (heroData.type) {
            RaceType.DEATH -> player.healthScale = 12.0
            RaceType.LIFE -> player.healthScale = 22.0 + heroData.level
            RaceType.ORDER -> player.walkSpeed = 0.2F + 0.015F + heroData.level / 400
            else -> {}
        }

    }

}