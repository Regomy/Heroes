package me.rejomy.heroes.util

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

object EconomyManager {

    private var economy = Bukkit.getServicesManager().getRegistration(Economy::class.java).provider

    fun takeMoney(p: OfflinePlayer?, prise: Double): Boolean {
        return if (economy.getBalance(p) < prise) false else economy.withdrawPlayer(p, prise)
            .transactionSuccess()
    }

    fun giveMoney(p: OfflinePlayer?, money: Double) {
        economy.depositPlayer(p, money)
    }

    fun getBalance(p: OfflinePlayer?): Double {
        return economy.getBalance(p)
    }
}