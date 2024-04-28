package io.github.yuazer.zaxworld.listener

import io.github.yuazer.zaxworld.ZaxWorld
import io.github.yuazer.zaxworld.runnable.WorldRunnable
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerEvent {
    @SubscribeEvent
    fun onJoin(e: PlayerJoinEvent){
        val player = e.player
        val playerName = player.name
        val worldName = player.world.name
        val worldList = ZaxWorld.config.getConfigurationSection("World")?.getKeys(false) ?: emptySet()
        if (!worldList.contains(worldName)){
            ZaxWorld.getPlayerCacheMap().getPlayerAllWorld(playerName).forEach {
                ZaxWorld.runnableManager.stop(playerName, it)
            }
            return
        }else{
            val worldRunnable = WorldRunnable(playerName,worldName)
            ZaxWorld.runnableManager.add(playerName , worldName,worldRunnable)
            ZaxWorld.runnableManager.asyncStart(playerName,worldName)
        }
    }
    @SubscribeEvent
    fun onQuit(e:PlayerQuitEvent){
        val player=e.player
        val worldName = player.world.name
        ZaxWorld.runnableManager.stop(player.name, worldName)
    }
}