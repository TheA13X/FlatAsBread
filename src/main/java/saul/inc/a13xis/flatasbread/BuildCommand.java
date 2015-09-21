package saul.inc.a13xis.flatasbread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import saul.FABMain;
import saul.MessageQueue;
import saul.MySQLMethod;

public class BuildCommand implements CommandExecutor {
	FABMain m;
	
	public BuildCommand(FABMain m) {
		this.m = m;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
// Sender ist Commandblock oder Konsole
		if (!(sender instanceof Player)) {
			accept(sender, args);
			return true;
		}
		
// Sender ist Spieler                
//  Todo:       Permissionpower
		if (sender.isOp()) {
			accept(sender, args);
			return true;
		}
		return false;
	}
	
	private void accept(CommandSender sender, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("list")) {
				int size = FABMain.gs_name.size();
				if (size == 0&&isNullOnly(Arrays.asList(FABMain.gs_owner.values().toArray(new String[FABMain.gs_owner.size()])))) {
					sender.sendMessage(ChatColor.DARK_PURPLE + "Es sind keine Grundstücke mit Besitzern registriert");
					return;
				}
				if (args.length == 1) {
					if (size > (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) {
						sender.sendMessage(ChatColor.DARK_PURPLE + "Liste aller Grundstücke auf der Flatwelt " + ChatColor.DARK_BLUE + "(Seite 1 von " + Integer.toString((int) (size / (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) + 1) + ")");
					} else {
						sender.sendMessage(ChatColor.DARK_PURPLE + "Liste aller Grundstücke auf der Flatwelt");
					}
					sender.sendMessage(ChatColor.GOLD + "ID owner name From: x  :y  :z    To: x  :y  :z    Biom Generiert?");
					int i = 0;
					while (i < (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) {
                                            int id=i+1;
                                            if (i > FABMain.gs_name.size() - 1)
                                                    break;
                                            else if(FABMain.gs_owner.get(id)==null){
                                                continue;
                                            }
                                            String name = FABMain.gs_name.get(id);
                                            String owner = FABMain.gs_owner.get(id);
                                            String biome = FABMain.gs_biome.get(id);
                                            Location from = FABMain.gs_from.get(id);
                                            Location to = FABMain.gs_to.get(id);
                                            boolean generated = FABMain.gs_generated.get(id);
                                            ChatColor gencol;
                                            if (generated)
                                                    gencol = ChatColor.GREEN;
                                            else
                                                    gencol = ChatColor.DARK_RED;
                                            sender.sendMessage(ChatColor.GOLD + Integer.toString(id) + ChatColor.DARK_PURPLE + " " + secureNullconv(owner) + " " + secureNullconv(name) + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                            i++;
					}
				} else if (args.length == 2 && isInt(args[1])) {
					int page = Integer.parseInt(args[1]);
					if (size > (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1) && (size / (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1) + 1) >= page) {
						sender.sendMessage(ChatColor.DARK_PURPLE + "Liste aller Maps auf dem Server " + ChatColor.DARK_BLUE + "(Seite " + Integer.toString(page) + " von " + Integer.toString((int) (size / (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) + 1) + ")");
						sender.sendMessage(ChatColor.GOLD + "ID owner name From: x  :y  :z    To: x  :y  :z    Biom Generiert?");
						int i = (page - 1) * (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1);
						while (i < page * (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) {
                                                    int id=i+1;
                                                if (i > FABMain.gs_name.size() - 1)
                                                        break;
                                                else if(FABMain.gs_owner.get(id)==null){
                                                    continue;
                                                }
                                                String name = FABMain.gs_name.get(id);
                                                String owner = FABMain.gs_owner.get(id);
                                                String biome = FABMain.gs_biome.get(id);
                                                Location from = FABMain.gs_from.get(id);
                                                Location to = FABMain.gs_to.get(id);
                                                boolean generated = FABMain.gs_generated.get(id);
                                                ChatColor gencol;
                                                if (generated)
                                                        gencol = ChatColor.GREEN;
                                                else
                                                        gencol = ChatColor.DARK_RED;
                                                sender.sendMessage(ChatColor.GOLD + Integer.toString(id) + ChatColor.DARK_PURPLE + " " + owner + " " + name + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                                i++;
						}
					} else {
						sender.sendMessage(ChatColor.DARK_PURPLE + "Liste aller Maps auf dem Server" + ChatColor.DARK_BLUE + "(Seite 1 von " + Integer.toString((int) (size / (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) + 1) + ")");
						sender.sendMessage(ChatColor.GOLD + "ID owner name From: x  :y  :z    To: x  :y  :z    Biom Generiert?");
						int i = 0;
						while (i < (ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT - 1)) {
							int id=i+1;
                                                        if (i > FABMain.gs_name.size() - 1)
                                                                break;
                                                        else if(FABMain.gs_owner.get(id)==null){
                                                            continue;
                                                        }
                                                        String name = FABMain.gs_name.get(id);
                                                        String owner = FABMain.gs_owner.get(id);
                                                        String biome = FABMain.gs_biome.get(id);
                                                        Location from = FABMain.gs_from.get(id);
                                                        Location to = FABMain.gs_to.get(id);
                                                        boolean generated = FABMain.gs_generated.get(id);
                                                        ChatColor gencol;
                                                        if (generated)
                                                                gencol = ChatColor.GREEN;
                                                        else
                                                                gencol = ChatColor.DARK_RED;
							sender.sendMessage(ChatColor.GOLD + Integer.toString(id) + ChatColor.DARK_PURPLE + " " + owner + " " + name + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
							i++;
						}
					}
				} else {
					sender.sendMessage("/gs list (page)");
				}
                        //-->!!sollte auch nur für den "Staff" verfügbar sein oder?!!<--//
			} else if (args[0].equalsIgnoreCase("info")) {
                            if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "keine Location gefunden. Bist du ein Spieler?");
					return;
                            }
                            if(args.length==1){
                                if(((Player)sender).getWorld().getName().equalsIgnoreCase("flat")){
                                  List<Location> from = Arrays.asList(FABMain.gs_from.values().toArray(new Location[FABMain.gs_from.size()]));
                                  List<Location> to=Arrays.asList(FABMain.gs_to.values().toArray(new Location[FABMain.gs_to.size()]));
                                  for(int i=0;i<from.size();i++){
                                    int id=i+1;
                                    if(ListenerGroup.isInLoc(((Player)sender).getLocation(), from.get(i), to.get(i))){
                                        String name = FABMain.gs_name.get(id);
                                        String owner = FABMain.gs_owner.get(id);
                                        String biome =  FABMain.gs_biome.get(id);
                                        boolean generated = FABMain.gs_generated.get(id);
                                        ChatColor gencol;
                                        if (generated)
                                                gencol = ChatColor.GREEN;
                                        else
                                                gencol = ChatColor.DARK_RED;
                                        sender.sendMessage(ChatColor.GOLD + Integer.toString(id) + ChatColor.DARK_PURPLE + " " + secureNullconv(owner) + " " + secureNullconv(name) + " From: " + Integer.toString(from.get(i).getBlockX()) + ":" + Integer.toString(from.get(i).getBlockY()) + ":" + Integer.toString(from.get(i).getBlockZ()) + " To: " + Integer.toString(to.get(i).getBlockX()) + ":" + Integer.toString(to.get(i).getBlockY()) + ":" + Integer.toString(to.get(i).getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                        return;
                                    }   
                                  }
                                  sender.sendMessage(ChatColor.RED + "Offenbar bist du nicht auf einem Grundstück");
                                }
                                else{
                                    sender.sendMessage(ChatColor.RED + "Wenn du nicht in der Flatwelt bist: /gs info <id/spieler/name>");
                                }
                            }
                            else if(args.length==2){
                                int id;
                                if(isInt(args[1])&&FABMain.gs_owner.size()>=(id=Integer.parseInt(args[1]))){
                                    String name = FABMain.gs_name.get(id);
                                    String owner = FABMain.gs_owner.get(id);
                                    String biome =  FABMain.gs_biome.get(id);
                                    Location from =  FABMain.gs_from.get(id);
                                    Location to =  FABMain.gs_from.get(id);
                                    boolean generated = FABMain.gs_generated.get(id);
                                    ChatColor gencol;
                                    if (generated)
                                            gencol = ChatColor.GREEN;
                                    else
                                            gencol = ChatColor.DARK_RED;
                                    sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.DARK_PURPLE + " " + secureNullconv(owner) + " " + secureNullconv(name) + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                }
                                else{
                                    String ipid;
                                    if(FABMain.gs_name.values().contains(args[1])){
                                        int gid = Arrays.asList(FABMain.gs_name.values().toArray(new String[FABMain.gs_name.size()])).indexOf(args[1])+1;
                                        String owner = FABMain.gs_owner.get(gid);
                                        String biome =  FABMain.gs_biome.get(gid);
                                        Location from =  FABMain.gs_from.get(gid);
                                        Location to =  FABMain.gs_from.get(gid);
                                        boolean generated = FABMain.gs_generated.get(gid);
                                        ChatColor gencol;
                                        if (generated)
                                                gencol = ChatColor.GREEN;
                                        else
                                                gencol = ChatColor.DARK_RED;
                                        sender.sendMessage(ChatColor.GOLD + Integer.toString(gid) + ChatColor.DARK_PURPLE + " " + secureNullconv(owner) + " " + args[1] + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                    }
                                    else if(FABMain.gs_owner.values().contains(args[1])){
                                        int gid = Arrays.asList(FABMain.gs_name.values().toArray(new String[FABMain.gs_name.size()])).indexOf(args[1])+1;
                                        String name = FABMain.gs_name.get(gid);
                                        String biome =  FABMain.gs_biome.get(gid);
                                        Location from =  FABMain.gs_from.get(gid);
                                        Location to =  FABMain.gs_from.get(gid);
                                        boolean generated = FABMain.gs_generated.get(gid);
                                        ChatColor gencol;
                                        if (generated)
                                                gencol = ChatColor.GREEN;
                                        else
                                                gencol = ChatColor.DARK_RED;
                                        sender.sendMessage(ChatColor.GOLD + Integer.toString(gid) + ChatColor.DARK_PURPLE + " " + m.getServer().getPlayer(UUID.fromString(args[1])).getName() + " " + secureNullconv(name) + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                    }
                                    else if ((ipid=m.getServer().getPlayer(args[1]).getUniqueId().toString())!=null&&FABMain.gs_owner.values().contains(ipid)){
                                        int gid = Arrays.asList(FABMain.gs_name.values().toArray(new String[FABMain.gs_name.size()])).indexOf(args[1])+1;
                                        String name = FABMain.gs_name.get(gid);
                                        String biome =  FABMain.gs_biome.get(gid);
                                        Location from =  FABMain.gs_from.get(gid);
                                        Location to =  FABMain.gs_from.get(gid);
                                        boolean generated = FABMain.gs_generated.get(gid);
                                        ChatColor gencol;
                                        if (generated)
                                                gencol = ChatColor.GREEN;
                                        else
                                                gencol = ChatColor.DARK_RED;
                                        sender.sendMessage(ChatColor.GOLD + Integer.toString(gid) + ChatColor.DARK_PURPLE + " " + args[1] + " " + secureNullconv(name) + " From: " + Integer.toString(from.getBlockX()) + ":" + Integer.toString(from.getBlockY()) + ":" + Integer.toString(from.getBlockZ()) + " To: " + Integer.toString(to.getBlockX()) + ":" + Integer.toString(to.getBlockY()) + ":" + Integer.toString(to.getBlockZ()) + " " + biome + " " + gencol + Boolean.toString(generated));
                                    }
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("claim")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "keine UniqueId gefunden. Bist du ein Spieler?");
					return;
				}
				if (FABMain.gs_owner.values().contains(((Player) sender).getUniqueId().toString())) {
					sender.sendMessage(ChatColor.RED + "Du besitzt schon ein Grundstück!");
				} else {
					int id = index(null,FABMain.gs_owner)+1;
					FABMain.gs_owner.put(id,((Player) sender).getUniqueId().toString());
					MySQLMethod.update("UPDATE flatsector SET owner = \""+((Player) sender).getUniqueId().toString()+"\" WHERE id="+id+";");
					try {
						new MessageQueue().addText("Dir wurde ein Grundstück zugewiesen! ").setColor(ChatColor.DARK_PURPLE).addText("teleport").setColor(ChatColor.GOLD).setSuggestOnClickEvent("/gs goto").setShowTextOnHoverEvent("goto").addText(" ").addText("benenne es um").setColor(ChatColor.GOLD).setSuggestOnClickEvent("/gs setname " + sender.getName() + "s-Grundstück").setShowTextOnHoverEvent("mit diesem Namen wird man sich später einfach hinporten können!")
								.sendToChat((Player) sender);
					} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException ex) {
						Logger.getLogger(BuildCommand.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
                        } else if (args[0].equalsIgnoreCase("leave")) {
				if (!(sender instanceof Player) || !FABMain.gs_owner.values().contains(((Player) sender).getUniqueId().toString())) {
					sender.sendMessage(ChatColor.RED + "Du besitzt kein Grundstück!");
				} else {
					int id = index(((Player) sender).getUniqueId().toString(),FABMain.gs_owner)+1;
					FABMain.gs_owner.put(id,null);
					MySQLMethod.update("UPDATE flatsector SET owner = NULL WHERE id="+id+";");
					sender.sendMessage(ChatColor.DARK_PURPLE + "Du hast dein Grundstück freigegeben. Alles was du gebaut hast bleibt.");
				}
			} else if (args[0].equalsIgnoreCase("goto")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage(ChatColor.RED + "keine Location gefunden. Bist du ein Spieler?");
					return;
				}
                                if(args.length==1){
                                    if(!FABMain.gs_owner.values().contains(((Player)sender).getUniqueId().toString())){
                                        sender.sendMessage(ChatColor.RED + "Du besitzt kein Grundstück");
                                    }
                                    else{
                                        int id=index(((Player)sender).getUniqueId().toString(),FABMain.gs_owner)+1;
                                        Location from = FABMain.gs_from.get(id);
                                        Location to = FABMain.gs_to.get(id);
                                        double compuX = Math.max(from.getX(), to.getX()) - ((Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX())) / 2);
                                        double compuZ = Math.max(from.getZ(), to.getZ()) - ((Math.max(from.getZ(), to.getZ()) - Math.min(from.getZ(), to.getZ())) / 2);
                                        double compuY = m.getServer().getWorld("flat").getHighestBlockYAt(new Location(m.getServer().getWorld("flat"), compuX+0.5, 65, compuZ+0.5));
                                        Location spawn = new Location(m.getServer().getWorld("flat"), compuX, compuY, compuZ);
                                        ((Player) sender).teleport(spawn);
                                    }
                                }
                                else if (args.length == 2) {
					if (isInt(args[1])) {
                                            //-->!!Nur für admins (und devs ;) )!!<--//
                                            int id;
                                                if(isInt(args[1])&&FABMain.gs_owner.size()>=(id=Integer.parseInt(args[1]))){
                                                Location from = FABMain.gs_from.get(id);
                                                Location to = FABMain.gs_to.get(id);
                                                double compuX = Math.max(from.getX(), to.getX()) - ((Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX())) / 2);
                                                double compuZ = Math.max(from.getZ(), to.getZ()) - ((Math.max(from.getZ(), to.getZ()) - Math.min(from.getZ(), to.getZ())) / 2);
                                                double compuY = m.getServer().getWorld("flat").getHighestBlockYAt(new Location(m.getServer().getWorld("flat"), compuX+0.5, 65, compuZ+0.5));
                                                Location spawn = new Location(m.getServer().getWorld("flat"), compuX, compuY, compuZ);
                                                ((Player) sender).teleport(spawn);
                                            }
					} else {
						if (args[1].equals("world")) {
                                                    ((Player) sender).teleport(m.getServer().getWorlds().get(0).getSpawnLocation());
						}
                                                else if (args[1].equals("spawn")) {
                                                    ((Player) sender).teleport(new Location(m.getServer().getWorld("flat"), 8.5, 65, 8.5));
                                                }
                                                else{
                                                    //-->!!Nur für admins (und devs ;) )!!<--//
                                                    int id;
                                                    if(m.getServer().getPlayer(args[1])==null||(id=(index(m.getServer().getPlayer(args[1]).getUniqueId().toString(),FABMain.gs_owner)+1))<1){
                                                        if((id=(index(args[1],FABMain.gs_name)+1))>0){
                                                            Location from = FABMain.gs_from.get(id);
                                                            Location to = FABMain.gs_to.get(id);
                                                            double compuX = Math.max(from.getX(), to.getX()) - ((Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX())) / 2);
                                                            double compuZ = Math.max(from.getZ(), to.getZ()) - ((Math.max(from.getZ(), to.getZ()) - Math.min(from.getZ(), to.getZ())) / 2);
                                                            double compuY = m.getServer().getWorld("flat").getHighestBlockYAt(new Location(m.getServer().getWorld("flat"), compuX+0.5, 65, compuZ+0.5));
                                                            Location spawn = new Location(m.getServer().getWorld("flat"), compuX, compuY, compuZ);
                                                            ((Player) sender).teleport(spawn);
                                                        }
                                                        else
                                                            sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.RED + " ist weder der Name eines Grundstücks noch ein Besitzer");
                                                    }
                                                    else{
                                                        Location from = FABMain.gs_from.get(id);
                                                        Location to = FABMain.gs_to.get(id);
                                                        double compuX = Math.max(from.getX(), to.getX()) - ((Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX())) / 2);
                                                        double compuZ = Math.max(from.getZ(), to.getZ()) - ((Math.max(from.getZ(), to.getZ()) - Math.min(from.getZ(), to.getZ())) / 2);
                                                        double compuY = m.getServer().getWorld("flat").getHighestBlockYAt(new Location(m.getServer().getWorld("flat"), compuX+0.5, 65, compuZ+0.5));
                                                        Location spawn = new Location(m.getServer().getWorld("flat"), compuX, compuY, compuZ);
                                                        ((Player) sender).teleport(spawn);
                                                    }
                                                }
					}
				} else {
					sender.sendMessage("/gs goto <id/name/besitzer>");
				}
			} else if (args[0].equalsIgnoreCase("setname")) {
				if (!(sender instanceof Player) || !FABMain.gs_owner.values().contains(((Player) sender).getUniqueId().toString())) {
					sender.sendMessage(ChatColor.RED + "Du besitzt kein Grundstück!");
				} else if (args.length == 2) {
					FABMain.gs_name.put(index(((Player) sender).getUniqueId().toString(),FABMain.gs_owner),args[1]);
					MySQLMethod.update("UPDATE flatsector SET name = \""+args[1].replace("\"", "\\\"").toUpperCase()+"\" WHERE owner = \""+((Player) sender).getUniqueId().toString()+"\";");
                                        sender.sendMessage(ChatColor.DARK_PURPLE + "dein Grundstück heisst jetzt " + ChatColor.GOLD + args[1]);
				} else {
					sender.sendMessage("/gs setname <neuerName>");
				}
			} else if (args[0].equalsIgnoreCase("generate")) {
			
			} else if (args[0].equalsIgnoreCase("setbiome")) {
				if (args.length == 2) {
					if (!(sender instanceof Player) || !FABMain.gs_owner.values().contains(((Player) sender).getUniqueId().toString())) {
						sender.sendMessage(ChatColor.RED + "Du besitzt kein Grundstück!");
					} else {
                                                int id = index(((Player) sender).getUniqueId().toString(),FABMain.gs_owner)+1;
						Location from = FABMain.gs_from.get(id);
						Location to = FABMain.gs_to.get(id);
						Biome biome;
						try {
							biome = Biome.valueOf(args[1].toUpperCase());
						} catch (IllegalArgumentException ex) {
							sender.sendMessage(ChatColor.RED + "Dieser Biomename existiert nicht");
							return;
						}
                                                ArrayList<Chunk> refreshed = new ArrayList<>();
						for (int x = Math.min(from.getBlockX(), to.getBlockX())-1; x <= Math.max(from.getBlockX(), to.getBlockX())+1; x++) {
							for (int z = Math.min(from.getBlockZ(), to.getBlockZ())-1; z <= Math.max(from.getBlockZ(), to.getBlockZ())+1; z++) {
								m.getServer().getWorld("flat").setBiome(x, z, biome);
								Chunk current = m.getServer().getWorld("flat").getBlockAt(x, 65, z).getChunk();
								if (!refreshed.contains(current)) {
									m.getServer().getWorld("flat").refreshChunk(current.getX(), current.getZ());
									refreshed.add(current);
								}
							}
						}
						FABMain.gs_biome.put(id,args[1].toUpperCase());
						MySQLMethod.update("UPDATE flatsector SET biome = \""+args[1].replace("\"", "\\\"").toUpperCase()+"\" WHERE owner = \""+((Player) sender).getUniqueId().toString()+"\";");
                                                sender.sendMessage(ChatColor.DARK_PURPLE + "dein Grundstück ist jetzt " + ChatColor.GOLD + args[1]);
					}
				} else {
					sender.sendMessage("/gs setbiome <biome>");
				}
			} else {
				sender.sendMessage("/gs <list/claim/leave/goto/setname/generate/setbiome>");
			}
		} else {
			sender.sendMessage("/gs <list/claim/leave/goto/setname/generate/setbiome>");
		}
	}
	
	public static void copyPaste(File src, File dest) throws IOException {
		
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyPaste(srcFile, destFile);
			}
		} else {
			OutputStream out;
			try (InputStream in = new FileInputStream(src)) {
				out = new FileOutputStream(dest);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			}
			out.close();
		}
	}
	
	public static void recDelete(File src) {
		if (src.isDirectory()) {
			for (File file : src.listFiles()) {
				recDelete(file);
			}
			src.delete();
		} else {
			boolean succ = src.delete();
			if (!succ) {
				Logger.getLogger(BuildCommand.class.getName()).log(Level.WARNING, "{0} could not be deleted!", src.getName());
			}
		}
	}
	
	private boolean isInt(String arg) {
		try {
			Integer.parseInt(arg);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
        private boolean isUID(String arg){
            try {
                UUID.fromString(arg);
                return true;
            } catch (IllegalArgumentException ex) {
                    return false;
            }
        }
        
	private String secureNullconv(String arg) {
		if (arg == null) {
			return "null";
		} 
                else if(isUID(arg)){
                    return m.getServer().getPlayer(UUID.fromString(arg)).getName();
                }
                else {
			return arg;
		}
	}

    private boolean isNullOnly(List all) {
        for(Object o:all){
            if(o!=null){
                return false;
            }
        }
        return true;
    }
    
    public int index(Object where,HashMap hm){
          Object[] vals=hm.values().toArray(new Object[hm.values().size()]);
          for(int i=0;i<vals.length;i++){
              if(where==null&&vals[i]==null){
                  return i;
              }
              else if(vals[i]!=null&&vals[i].equals(where)){
                  return i;
              }
          }
          return -1;
    }
}
