package me.hoohoo4.ocarinasong.songs;

import me.hoohoo4.ocarinasong.OcarinaSong;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class SongOfStorms implements Runnable {

    private Player player;
    private boolean finished = false;
    OcarinaSong plugin;
    private int currently;
    
    static final byte song[] = {0x16, 0x00 ,0x17, 0x16,0x17, 0x16,0x12,0x0F, 0x00, 0x0F, 0x08, 0x0B, 0x0D, 0x0F,0x00,0x00, 0x0F, 0x08, 0x0B, 0x0D, 0x0A,};



    public SongOfStorms(Player thisplayer,OcarinaSong plugin, Integer counter){
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
            if (finished) {
                return;
            }
            
            
            if(currently<song.length)musicnote = song[currently];

            currently++;
            
            if (currently>song.length){
            	if (player.hasPermission("ocarina.storms")){
            		player.getWorld().setStorm(!player.getWorld().hasStorm());
                }
            	return;
            }
            if (musicnote!= 0x00 && musicnote!=0x16 && musicnote!= 0x17){
            plugin.PlayThatNoteFreely(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfStorms(player, plugin, currently),6);
            }
            else if (musicnote==0x00){
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfStorms(player, plugin, currently),4);
            }
            else{
            plugin.PlayThatNoteFreely(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfStorms(player, plugin, currently),4);
            }
            
            
        }
        return;

    }
}
