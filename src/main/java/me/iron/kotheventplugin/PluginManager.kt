package me.iron.kotheventplugin

import me.iron.kotheventplugin.commands.Kit
import me.iron.kotheventplugin.managers.GameManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PluginManager : JavaPlugin() {

    companion object {
        var instance: PluginManager? = null
            private set;
    }

    override fun onEnable() {

        instance = this

        val gameManager = GameManager(this)

        gameManager.startScoreboardTask()

        getCommand("kit")?.setExecutor(Kit())
        Bukkit.getPluginManager().registerEvents(Kit(),this)
    }
    override fun onDisable() {}
}