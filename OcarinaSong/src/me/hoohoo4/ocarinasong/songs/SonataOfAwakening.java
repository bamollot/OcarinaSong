package me.hoohoo4.ocarinasong.songs;

import me.hoohoo4.ocarinasong.OcarinaSong;
import me.hoohoo4.ocarinasong.SignCheck;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class SonataOfAwakening implements Runnable {

    private Player player;
    OcarinaSong plugin;
    private int currently;
    
    static final byte song[] = {0x14, 0x11, 0x14, 0x11, 0x00, 0x08, 0x0F, 0x00, 0x08};



    public SonataOfAwakening(Player thisplayer,OcarinaSong plugin, Integer counter){
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
                if (player.hasPermission("ocarina.awakening"))Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SignCheck(player, plugin, "awakening"),0);
                return;
            }

            if (musicnote!= 0x00){plugin.PlayThatNoteFreely(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SonataOfAwakening(player, plugin, currently),5);
            }
            else{
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SonataOfAwakening(player, plugin, currently),2);
            }
        }
        return;

    }
}
