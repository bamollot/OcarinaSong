package me.hoohoo4.ocarinasong.songs;

import me.hoohoo4.ocarinasong.OcNotes;
import me.hoohoo4.ocarinasong.OcarinaSong;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EponasSong implements Runnable {

    private Player player;
    OcarinaSong plugin;
    private int currently;
    
    static final byte song[] = {OcNotes.UP.getByte(), OcNotes.REST.getByte(), OcNotes.LEFT.getByte(), OcNotes.RIGHT.getByte(), OcNotes.REST.getByte(), OcNotes.REST.getByte(), OcNotes.LEFT.getByte(), OcNotes.RIGHT.getByte()};



    public EponasSong(Player thisplayer,OcarinaSong plugin, Integer counter){
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
                if (player.hasPermission("ocarina.epona.call")){
                	boolean hasPet=false;
                	LivingEntity livent=null;
                	for(int i=0; i<player.getLocation().getWorld().getEntities().size(); i++){
                		if(player.getLocation().getWorld().getEntities().get(i).hasMetadata("tamed")){
                			if(player.getLocation().getWorld().getEntities().get(i).getMetadata("tamed").get(0).asString().equals(player.getName())){
                				hasPet=true;
                				livent=(LivingEntity) player.getLocation().getWorld().getEntities().get(i);
                			}
                		}
                	}
                    if (hasPet){
                        livent.teleport(player.getLocation().add(1,0,1));
                        player.sendMessage(ChatColor.AQUA + "Your pet is coming!");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "You don't even know how these creatures work, do you?");
                }
                return;
            }

            if (musicnote!= 0x00){plugin.PlayThatNote(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new EponasSong(player, plugin, currently),5);
            }
            else{
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new EponasSong(player, plugin, currently),2);
            }
        }
        return;

    }
}