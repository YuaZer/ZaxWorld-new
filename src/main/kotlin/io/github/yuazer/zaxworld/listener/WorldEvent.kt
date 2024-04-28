package io.github.yuazer.zaxworld.listener

import io.github.yuazer.zaxworld.ZaxWorld
import io.github.yuazer.zaxworld.runnable.WorldRunnable
import org.bukkit.event.player.PlayerChangedWorldEvent
import taboolib.common.platform.event.SubscribeEvent

object WorldEvent {

    @SubscribeEvent
    fun onChangeWorld(e:PlayerChangedWorldEvent) {
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
}