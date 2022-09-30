package me.iron.kotheventplugin.utils

import me.iron.kotheventplugin.PluginManager
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

class RepeatTask {

    private var plugin: Plugin? = null
    private val task: BukkitTask? = null

    constructor(instance: PluginManager?) {
        this.plugin = instance
    }

    /**
     *
     * @param period in ticks
     */
    constructor(runnable: Runnable, period: Long) {
        if (plugin!!.isEnabled) {
            val task = Bukkit.getScheduler().runTaskTimer(plugin!!, runnable, 0, period)
        } else {
            runnable.run()
        }
    }

    fun getTask(): BukkitTask? {
        return task
    }
}