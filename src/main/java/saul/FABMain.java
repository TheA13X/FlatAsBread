package saul;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import saul.de.samu.BuildSystem.GenerateFlat;
import saul.inc.a13xis.flatasbread.BuildCommand;
import saul.inc.a13xis.flatasbread.GSTable;
import saul.inc.a13xis.flatasbread.ListenerGroup;

public class FABMain extends JavaPlugin {
        
        public static final HashMap<Integer,String> gs_owner =  new HashMap<>();
        public static final HashMap<Integer,String> gs_name =  new HashMap<>();
        public static final HashMap<Integer,String> gs_biome =  new HashMap<>();
        public static final HashMap<Integer,Boolean> gs_generated =  new HashMap<>();
        public static final HashMap<Integer,Location> gs_from =  new HashMap<>();
        public static final HashMap<Integer,Location> gs_to =  new HashMap<>();
	
	BuildCommand	buildcommand;
	GenerateFlat	generateflat;
	
	@Override
	public void onEnable() {
		new File(getDataFolder().getPath() + "/worlds").mkdir();
		initDatabase();
		GenerateFlat.loadChunks(getDatabase());
		NMSTools.init(getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]);
                try {
                    GSTable.initTable();
                } catch (SQLException ex) {
                    Logger.getLogger(FABMain.class.getName()).log(Level.SEVERE, null, ex);
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
		WorldCreator worldCreator = new WorldCreator("flat");
		worldCreator.generator(new GenerateFlat()).createWorld();
                getServer().getWorld("flat").setSpawnFlags(false, true);
		getServer().getWorld("flat").setSpawnLocation(8, 65, 8);
                getCommand("gs").setExecutor(new BuildCommand(this));
                getServer().getPluginManager().registerEvents(new ListenerGroup(), this);
	}
        
	public void initDatabase() {
		MySQLMethod.open();
                MySQLMethod.create();
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new GenerateFlat();
	}
	
}

