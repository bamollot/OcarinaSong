package me.hoohoo4.ocarinasong;

import org.bukkit.Note;

public enum OcNotes {
    UP(0x14),
    DOWN(0x0B),
    LEFT(0x11),
    RIGHT(0x0F),
    A(0x08),
    REST(0x00);
    
    
    public byte bytevalue;
    
    OcNotes(int nom){
        byte derp;
        derp = (byte) nom;
        bytevalue = derp;
    }
    
    
    public Note getNote(){
        byte thebyte = 0x00;
        
        thebyte = bytevalue;
        
        Note note = new Note(thebyte);

        return note;
    }
    
    public Byte getByte(){
        byte thebyte = 0x00;
        
        thebyte = bytevalue;

        return thebyte;
    }
    
}