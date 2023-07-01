package me.rejomy.heroes.command

import me.rejomy.heroes.INSTANCE
import me.rejomy.heroes.db
import me.rejomy.heroes.nekro
import me.rejomy.heroes.users
import me.rejomy.heroes.util.newplayersInv
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

        if(args!!.isEmpty() && sender is Player) {
            if(users.containsKey(sender.name))
                sender.openInventory(nekro[sender.name])
            else
                sender.openInventory(newplayersInv)
        } else if (checkPermission(sender!!, "admin")) {
            if(args.isEmpty()) return true
            when (args[0]) {
                "help" -> sender.sendMessage("/reload | /info name | /remove name | /set name size")
                "reload", "релоад" -> INSTANCE.reloadConfig()
                "info" -> if(hasPlayer(args[1])) sender.sendMessage("${args[1]} level ${users[args[1]]!![1]}")
                "remove" -> if(hasPlayer(args[1])) {
                        db.delete(args[1])
                        users.remove(args[1])
                        nekro.remove(args[1])
                    }
                "set" -> if(hasPlayer(args[1])) {
                    if(args.size < 3) return true
                    db.set(args[1],  users[args[1]]!![0], args[2].toInt())
                    users[args[1]] = arrayOf(users[args[1]]!![0], args[2])
                }
            }
        }

        return false
    }

    fun checkPermission(sender: CommandSender, permission: String): Boolean { return sender.hasPermission(permission) }

    fun hasPlayer(player: String): Boolean { return player.isNotEmpty() && users.containsKey(player) }

}