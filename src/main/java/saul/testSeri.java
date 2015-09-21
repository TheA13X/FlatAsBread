/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saul;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import saul.MessageQueue.MessageColor;

/**
 *
 * @author alexis
 */
public class testSeri implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.DARK_RED+"Nein. Böse Konsole!");
            return true;
        }
        Player p=((Player)sender);
        if(args.length==1){
            MessageQueue qu= new MessageQueue();
            try {
                qu.addText("das ist ein Test ")
                .setColor(MessageColor.DARK_PURPLE)
                .addText("GO")
                .setColor(MessageColor.GOLD)
                .setRunOnClickEvent("/tp 0 100 0")
                .setShowTextOnHoverEvent("beam me up!")
                .closeText()
                .sendToChat(new Player[]{p});
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException ex) {
                Logger.getLogger(testSeri.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
    
}
