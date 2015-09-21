/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saul.inc.a13xis.flatasbread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import saul.Main;
import saul.MessageQueue;
import saul.inc.a13xis.flatasbread.model.Map;

/**
 *
 * @author alexis
 */
public class BuildAction {
    public static void list(Main m,CommandSender sender, int size){
        if(size>ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT){
            sender.sendMessage(ChatColor.DARK_PURPLE+"Liste aller Maps auf dem Server "+ChatColor.DARK_BLUE+"(Seite 1 von "+Double.toString(size/ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT)+")");
        }
        else {
            sender.sendMessage(ChatColor.DARK_PURPLE+"Liste aller Maps auf dem Server");
        }
        int i=0;
        String[] mapnames=Main.map_edit.keySet().toArray(new String[Main.map_edit.size()]);
        while(i<ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT){
            if(i>mapnames.length-1)break;
            Location spawn=Main.map_spawn.get(mapnames[i]);
            boolean edit=Main.map_edit.get(mapnames[i]);
            ChatColor editcol;
            if(edit) editcol=ChatColor.GREEN;
            else editcol=ChatColor.DARK_RED;
            sender.sendMessage(ChatColor.GOLD+mapnames[i]+ChatColor.DARK_PURPLE+" v"+Main.map_version.get(mapnames[i])+" "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ()+" "+editcol+Boolean.toString(edit));
            i++;
        }
    }
    public static void list(Main m,CommandSender sender, int size, int page){
        if(size>ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT&&(size/ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT+1)>=page){
            sender.sendMessage(ChatColor.DARK_PURPLE+"Liste aller Maps auf dem Server "+ChatColor.DARK_BLUE+"(Seite "+Integer.toString(page)+" von "+Integer.toString((int)(size/ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT)+1)+")");
            int i=(page-1)*ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT;
            String[] mapnames=Main.map_edit.keySet().toArray(new String[Main.map_edit.size()]);
            while(i<page*ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT){
                if(i>mapnames.length-1)break;
                Location spawn=Main.map_spawn.get(mapnames[i]);
                boolean edit=Main.map_edit.get(mapnames[i]);
                ChatColor editcol;
                if(edit) editcol=ChatColor.GREEN;
                else editcol=ChatColor.DARK_RED;
                sender.sendMessage(ChatColor.GOLD+mapnames[i]+ChatColor.DARK_PURPLE+" v"+Main.map_version.get(mapnames[i])+" "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ()+" "+editcol+Boolean.toString(edit));
                i++;
            }
        }
        else {
            sender.sendMessage(ChatColor.DARK_PURPLE+"Liste aller Maps auf dem Server"+ChatColor.DARK_BLUE+"(Seite 1 von "+Integer.toString((int)(size/ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT)+1)+")");
            int i=0;
            String[] mapnames=Main.map_edit.keySet().toArray(new String[Main.map_edit.size()]);
            while(i<ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT){
                if(i>mapnames.length-1)break;
                Location spawn=Main.map_spawn.get(mapnames[i]);
                boolean edit=Main.map_edit.get(mapnames[i]);
                ChatColor editcol;
                if(edit) editcol=ChatColor.GREEN;
                else editcol=ChatColor.DARK_RED;
                sender.sendMessage(ChatColor.GOLD+mapnames[i]+ChatColor.DARK_PURPLE+" v"+Main.map_version.get(mapnames[i])+" "+spawn.getBlockX()+" "+spawn.getBlockY()+" "+spawn.getBlockZ()+" "+editcol+Boolean.toString(edit));
                i++;
            }
        }
    }
    public static void copyPaste(File src, File dest) throws IOException{
        if(src.isDirectory()){
                if(!dest.exists()){
                   dest.mkdir();
                }
                String files[] = src.list();
                for (String file : files) {
                   File srcFile = new File(src, file);
                   File destFile = new File(dest, file);
                   copyPaste(srcFile,destFile);
                }
        }else{
            OutputStream out;
                try (InputStream in = new FileInputStream(src)) {
                    out = new FileOutputStream(dest);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0){
                        out.write(buffer, 0, length);
                    }
                }
            out.close();
        }
    }
    
    public static void recDelete(File src){
        if(src.isDirectory()){
                for (File file : src.listFiles()) {
                   recDelete(file);
                }
                src.delete();
        }else{
            boolean succ=src.delete();
            if(!succ){
                Logger.getLogger(BuildCommand.class.getName()).log(Level.WARNING, "{0} could not be deleted!", src.getName());
            }
        }
    }

    static void create(Main m, CommandSender sender, String map) {
        if(m.getServer().getWorld(map)!=null){
            sender.sendMessage(ChatColor.RED+"Dieser Name wird schon verwendet. Probiers mit einem anderen");
            return;
        }
        WorldCreator worldCreator = new WorldCreator(map);
        worldCreator.generator(new GenerateEmpty());
        worldCreator.createWorld();
        Map newMap=new Map();
        newMap.setEdit(Boolean.TRUE);
        Main.map_edit.put(map,true);
        newMap.setLocX(0);
        newMap.setLocY(1);
        newMap.setLocZ(0);
        Main.map_spawn.put(map,new Location(Bukkit.getWorld(map),0,1,0));
        newMap.setName(map);
        newMap.setVersion("0");
        Main.map_version.put(map,"0");
        m.getDatabase().insert(newMap);
        if(sender instanceof Player)  
            try {
                new MessageQueue().addText(map).setColor(ChatColor.GOLD).addText(" wartet darauf gebaut zu werden! ").setColor(ChatColor.DARK_PURPLE).addText("tp mich hin").setColor(ChatColor.GOLD).setShowTextOnHoverEvent("goto "+map).setRunOnClickEvent("/build map goto "+map).sendToChat((Player)sender);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException ex) {
                Logger.getLogger(BuildCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        else{
            sender.sendMessage(ChatColor.GOLD+map+ChatColor.DARK_PURPLE+" erstellt");
        }
    }
}
