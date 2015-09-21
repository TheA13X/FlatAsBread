package saul.de.samu.BuildSystem;

import com.avaje.ebean.EbeanServer;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import saul.Main;
import saul.MultiMapTable.ValuePair;
import saul.inc.a13xis.flatasbread.model.Map;

public class MapTable {
	static EbeanServer base;
        public static void initTable(EbeanServer b){
            base=b;
            String name;
            Location spawn;
            boolean edit;
            String version;
            Iterator<Map> result_map = base.find(Map.class).findList().iterator();
            while (result_map.hasNext()) {
                Map map=result_map.next();
                name = map.getName();
                spawn = new Location(Bukkit.getWorld(map.getName()),map.getLocX(),map.getLocY(),map.getLocZ());
                edit = map.getEdit();
                version = map.getVersion();
                Main.map_table.addRow(new ValuePair("name",name),new ValuePair("edit",edit),new ValuePair("version",version),new ValuePair("spawn",spawn));
            } 
        }
}
