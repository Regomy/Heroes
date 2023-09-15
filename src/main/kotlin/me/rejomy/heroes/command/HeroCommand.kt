package me.rejomy.heroes.command

import me.rejomy.heroes.*
import me.rejomy.heroes.util.HeroUtil
import me.rejomy.heroes.util.classSelectorInv
import me.rejomy.heroes.util.inventory.implement.UpgradeHeroInventory
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HeroCommand : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val name = sender.name

        if(args.isEmpty() && sender is Player) {

            if(users.containsKey(name)) {

                if(!nekro.containsKey(name))
                    nekro[name] = UpgradeHeroInventory(name).openInventory()

                sender.openInventory(nekro[name])
            } else {
                sender.openInventory(classSelectorInv)
            }

        } else if (checkPermission(sender, "admin")) {

            if(args.isEmpty()) {
                sender.sendMessage("/heroes reload | /heroes info name | /heroes remove name | /heroes set name size")
            }

            when (args[0]) {
                "help" -> sender.sendMessage("/reload | /info name | /remove name | /set name size")
                "reload", "релоад" -> INSTANCE.reloadConfig()
                "info" -> if(hasPlayer(args[1])) sender.sendMessage("${args[1]} ${users[args[1]]!!}")
                "remove" -> {
                    if (hasPlayer(args[1])) {

                        HeroUtil.removePlayerData(args[1])

                    }
                }
                "set" -> if(hasPlayer(args[1])) {

                    if(args.size < 3) return true

                    val heroData = users[args[1]]!!

                    db.set(args[1], heroData.type.russianType, args[2].toInt())

                    users[args[1]] = PlayerData(heroData.type, args[2].toInt())

                }
            }
        }

        return false
    }

    fun checkPermission(sender: CommandSender, permission: String): Boolean { return sender.hasPermission(permission) }

    fun hasPlayer(player: String): Boolean { return player.isNotEmpty() && users.containsKey(player) }

}