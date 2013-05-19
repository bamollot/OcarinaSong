package me.hoohoo4.ocarinasong;

import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.material.Button;

public class KillButton implements Runnable {
    Button button;
    Block block;
    OcarinaSong plugin;
    public Server server;
    
    public KillButton(Button button, Block block,OcarinaSong plugin){
     this.plugin = plugin;
     this.block = block;
     this.button = button;
     this.server = plugin.getServer();

    }
        
    public void run() {
        button.setPowered(false);
        block.setData(button.getData());
        }
    }