package me.TomTheDeveloper.Handlers;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.TomTheDeveloper.Game.GameInstance;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class MessageHandler_v1_9_R1 {

    private GameInstance gameInstance;

    private MessageHandler_v1_9_R1(GameInstance gameInstance){
        this.gameInstance = gameInstance;
    }

    public  static void sendSubTitleMessage(Player player, String message){
        PlayerConnection titleConnection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleMain);
        titleConnection.sendPacket(packetPlayOutTitle);
    }

    public static void sendTitleMessage(Player player, String message){
        PlayerConnection titleConnection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
        titleConnection.sendPacket(packetPlayOutTitle);
    }

    public static void sendActionBarMessage(Player player, String message){
        PlayerConnection titleConnection = ((CraftPlayer) player).getHandle().playerConnection;
        IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(titleMain, (byte)2);
        titleConnection.sendPacket(bar);
    }
	
}
