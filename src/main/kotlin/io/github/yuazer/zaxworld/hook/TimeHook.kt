package io.github.yuazer.zaxworld.hook

import io.github.yuazer.zaxworld.ZaxWorld
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object TimeHook:PlaceholderExpansion {
    override val identifier: String = "zaxworld"
    override fun onPlaceholderRequest(player: Player?, args: String): String {
        val time = if (player==null) 0 else ZaxWorld.getPlayerCacheMap().getPlayerWorldTime(player.name,args)
        return "$time"
    }
}