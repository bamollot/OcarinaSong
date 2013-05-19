package me.hoohoo4.ocarinasong.songs;

import me.hoohoo4.ocarinasong.OcarinaSong;
import me.hoohoo4.ocarinasong.SignCheck;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class SongOfTime implements Runnable {

    private Player player;
    OcarinaSong plugin;
    private int currently;
    
    static final byte song[] = {0x0F,0x12, 0x11,0x00, 0x0D,0x0B,0x0D, 0x00, 0x0F, 0x08,0x00,0x6,0x0A,0x08};



    public SongOfTime(Player thisplayer,OcarinaSong plugin, Integer counter){
     this.plugin = plugin;
     player = thisplayer;
     currently = counter;
    }
    
        public void run() {
        
        if (player.hasMetadata("playing")) {
            byte musicnote = 0x01;
            if (!player.hasPermission("ocarina")) {
                return;
            }
            
            
            if(currently<song.length)musicnote = song[currently];

            currently++;
            
            if (currently>song.length){
                if (player.hasPermission("ocarina.time.awaken")){
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SignCheck(player, plugin, "time"),0);
                }
                if (player.hasPermission("ocarina.time")){
                        plugin.getServer().broadcastMessage(ChatColor.GREEN + player.getName() + " has changed the time of day!");
                        player.getWorld().setTime(player.getWorld().getTime()+12000);
                }
                return;
            }

            if (musicnote!= 0x00){plugin.PlayThatNote(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfTime(player, plugin, currently),5);
            }
            else{
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfTime(player, plugin, currently),3);
            }
        }
        return;

    }
}