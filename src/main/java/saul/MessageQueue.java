package saul;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageQueue {
	StringBuilder s = new StringBuilder("[");
	
	private boolean	serialized	= false;
	private boolean	open		= false;
	
	public boolean isSerialized() {
		return serialized;
	}
	
	public MessageQueue addText(String str) {
		if (open) {
			this.closeText();
		}
		s = s.append("{\"text\":\"").append(str).append("\",");
		open = true;
		return this;
	}
	
	public MessageQueue setColor(ChatColor msc) {
		if (!open) {
			throw new IllegalArgumentException("No open text clause found");
		}
		s = s.append("\"color\":\"").append(msc.name().toLowerCase()).append("\",");
		return this;
	}
	
	public MessageQueue setShowTextOnHoverEvent(String str) {
		if (!open) {
			throw new IllegalArgumentException("No open text clause found");
		}
		s = s.append("\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"").append(str).append("\"},");
		return this;
	}
	
	public MessageQueue setRunOnClickEvent(String str) {
		if (!open) {
			throw new IllegalArgumentException("No open text clause found");
		}
		s = s.append("\"clickEvent\":{\"action\":\"run_command\",\"value\":\"").append(str).append("\"},");
		return this;
	}
	
	public MessageQueue setSuggestOnClickEvent(String str) {
		if (!open) {
			throw new IllegalArgumentException("No open text clause found");
		}
		s = s.append("\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"").append(str).append("\"},");
		return this;
	}
	
	public MessageQueue setURLOnClickEvent(String str) {
		if (!open) {
			throw new IllegalArgumentException("No open text clause found");
		}
		s = s.append("\"clickEvent\":{\"action\":\"open_url\",\"value\":\"").append(str).append("\"},");
		return this;
	}
	
	public MessageQueue closeText() {
		if (open) {
			String sub = s.substring(0, s.length() - 1);
			s = new StringBuilder(sub);
			s.append("},");
			open = false;
		}
		return this;
	}
	
	public void sendToChat(Player... ps) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, InstantiationException {
		if (open) {
			closeText();
		}
		String sub = s.substring(0, s.length() - 1);
		s = new StringBuilder(sub);
		s.append("]");
		Object serializedStr = NMSTools.serialize(s.toString());
		serialized = true;
		Class<?> CraftPlayer = Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".entity.CraftPlayer");
		Method getHandle = CraftPlayer.getMethod("getHandle");
		Class<?> packetClass = NMSTools.getNMSClass("PacketPlayOutChat");
		Constructor<?> packetConstructor = packetClass.getConstructor(NMSTools.getNMSClass("IChatBaseComponent"));
		for (Player p : ps) {
			Object nmsPlayer = getHandle.invoke(CraftPlayer.cast(p));
			Field conField = nmsPlayer.getClass().getField("playerConnection");
			Object con = conField.get(nmsPlayer);
			Object packet = packetConstructor.newInstance(serializedStr);
			Method sendPacket = NMSTools.getNMSClass("PlayerConnection").getMethod("sendPacket", NMSTools.getNMSClass("Packet"));
			sendPacket.invoke(con, packet);
		}
	}
}
