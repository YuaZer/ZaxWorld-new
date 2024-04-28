package io.github.yuazer.zaxworld

import io.github.yuazer.zaxworld.database.DataLoader
import io.github.yuazer.zaxworld.database.YamlData
import io.github.yuazer.zaxworld.manager.BukkitRunnableManager
import io.github.yuazer.zaxworld.mymap.PlayerWorldMap
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.platform.BukkitPlugin

object ZaxWorld : Plugin() {
    private lateinit var playerWorldCache: PlayerWorldMap
    lateinit var runnableManager: BukkitRunnableManager
    fun getPlayerCacheMap(): PlayerWorldMap {
        return playerWorldCache
    }

    @Config("config.yml")
    lateinit var config: ConfigFile

    @Config("database.yml")
    lateinit var databaseConfig: ConfigFile
    override fun onEnable() {
        //初始化缓存，并从数据库中读取数据加载进缓存
        playerWorldCache = PlayerWorldMap
        //初始化Runnable管理器
        runnableManager = BukkitRunnableManager(BukkitPlugin.getInstance())
        if (config.getString("dataMode").equals("YAML", true)) {
            YamlData.loadYamlData()
        } else {
            //初始化数据库
            DataLoader.loadData2Cache_Async()
        }
        info("Successfully running ZaxWorld!")
        info("Author: YuaZer[QQ:1109132]")
        val dataMode = config.getString("dataMode")
        info("data mode is $dataMode")
    }

    override fun onDisable() {
        //取消所有调度器
        runnableManager.stopAll()
        //TODO 缓存数据保存到文件
        if (config.getString("dataMode").equals("YAML", true)) {
            YamlData.saveYamlData()
        } else {
            DataLoader.saveDataFromCache_Async()
        }
    }
}