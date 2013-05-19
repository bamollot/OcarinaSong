//BAM
//11.12.12

/*
 * TODO
 * 
 * Fix song playing leaving note block behind
 */


package me.hoohoo4.ocarinasong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import me.hoohoo4.ocarinasong.songs.EponasSong;
import me.hoohoo4.ocarinasong.songs.SonataOfAwakening;
import me.hoohoo4.ocarinasong.songs.SongOfHealing;
import me.hoohoo4.ocarinasong.songs.SongOfStorms;
import me.hoohoo4.ocarinasong.songs.SongOfTime;
import me.hoohoo4.ocarinasong.songs.ZeldasLullaby;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.NoteBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Dye;
import org.bukkit.material.Lever;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class OcarinaSong extends JavaPlugin implements Listener {
	
	/*//////////////////
	 * GLOBAL VARIABLES/
	 *//////////////////
	
    public Configuration vehicleByPlayer;
    FixedMetadataValue T=new FixedMetadataValue(this, true);
    public OcarinaSong plugin=this;
    @SuppressWarnings("rawtypes")
	public Map<Player, List> PlayersNotes = new HashMap<Player,List>();
    public Map<Player, Location> MusicBoxBlockLocation = new HashMap<Player, Location>();
    public Map<Location, Material> ReturnMaterial = new HashMap<Location, Material>();
    public Map<Location, BlockState> ReturnBlock = new HashMap<Location, BlockState>();
    public Set<Player> isTaming = new HashSet<Player>(); 
    
    /*////////////////
     * EVENT HANDLERS/
     *////////////////
    
    //Called on plugin enable
    public void onEnable(){
    	
    	//Routine log setup and event registry
        this.getLogger().info("OcarinaSong is Enabled");
        getServer().getPluginManager().registerEvents(this, this);
        
        //Adding a custom recipe
        ItemStack ocarina=new ItemStack(Material.INK_SACK, 1);
        List<String> lore=new ArrayList<String>();
        lore.add("Right click to play");
        Dye lapizData=(Dye) new ItemStack(Material.INK_SACK).getData();
        lapizData.setColor(DyeColor.BLUE);
        ocarina=lapizData.toItemStack(1);
        ItemMeta ocarinaMeta=ocarina.getItemMeta();
        ocarinaMeta.setDisplayName("Ocarina");
        ocarinaMeta.setLore(lore);
        ocarina.setItemMeta(ocarinaMeta);
        ShapedRecipe ocarinaRecipe=new ShapedRecipe(ocarina);
        ocarinaRecipe.shape("lll",
        					"bbb",
        					"lll");
        ocarinaRecipe.setIngredient('l', lapizData);
        ocarinaRecipe.setIngredient('b', Material.CLAY_BRICK);
        getServer().addRecipe(ocarinaRecipe);
    }//end of onEnable
    
    //Called on plugin disable
    public void onDisable(){
        this.getLogger().info("OcarinaSong has stopped!");
    }//end of onDisable
    
    //Called when the held item changes
    @EventHandler
    public void onItemHeldChangeEvent(PlayerItemHeldEvent event) {
    	Player p=event.getPlayer();
    	if (event.getPlayer().hasMetadata("playing")){
    		event.getPlayer().sendMessage(ChatColor.YELLOW+"You stop playing your ocarina.");
    		event.getPlayer().removeMetadata("playing", this);
    		undrawNoteblock(MusicBoxBlockLocation.get(p), ReturnBlock.get(plugin.MusicBoxBlockLocation.get(p)));
			ReturnBlock.remove(MusicBoxBlockLocation.get(p));
	        MusicBoxBlockLocation.remove(p);
        }
    }//end of onItemHeldChangeEvent
    
    //Called when a player dies
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){
    	Player p=event.getEntity();
    	if(p.hasMetadata("playing")){
    		p.removeMetadata("playing", this);
    		undrawNoteblock(MusicBoxBlockLocation.get(p), ReturnBlock.get(plugin.MusicBoxBlockLocation.get(p)));
			ReturnBlock.remove(MusicBoxBlockLocation.get(p));
	        MusicBoxBlockLocation.remove(p);
    	}
    }//end of onPlayerDeathEvent
    
    //Called on player interacting with the environment
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
    	Player p=event.getPlayer();
    	ItemStack i=event.getItem();
    	boolean canCancel=true;
    	
    	if(event.hasItem()){
    		if(i.hasItemMeta()&&i.getItemMeta().hasDisplayName()&&i.getItemMeta().getDisplayName().equals("Ocarina")){
    			if(event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
    				event.setCancelled(true);
    				if(!p.hasMetadata("playing")){
    					p.setMetadata("playing", T);
    					p.sendMessage(ChatColor.AQUA+"You begin to play your ocarina.");
    					MusicBoxBlockLocation.put(p, p.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation());
    					ReturnBlock.put(MusicBoxBlockLocation.get(p), MusicBoxBlockLocation.get(p).getBlock().getState());
    					drawNoteblock(MusicBoxBlockLocation.get(p));
    					canCancel=false;
    				}
    			}	
    		}
    	}
    	if(p.hasMetadata("playing")&&canCancel){
			p.removeMetadata("playing", this);
			p.sendMessage(ChatColor.YELLOW+"You stop playing your ocarina.");
			undrawNoteblock(MusicBoxBlockLocation.get(p), ReturnBlock.get(plugin.MusicBoxBlockLocation.get(p)));
			ReturnBlock.remove(MusicBoxBlockLocation.get(p));
	        MusicBoxBlockLocation.remove(p);
		}
    }//end of onPlayerInteractEvent
    
    //Called on interacting with an entity
    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked().hasMetadata("tamed")){
            if (event.getRightClicked().getMetadata("tamed").get(0).asString().equals(player.getName())){
                if (event.getRightClicked().getPassenger() == player){
                    event.getRightClicked().eject();
                    player.sendMessage(ChatColor.AQUA + "You dismount your pet!");
                }
                else if (event.getRightClicked().getPassenger() == null){
                    event.getRightClicked().setPassenger(player);
                    player.sendMessage(ChatColor.AQUA + "You mount your pet!");
                }
                else{
                    if (player.hasPermission("ocarina.horses.dismountopponent")){
                        Random generator = new Random();
                        Integer  inty = generator.nextInt(20);
                        if (inty==3){
                            player.sendMessage(ChatColor.RED + "You have dismounted the Jockey!");
                            
                            if (event.getRightClicked().getPassenger() instanceof Player){
                                ((Player) event.getRightClicked()).sendMessage(ChatColor.RED + player.getName() + " has dismounted you from your pet!");
                            }
                            event.getRightClicked().setPassenger(null);
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "You tug at the Jockey!");
                        }
                    }                
                }
                event.setCancelled(true);
            }
        }
        else if (event.getRightClicked() instanceof LivingEntity && !(event.getRightClicked() instanceof Player)){
            LivingEntity entity = (LivingEntity) event.getRightClicked();
            if(isTaming.contains(player)){
            	event.setCancelled(true);
               if (player.getItemInHand().getType()==Material.APPLE){
                   if (entity instanceof Creature){
                	   entity.setMetadata("tamed", new FixedMetadataValue(this, player.getName()));
                       player.sendMessage(ChatColor.AQUA + "You tame the majestic creature!");
                       if (player.getItemInHand().getAmount()!=1)player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                       else player.getInventory().clear(player.getInventory().getHeldItemSlot());
                   }
                   else{
                       player.sendMessage(ChatColor.RED + "You cannot tame this with an apple!");
                   }
               }
               else if (player.getItemInHand().getType()==Material.BONE){
                   if (entity instanceof Wolf || entity instanceof Monster){
                       if (entity.getHealth() < 8){
                           entity.setMetadata("tamed", new FixedMetadataValue(this, player.getName()));
                           player.sendMessage(ChatColor.AQUA + "You tame the wild beast!");
                           if (player.getItemInHand().getAmount()!=1)player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
                           else player.getInventory().clear(player.getInventory().getHeldItemSlot());
                       }
                       else{
                           player.sendMessage(ChatColor.RED + "It's too strong to be tamed!");
                       }
                   }
                   else{
                       player.sendMessage(ChatColor.RED + "You cannot tame this with a bone!");
                   }
               }
               else{
                   player.sendMessage(ChatColor.RED + "You cannot tame this with a " + player.getItemInHand().getType().toString());
               }   
            }
        }
    }//end of onPlayerInteractEntityEvent
    
    //Called when an entity targets another entity
    @EventHandler
    public void onEntityTargetEvent(EntityTargetEvent event) {
        if (event.getEntity().hasMetadata("tamed")){
        	if(event.getTarget() instanceof Player){
        		if (event.getEntity().getMetadata("tamed").get(0).asString().equals(((Player)event.getTarget()).getName())){
        			event.setCancelled(true);
        		}
        	}
        }
    }//end of onEntityTargetEvent
    
    //Called when an entity dies
    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity().hasMetadata("tamed")){
            boolean isOnline=false;
        	Player rider = getServer().getPlayer(event.getEntity().getMetadata("tamed").get(0).asString());
            for(Player p:getServer().getOnlinePlayers()){
            	if(p.equals(rider)) isOnline=true;
            }
            if(isOnline) rider.sendMessage(ChatColor.RED + "Your pet has died!");
        }
    }
    
    //Called when a sign's text changes
    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event) {
        if (event.getBlock().getType()==Material.WALL_SIGN){
            if (event.getLine(1).toLowerCase().equals("[awaken]".toLowerCase())||event.getLine(1).toLowerCase().equals("§b[awaken]".toLowerCase())){
                if (event.getPlayer().hasPermission("ocarina.awakening.sign")){
                    event.setLine(1, "§b[Awaken]");
                    event.getPlayer().sendMessage(ChatColor.AQUA+ "Created " + ChatColor.GRAY + "Awakening Detector" + ChatColor.AQUA + "!");
                }
                else{
                    event.getPlayer().sendMessage(ChatColor.BLUE + "You don't understand ancient magicks enough!");
                    event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.SIGN,1));
                    event.getBlock().setType(Material.AIR);
                    event.setCancelled(true);
                }
            }       
            if (event.getLine(1).toLowerCase().equals("[zelda]".toLowerCase())||event.getLine(1).toLowerCase().equals("§b[zelda]".toLowerCase())){
                if (event.getPlayer().hasPermission("ocarina.zelda.sign")){
                    event.setLine(1, "§b[Zelda]");
                    event.getPlayer().sendMessage(ChatColor.AQUA+ "Created " + ChatColor.GRAY + "Zelda's Lullaby Detector" + ChatColor.AQUA + "!");
                }
                else{
                    event.getPlayer().sendMessage(ChatColor.BLUE + "You aren't trusted by the Hyrulean Royal Family.");
                    event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.SIGN,1));
                    event.getBlock().setType(Material.AIR);
                    event.setCancelled(true);
                }
            }
            
            if (event.getLine(1).toLowerCase().equals("[time]".toLowerCase())||event.getLine(1).toLowerCase().equals("§b[time]".toLowerCase())){
                if (event.getPlayer().hasPermission("ocarina.time.sign")){
                    event.setLine(1, "§b[Time]");
                    event.getPlayer().sendMessage(ChatColor.AQUA+ "Created " + ChatColor.GRAY + "Song of Time Detector" + ChatColor.AQUA + "!");
                }
                else{
                    event.getPlayer().sendMessage(ChatColor.BLUE + "You don't well enough understand the art of watchmaking!");
                    event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.SIGN,1));
                    event.getBlock().setType(Material.AIR);
                    event.setCancelled(true);
                }
            }
        }
        return;
    }//end of onSignChangeEvent
    
    //Called on player moving
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
    	Player player=event.getPlayer();
    	Player p=event.getPlayer();
    	Location from = event.getFrom();
    	Location to=event.getTo();
        
        //Score_Under's algorithm,
        double angle = Math.atan2(-(to.getX() - from.getX()),to.getZ() - from.getZ())*180.0f/Math.PI;
        int facing = (int) Math.round(((angle - player.getLocation().getYaw()) / 45.0f) % 8.0f);
        if(facing<0)facing+=8;
    	
        //Handles note playing
    	if(p.hasMetadata("playing")){
    		
    		//Resets
    		p.teleport(from);
    		
    		//Interprets Movement
    		switch (facing) {
            case 0:
                plugin.hitUp(player,from.add(0, -1, 0));
                break;
            case 2:
                plugin.hitRight(player,from.getBlock().getRelative(BlockFace.DOWN).getLocation());
                break;
            case 4:
                plugin.hitDown(player,from.getBlock().getRelative(BlockFace.DOWN).getLocation());
                break;
            case 6:
                plugin.hitLeft(player,from.getBlock().getRelative(BlockFace.DOWN).getLocation());
                break;
    		}
    		
    	}
    }//end of onPlayerMoveEvent
    
    //Called when a player toggles sneak
    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("ocarina")) {
            return;
        }   
        if (player.hasMetadata("playing")){             
        	if (event.isSneaking()){
        		hitShift(player, player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation());            
            }
        }
        return;
    }
    
    //Called when a command is sent
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equals("ocarina")){
    		listSongs(sender);
    		return true;
    	}
    	return false;
    }//end of onCommand
    
    /*////////////////
     * HELPER METHODS/
     *////////////////
    
    //Displays the available songs
    public void listSongs(CommandSender sender){
    	sender.sendMessage(ChatColor.AQUA + "Songs you can Play:");
    	sender.sendMessage(ChatColor.GRAY + "Song of Storms:" + ChatColor.YELLOW + " S V ^ S V ^");
    	sender.sendMessage(ChatColor.GRAY + "Song of Time:" + ChatColor.YELLOW + " > S V > S V");
    	sender.sendMessage(ChatColor.GRAY + "Song of Healing:" + ChatColor.YELLOW + " < > V < > V");
    	sender.sendMessage(ChatColor.GRAY + "Zelda's Lullaby:" + ChatColor.YELLOW + " < ^ > < ^ >");
    	sender.sendMessage(ChatColor.GRAY +  "Epona's Song:" + ChatColor.YELLOW + " ^ < > ^ < >");
    	sender.sendMessage(ChatColor.GRAY + "Sonata of Awakening:" + ChatColor.YELLOW + " ^ < ^ < S > S");
    }
    
    //Plays a song
    public void PlaySong(String song, Player player){
        if ("time".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfTime(player, plugin, 0),4);
        }
        else if ("storms".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfStorms(player, plugin, 0),4);
        }
        else if ("healing".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SongOfHealing(player, plugin, 0),4);
            if (player.hasPermission("ocarina.healing")){
            }
        }  
        else if ("zelda".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ZeldasLullaby(player, plugin, 0),4);
            if (player.hasPermission("ocarina.zelda")){
            }
        }
        else if ("awakening".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SonataOfAwakening(player, plugin, 0),4);
            if (player.hasPermission("ocarina.awakening")){
            }
        }
        else if ("epona".equals(song)){getServer().getScheduler().scheduleSyncDelayedTask(plugin, new EponasSong(player, plugin, 0),4);
            if (player.hasPermission("ocarina.epona.call")){
            }
        }
        return;
    }
    
    //Handle live playing of songs
    public void hitUp(Player player, Location location) {
        byte musicnote = 0x14;
        /* B */
        PlayThatNote(player, location, musicnote);
        return;
    }

    public void hitLeft(Player player, Location location) {
        byte musicnote = 0x11;
        /* A */
        PlayThatNote(player, location, musicnote);
        return;
    }

    public void hitRight(Player player, Location location) {
        byte musicnote = 0x0F;
        /* G */
        PlayThatNote(player, location, musicnote);
        return;
    }

    public void hitDown(Player player, Location location) {
        byte musicnote = 0x0B;
        /* F */
        PlayThatNote(player, location, musicnote);
        return;
    }

    public void hitShift(Player player, Location location) {
        byte musicnote = 0x08;
        /* Other Note */
        PlayThatNote(player, location, musicnote);
        return;
    }
    
    //Plays a Note
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void PlayThatNote(Player player, Location location, byte bite){
    	
    	final Player p=player;
    	final byte b=bite;
    	
    	NoteBlock n=(NoteBlock) MusicBoxBlockLocation.get(p).getBlock().getState();
    	
    	n.play(Instrument.PIANO, new Note(b));
		
    	List IndivNotes = new LinkedList();
    	IndivNotes = new ArrayList();     
        
    	if (PlayersNotes.containsKey(p)){
    			IndivNotes = PlayersNotes.get(p);
    	}
         
    	boolean success = false;
    	IndivNotes.add(b);
            
    	if (IndivNotes.size()>=6){
    		success = SongCheck(p,IndivNotes);
    	}
    	if (success==true)IndivNotes.clear();
    		PlayersNotes.put(p, IndivNotes);
    	}
    
    //Plays a Note without recording it
	public void PlayThatNoteFreely(Player player, Location location, byte bite){
    	
    	final Player p=player;
    	final byte b=bite;
    	
    	final NoteBlock n=(NoteBlock) MusicBoxBlockLocation.get(p).getBlock().getState();
    	
    	n.play(Instrument.PIANO, new Note(b));
    }
    
    //Draws the note block
    public void drawNoteblock(Location loc){
    	
        loc.getBlock().setType(Material.NOTE_BLOCK);
    }
    
    //Returns the note block to normal
    public void undrawNoteblock(Location loc, BlockState b){
    	loc.getBlock().setType(b.getType());
    	loc.getBlock().setData(b.getData().getData());
    }
    
    //Checks for a button block
    public void checkForButtons(Block theblock, BlockFace blockface, int ticks){
        
        if (theblock.getRelative(blockface).getType().equals(Material.LEVER)|| theblock.getRelative(blockface).getType().equals(Material.STONE_BUTTON)){
            Button button = new Button();
            Lever lever = new Lever();
            if (theblock.getRelative(blockface).getType().equals(Material.STONE_BUTTON)||
            		theblock.getRelative(blockface).getType().equals(Material.WOOD_BUTTON)){
            	button.setData(theblock.getRelative(blockface).getData());
                button.setPowered(true);
                getServer().getScheduler().scheduleSyncDelayedTask(plugin, new KillButton(button, theblock.getRelative(blockface), this),ticks);
                theblock.getRelative(blockface).setData(button.getData());
            }
            if (theblock.getRelative(blockface).getType().equals(Material.LEVER)){
            	lever.setData(theblock.getRelative(blockface).getData());
                lever.setPowered(!lever.isPowered());
                theblock.getRelative(blockface).setData(lever.getData());
            }

        }
    return;
    }
    
    //Checks to see if a player played a song
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean SongCheck(Player player, List YourSong){
        List SongOfTime = new LinkedList();
        SongOfTime = new ArrayList();  
        byte musicnote;
        musicnote = 0x0F;
        SongOfTime.add(musicnote);
        musicnote = 0x08;
        SongOfTime.add(musicnote);
        musicnote = 0x0B;
        SongOfTime.add(musicnote);
        musicnote = 0x0F;
        SongOfTime.add(musicnote);
        musicnote = 0x08;
        SongOfTime.add(musicnote);
        musicnote = 0x0B;
        SongOfTime.add(musicnote);
        List SongOfStorms = new LinkedList();
        SongOfStorms = new ArrayList();  
        musicnote = 0x08;
        SongOfStorms.add(musicnote);
        musicnote = 0x0B;
        SongOfStorms.add(musicnote);
        musicnote = 0x14;
        SongOfStorms.add(musicnote);
        musicnote = 0x08;
        SongOfStorms.add(musicnote);
        musicnote = 0x0B;
        SongOfStorms.add(musicnote);
        musicnote = 0x14;
        SongOfStorms.add(musicnote);
        List SongOfHealing = new LinkedList();
        SongOfHealing = new ArrayList();    
        musicnote = 0x11;
        SongOfHealing.add(musicnote);
        musicnote = 0x0F;
        SongOfHealing.add(musicnote);
        musicnote = 0x0B;
        SongOfHealing.add(musicnote);
        musicnote = 0x11;
        SongOfHealing.add(musicnote);
        musicnote = 0x0F;
        SongOfHealing.add(musicnote);
        musicnote = 0x0B;
        SongOfHealing.add(musicnote);
        List ZeldasLullaby = new LinkedList();
        ZeldasLullaby = new ArrayList();    
        musicnote = 0x11;
        ZeldasLullaby.add(musicnote);
        musicnote = 0x14;
        ZeldasLullaby.add(musicnote);
        musicnote = 0x0F;
        ZeldasLullaby.add(musicnote);
        musicnote = 0x11;
        ZeldasLullaby.add(musicnote);
        musicnote = 0x14;
        ZeldasLullaby.add(musicnote);
        musicnote = 0x0F;
        ZeldasLullaby.add(musicnote);
        List SonataOfAwakening = new LinkedList();
        SonataOfAwakening = new ArrayList();    
        musicnote = 0x14;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x11;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x14;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x11;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x08;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x0F;
        SonataOfAwakening.add(musicnote);
        musicnote = 0x08;
        SonataOfAwakening.add(musicnote);
        
        List EponasSong = new LinkedList();
        EponasSong = new ArrayList();    
        EponasSong.add(OcNotes.UP.getByte());
        EponasSong.add(OcNotes.LEFT.getByte());
        EponasSong.add(OcNotes.RIGHT.getByte());
        EponasSong.add(OcNotes.UP.getByte());
        EponasSong.add(OcNotes.LEFT.getByte());
        EponasSong.add(OcNotes.RIGHT.getByte());
        
       
        List LastSix = new LinkedList();
        LastSix = new ArrayList();   
        
        LastSix.add(YourSong.get(YourSong.size()-6));
        LastSix.add(YourSong.get(YourSong.size()-5));
        LastSix.add(YourSong.get(YourSong.size()-4));
        LastSix.add(YourSong.get(YourSong.size()-3));
        LastSix.add(YourSong.get(YourSong.size()-2));
        LastSix.add(YourSong.get(YourSong.size()-1));

        List LastSeven = new LinkedList();
        LastSeven = new ArrayList();   
        if (YourSong.size()>6){
            LastSeven.add(YourSong.get(YourSong.size()-7));
            LastSeven.add(YourSong.get(YourSong.size()-6));
            LastSeven.add(YourSong.get(YourSong.size()-5));
            LastSeven.add(YourSong.get(YourSong.size()-4));
            LastSeven.add(YourSong.get(YourSong.size()-3));
            LastSeven.add(YourSong.get(YourSong.size()-2));
            LastSeven.add(YourSong.get(YourSong.size()-1));
        }
        
        
            
        if (SongOfTime.equals(LastSix)){
            player.sendMessage(ChatColor.AQUA + "Played the " + ChatColor.GRAY + "Song of Time" + ChatColor.AQUA + "!");
            PlaySong("time", player);
            return true;
        }
        else if (SongOfStorms.equals(LastSix)){
            player.sendMessage(ChatColor.AQUA + "Played the " + ChatColor.GRAY + "Song of Storms" + ChatColor.AQUA + "!");
            PlaySong("storms", player);
            return true;
        }
        else if (SongOfHealing.equals(LastSix)){
            player.sendMessage(ChatColor.AQUA + "Played the " + ChatColor.GRAY + "Song of Healing" + ChatColor.AQUA + "!");
            PlaySong("healing", player);
            return true;
        }
        else if (ZeldasLullaby.equals(LastSix)){
            player.sendMessage(ChatColor.AQUA + "Played " + ChatColor.GRAY + "Zelda's Lullaby" + ChatColor.AQUA + "!");
            PlaySong("zelda", player);
            return true;
        }
        else if (EponasSong.equals(LastSix)){
            player.sendMessage(ChatColor.AQUA + "Played " + ChatColor.GRAY + "Epona's Song" + ChatColor.AQUA + "!");
            PlaySong("epona", player);
            return true;
        }
        else if (SonataOfAwakening.equals(LastSeven)){
            player.sendMessage(ChatColor.AQUA + "Played the " + ChatColor.GRAY + "Sonata of Awakening" + ChatColor.AQUA + "!");
            PlaySong("awakening", player);
            return true;
        }
        
        
        return false;
    }
}//end of plugin