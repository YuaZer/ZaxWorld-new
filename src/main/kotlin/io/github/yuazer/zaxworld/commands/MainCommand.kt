package io.github.yuazer.zaxworld.commands

import io.github.yuazer.zaxworld.ZaxWorld
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.platform.util.asLangText
import taboolib.platform.util.sendLang

@CommandHeader("zaxworld", ["zw"], permission = "zaxworld.reload")
object MainCommand {
    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, context, argument ->
            ZaxWorld.config.reload()
            sender.sendLang("reload-message")
        }
    }

    @CommandBody
    val add = subCommand {
        dynamic("user") {
            execute<CommandSender> { sender, context, argument ->
                // 获取参数的值
                if (sender is Player){
                    val user:Player = sender
                    val worldName:String = user.world.name
                    val time:Int = context.int("user")
                    ZaxWorld.getPlayerCacheMap().add(sender.name,worldName, time)
                    sender.sendMessage(sender.asLangText("manage-command-message-add")
                        .replace("{user}",user.name)
                        .replace("{world}",worldName)
                        .replace("{time}",time.toString()))
                }
            }
            dynamic ("world"){
                dynamic ("time"){
                    execute<CommandSender> { sender, context, argument ->
                        // 获取参数的值
                        val user = context["user"]
                        val worldName:String = context["world"]
                        val time:Int = context.int("time")
                        ZaxWorld.getPlayerCacheMap().add(user,worldName, time)
                        sender.sendMessage(sender.asLangText("manage-command-message-add")
                            .replace("{user}",user)
                            .replace("{world}",worldName)
                            .replace("{time}",time.toString()))
                    }
                }
            }
        }
    }
    @CommandBody
    val reduce = subCommand {
        dynamic("user") {
            execute<CommandSender> { sender, context, argument ->
                // 获取参数的值
                if (sender is Player){
                    val user:Player = sender
                    val worldName:String = user.world.name
                    val time:Int = context.int("user")
                    ZaxWorld.getPlayerCacheMap().reduce(sender.name,worldName, time)
                    sender.sendMessage(sender.asLangText("manage-command-message-reduce")
                        .replace("{user}",user.name)
                        .replace("{world}",worldName)
                        .replace("{time}",time.toString()))
                }
            }
            dynamic ("world"){
                dynamic ("time"){
                    execute<CommandSender> { sender, context, argument ->
                        val user = context["user"]
                        val worldName:String = context["world"]
                        val time:Int = context.int("time")
                        ZaxWorld.getPlayerCacheMap().reduce(user,worldName, time)
                        sender.sendMessage(sender.asLangText("manage-command-message-reduce")
                            .replace("{user}",user)
                            .replace("{world}",worldName)
                            .replace("{time}",time.toString()))
                    }
                }
            }
        }
    }
    @CommandBody
    val set = subCommand {
        dynamic("user") {
            execute<CommandSender> { sender, context, argument ->
                if (sender is Player){
                    val user:Player = sender
                    val worldName:String = user.world.name
                    val time:Int = context.int("user")
                    ZaxWorld.getPlayerCacheMap().set(sender.name,worldName, time)
                    sender.sendMessage(sender.asLangText("manage-command-message-set")
                        .replace("{user}",user.name)
                        .replace("{world}",worldName)
                        .replace("{time}",time.toString()))
                }
            }
            dynamic ("world"){
                dynamic ("time"){
                    execute<CommandSender> { sender, context, argument ->
                        // 获取参数的值
                        val user = context["user"]
                        val worldName:String = context["world"]
                        val time:Int = context.int("time")
                        ZaxWorld.getPlayerCacheMap().set(user,worldName, time)
                        sender.sendMessage(sender.asLangText("manage-command-message-set")
                            .replace("{user}",user)
                            .replace("{world}",worldName)
                            .replace("{time}",time.toString()))
                    }
                }
            }
        }
//        dynamic ("time"){
//            execute<CommandSender> { sender, context, argument ->
//                if (sender is Player){
//                    val user:Player = sender
//                    val worldName:String = user.world.name
//                    val time:Int = context.int("time")
//                    ZaxWorld.getPlayerCacheMap().set(sender.name,worldName, time)
//                    sender.sendMessage(sender.asLangText("manage-command-message-set")
//                        .replace("{user}",user.name)
//                        .replace("{world}",worldName)
//                        .replace("{time}",time.toString()))
//                }
//            }
//        }
    }
    @CommandBody
    val check = subCommand {
        dynamic("user") {
            dynamic ("world"){
                execute<CommandSender> { sender, context, argument ->
                    // 获取参数的值
                    val user = context["user"]
                    val worldName:String = context["world"]
                    val time = ZaxWorld.getPlayerCacheMap().getPlayerWorldTime(user,worldName)
                    sender.sendMessage(sender.asLangText("manage-command-message-check")
                        .replace("{user}",user)
                        .replace("{world}",worldName)
                        .replace("{time}",time.toString()))
                }
            }
        }
        execute<CommandSender> { sender, context, argument ->
            if (sender is Player){
                val user:Player = sender
                val worldName:String = user.world.name
                val time = ZaxWorld.getPlayerCacheMap().getPlayerWorldTime(user.name,worldName)
                sender.sendMessage(sender.asLangText("manage-command-message-check")
                    .replace("{user}",user.name)
                    .replace("{world}",worldName)
                    .replace("{time}",time.toString()))
            }
        }
    }
}