package me.rejomy.heroes.command

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.nekro
import me.rejomy.heroes.users
import me.rejomy.heroes.util.newplayersInv
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Hero : CommandExecutor {

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {

        if(args!!.isEmpty()) {
            // open inventory
            var player = sender as Player
            if(users.containsKey(sender.name))
                player.openInventory(nekro[player.name])
            else
                player.openInventory(newplayersInv)


        } else if (checkPermission(sender!!, "admin")) {
            when (args[0]) {
                "reload", "релоад" -> {
                    INSTANCE.saveDefaultConfig()
                }
                "info" -> {
                    if(args[1].isNotEmpty()) {
                        sender.sendMessage("Укажите ник игрока")
                        return true
                    } else {
                        val target = Bukkit.getPlayer(args[1])
                        if(target != null)
                            sender.sendMessage("${target.name} level ${users[target.name]!![1]}")
                        else
                            sender.sendMessage("Игрок не найден!")
                    }
                }
            }
        }

        return false
    }

    fun checkPermission(sender: CommandSender, permission: String): Boolean { return sender.hasPermission(permission) }

}