package me.rejomy.heroes.util

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

object EconomyManager {
    fun init() {
        val reg = Bukkit.getServicesManager().getRegistration(
            Economy::class.java
        )
        if (reg != null) e = reg.provider
    }

    private var e: Economy? = null
    fun takeMoney(p: OfflinePlayer?, prise: Double): Boolean {
        if (e == null) return false
        return if (e!!.getBalance(p) < prise) false else e!!.withdrawPlayer(p, prise)
            .transactionSuccess()
    }

    fun giveMoney(p: OfflinePlayer?, money: Double) {
        if (e == null) return
        e!!.depositPlayer(p, money)
    }

    fun getBalance(p: OfflinePlayer?): Double {
        return e!!.getBalance(p)
    }
}