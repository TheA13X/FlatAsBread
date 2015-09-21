package saul.inc.a13xis.flatasbread;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;


import saul.FABMain;
import saul.MySQLMethod;

public class GSTable {
	
	public static void initTable() throws SQLException {
		int id;
		Location from;
		Location to;
		boolean generated;
		ResultSet result_fls = MySQLMethod.select("SELECT * FROM flatsector");
		while (result_fls.next()) {
			id = result_fls.getInt("id");
                        FABMain.gs_name.put(id,result_fls.getString("name"));
			FABMain.gs_owner.put(id,result_fls.getString("owner"));
			int secx = result_fls.getInt("secX");
			int secy = result_fls.getInt("secY");
			if (Math.signum(secx) == 1) {
				if (Math.signum(secy) == 1) {
					to = new Location(Bukkit.getWorld("flat"), 96 * secx + 3, 64, 96 * secy + 3);
					from = new Location(Bukkit.getWorld("flat"), 96 * (secx - 1) + 12, 64, 96 * (secy - 1) + 12);
				} else {
					to = new Location(Bukkit.getWorld("flat"), 96 * secx + 3, 64, 96 * secy + 12);
					from = new Location(Bukkit.getWorld("flat"), 96 * (secx - 1) + 12, 64, 96 * (secy + 1) + 3);
				}
			} else {
				if (Math.signum(secy) == 1) {
					to = new Location(Bukkit.getWorld("flat"), 96 * secx + 12, 64, 96 * secy + 3);
					from = new Location(Bukkit.getWorld("flat"), 96 * (secx + 1) + 3, 64, 96 * (secy - 1) + 12);
				} else {
					to = new Location(Bukkit.getWorld("flat"), 96 * secx + 12, 64, 96 * secy + 12);
					from = new Location(Bukkit.getWorld("flat"), 96 * (secx + 1) + 3, 64, 96 * (secy + 1) + 3);
				}
			}
			FABMain.gs_generated.put(id,result_fls.getBoolean("generated"));
			FABMain.gs_biome.put(id,result_fls.getString("biome"));
                        FABMain.gs_to.put(id,to);
                        FABMain.gs_from.put(id,from);
		}
	}
}
