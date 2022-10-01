package me.iron.kotheventplugin.utils

import me.iron.kotheventplugin.PluginManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class RepeatTask(private var plugin: Plugin) {

    private var task: BukkitTask? = null

    /**
     *
     * @param period in ticks
     */
    fun generateRepeatTask(runnable: Runnable, period: Long) {
        if (this.plugin.isEnabled) {
            this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, runnable, 0, period)
        } else {
            runnable.run()
        }
    }

}