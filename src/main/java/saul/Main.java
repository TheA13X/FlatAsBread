package saul;

import saul.inc.a13xis.flatasbread.BuildCommand;
import com.avaje.ebean.EbeanServer;
import java.io.File;
import java.util.ArrayList;
import saul.de.samu.BuildSystem.GenerateFlat;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import saul.MultiMapTable.ValuePair;
import saul.inc.a13xis.flatasbread.GSTable;
import saul.inc.a13xis.flatasbread.model.FlatSector;

public class Main extends JavaPlugin {
    public enum SQLType{
        SQLITE,MYSQL;
        public static String pathByType(SQLType t){
            switch(t){
                case SQLITE:
                    return "org.sqlite.JDBC";
                case MYSQL:
                    return "com.mysql.jdbc.Driver";
                default:
                    return null;
                
            }
        }
    }
	private BeanBase base;
        public static final MultiMapTable map_table=new MultiMapTable(new ValuePair<Class>("name",String.class),new ValuePair<Class>("spawn",Location.class),new ValuePair<Class>("version",String.class),new ValuePair<Class>("edit",Boolean.class));
        public static final MultiMapTable gs_table=new MultiMapTable(new ValuePair<Class>("id",Integer.class),new ValuePair<Class>("name",String.class),new ValuePair<Class>("owner",String.class),new ValuePair<Class>("generated",Boolean.class),new ValuePair<Class>("biome",String.class),new ValuePair<Class>("from",Location.class),new ValuePair<Class>("to",Location.class));
	
	BuildCommand buildcommand;
	GenerateFlat generateflat;
	
        @Override
	public void onEnable() {
                new File(getDataFolder().getPath()+"/worlds").mkdir();
		getConfig().addDefault("sql.main.type", "mysql");
                getConfig().addDefault("sql.mysql.name", "root");
                getConfig().addDefault("sql.mysql.password", "rootkit");
                getConfig().addDefault("sql.mysql.base", "base");
                getConfig().addDefault("sql.main.location", "localhost");
                getConfig().addDefault("sql.main.rebuild", true);
                getConfig().addDefault("sql.main.allowedinput.type", Arrays.asList(new String[]{"mysql","sqlite"}));
                getConfig().options().copyDefaults(true);
                getConfig().set("sql.main.allowedinput.type", Arrays.asList(new String[]{"mysql","sqlite"}));
                saveConfig();
		initDatabase();
                GenerateFlat.loadChunks(getDatabase());
                WorldCreator worldCreator = new WorldCreator("flat");
		worldCreator.generator(new GenerateFlat()).createWorld();
                getServer().getWorld("flat").setSpawnLocation(7, 65, 8);
                Location mainspawn=getServer().getWorlds().get(0).getSpawnLocation();
                NMSTools.init(getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]);
                GSTable.initTable(getDatabase());
                getCommand("gs").setExecutor(new BuildCommand(this));
                
                
	}
	
        public void initDatabase(){
            base=new BeanBase(this){
                @Override
		protected List<Class<?>> getDatabaseClasses(){
			List<Class<?>> list;
                        list = new ArrayList<>();
                        list.add(FlatSector.class);
			return list;
		}
            };
            base.initializeDatabase(SQLType.pathByType(SQLType.valueOf(getConfig().getString("sql.main.type").toUpperCase())), "jdbc:mysql://"+getConfig().getString("sql.main.location")+"/"+getConfig().getString("sql.mysql.base"), getConfig().getString("sql.mysql.name"), getConfig().getString("sql.mysql.password"), "SERIALIZABLE", true, getConfig().getBoolean("sql.main.rebuild",false));
            getConfig().set("sql.main.rebuild",false);
            saveConfig();
        }
        
        @Override
        public EbeanServer getDatabase(){
            return base.getDatabase();
        }
        
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new GenerateFlat();
	}
	
}
