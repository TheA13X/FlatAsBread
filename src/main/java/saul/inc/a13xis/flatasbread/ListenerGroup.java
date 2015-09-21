/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saul.inc.a13xis.flatasbread;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import saul.FABMain;

/**
 *
 * @author alexis
 */
public class ListenerGroup implements Listener{
    @EventHandler
    public void onAct(PlayerInteractEvent ev){
        if(ev.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(ev.getMaterial().equals(Material.REDSTONE)||ev.getMaterial().equals(Material.REDSTONE_BLOCK)){
                ev.getPlayer().sendMessage(ChatColor.RED+"Redstone und Redstoneblöcke sind nicht erlaubt.");
                ev.setCancelled(true);
            }
            else if(ev.getClickedBlock().getType().equals(Material.TNT)&&ev.getMaterial().equals(Material.FLINT_AND_STEEL)){
                ev.getPlayer().sendMessage(ChatColor.RED+"Was hast du vor?");
                ev.setCancelled(true);
            }
            else if((ev.getMaterial().equals(Material.REDSTONE_TORCH_ON)||ev.getMaterial().equals(Material.REDSTONE_WIRE)||ev.getMaterial().equals(Material.STONE_BUTTON)||ev.getMaterial().equals(Material.LEVER)||ev.getMaterial().equals(Material.WOOD_BUTTON))&&!safe(Material.TNT,ev.getBlockFace(),ev.getClickedBlock().getLocation())){
                ev.getPlayer().sendMessage(ChatColor.RED+"Netter Versuch ;)");
                ev.setCancelled(true);
            }
            else if((ev.getMaterial().equals(Material.TNT))&&(!safe(Material.REDSTONE_TORCH_OFF,ev.getBlockFace(),ev.getClickedBlock().getLocation())||!safe(Material.REDSTONE_TORCH_ON,ev.getBlockFace(),ev.getClickedBlock().getLocation())||!safe(Material.REDSTONE_WIRE,ev.getBlockFace(),ev.getClickedBlock().getLocation())||!safe(Material.STONE_BUTTON,ev.getBlockFace(),ev.getClickedBlock().getLocation())||!safe(Material.LEVER,ev.getBlockFace(),ev.getClickedBlock().getLocation())||!safe(Material.WOOD_BUTTON,ev.getBlockFace(),ev.getClickedBlock().getLocation()))){
                ev.getPlayer().sendMessage(ChatColor.RED+"Netter Versuch ;)");
                ev.setCancelled(true);
            }
            else if(!isInGS(ev.getPlayer(),ev.getClickedBlock().getLocation())){
               ev.getPlayer().sendMessage(ChatColor.RED+"Geh weg, das ist nicht dein Grundstück!");
               ev.setCancelled(true);
            }
        }
        else if((ev.getAction().equals(Action.LEFT_CLICK_BLOCK)||ev.getAction().equals(Action.PHYSICAL))&&!isInGS(ev.getPlayer(),ev.getClickedBlock().getLocation())){
               ev.getPlayer().sendMessage(ChatColor.RED+"Geh weg, das ist nicht dein Grundstück!");
               ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotion(PotionSplashEvent pv){
        pv.setCancelled(true);
    }
    
    private boolean safe(Material type,BlockFace bf,Location loc){
        switch(bf){
            case NORTH:
                loc.add(0,0,-1);
                break;
            case EAST:
                loc.add(1,0,0);
                break;
            case SOUTH:
                loc.add(0,0,1);
                break;
            case WEST:
                loc.add(-1,0,0);
                break;
            case UP:
                loc.add(0,1,0);
                break;
            case DOWN:
                loc.add(0,-1,0);
                break;
            default:
                break;
        }
        boolean secure1=loc.add(1, 0, 1).getBlock().getType().equals(type);
        loc.subtract(1, 0, 1);
        boolean secure2=loc.add(0, 0, 1).getBlock().getType().equals(type);
        loc.subtract(0, 0, 1);
        boolean secure3=loc.add(-1, 0, 1).getBlock().getType().equals(type);
        loc.subtract(-1, 0, 1);
        boolean secure4=loc.add(1, 0, 0).getBlock().getType().equals(type);
        loc.subtract(1, 0, 0);
        boolean secure5=loc.add(-1, 0, 0).getBlock().getType().equals(type);
        loc.subtract(-1, 0, 0);
        boolean secure6=loc.add(1, 0, -1).getBlock().getType().equals(type);
        loc.subtract(1, 0, -1);
        boolean secure7=loc.add(0, 0, -1).getBlock().getType().equals(type);
        loc.subtract(0, 0, -1);
        boolean secure8=loc.add(-1, 0, -1).getBlock().getType().equals(type);
        loc.subtract(-1, 0, -1);
        boolean secure9=loc.add(0, -1, 0).getBlock().getType().equals(type);
        loc.subtract(0, -1, 0);
        boolean secure10=loc.add(0, 1, 0).getBlock().getType().equals(type);
        loc.subtract(0, 1, 0);
        return !(secure1||secure2||secure3||secure4||secure5||secure6||secure7||secure8||secure9||secure10);
    }
    private boolean isInGS(Player p,Location loc) {
       int id=index(p.getUniqueId().toString(),FABMain.gs_owner)+1;
       return loc.getWorld().getName().equals("flat")&&FABMain.gs_owner.values().contains(p.getUniqueId().toString())&&isInLoc(loc,FABMain.gs_from.get(id),FABMain.gs_to.get(id));
    }

    public static boolean isInLoc(Location loc, Location from, Location to) {
        return loc.getX()<=Math.max(from.getX(), to.getX())&&loc.getX()>=Math.min(from.getX(), to.getX())&&loc.getZ()<=Math.max(from.getZ(), to.getZ())&&loc.getZ()>=Math.min(from.getZ(), to.getZ());
    }

    public int index(Object where,HashMap hm){
        int i=0;
        for(Object val:hm.values()){
            if(where==null){if(val==null){
                return i;
            }}
            else if(val!=null&&val.equals(where)){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
      }
}   

