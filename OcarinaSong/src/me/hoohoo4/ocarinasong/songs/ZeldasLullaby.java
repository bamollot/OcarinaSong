package me.hoohoo4.ocarinasong.songs;

import java.util.List;

import me.hoohoo4.ocarinasong.OcarinaSong;
import me.hoohoo4.ocarinasong.SignCheck;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class ZeldasLullaby implements Runnable {

    private Player player;
    OcarinaSong plugin;
    private int currently;
    
    static final byte song[] = {0x00,0x11,0x00,0x14,0x18,0x00,0x14,0x00,0x12,0x11,0x0F};



    public ZeldasLullaby(Player thisplayer,OcarinaSong plugin, Integer counter){
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
                List<Entity> entities = player.getNearbyEntities(10,10,10);
                for (Object entity : entities){
                    Entity thisentity = (Entity)entity;
                    if ("org.bukkit.craftbukkit.entity.CraftWolf".equals(thisentity.getClass().getName())){
                        Wolf thewolf = (Wolf)thisentity;
                         Location wolflocation = thewolf.getLocation();
                        if (player.hasPermission("ocarina.zelda.calm")){
                            if (thewolf.isAngry()){
                            AnimalTamer owner = thewolf.getOwner();
                            thewolf.remove();
                            World theworld = thewolf.getWorld();
                            thewolf = (Wolf)theworld.spawnEntity(wolflocation, EntityType.WOLF);
                            if (owner!=null){
                            thewolf.setOwner(owner);
                            thewolf.setTamed(true);
                            }
                            
                            }
                            thewolf.setSitting(true);
                        }
                        if (player.hasPermission("ocarina.zelda.tame")){
                            if (thewolf.isTamed()==false){
                                thewolf.setAngry(false);
                                thewolf.setTamed(true);
                                thewolf.setOwner(player);
                            }
                        }
                    }
                }
                if (player.hasPermission("ocarina.zelda.awaken")){Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SignCheck(player, plugin, "zelda"),0);}
                
                if (player.hasPermission("ocarina.zelda.tameforriding")){
                    plugin.isTaming.add(player);
                    
                    player.sendMessage(ChatColor.AQUA + "To tame an Animal, rightclick it with an apple.");
                    player.sendMessage(ChatColor.AQUA + "To train a Monster, weaken it, then rightclick it with a bone.");
                    
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
                        public void run() {plugin.isTaming.remove(player);return;}},200);
                 }
                return;
            }
            
            if (musicnote!= 0x00){
            plugin.PlayThatNoteFreely(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation(), musicnote);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ZeldasLullaby(player, plugin, currently),6);
            }
            else{
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ZeldasLullaby(player, plugin, currently),4);
            }

            
            
        }
        return;

    }
}
