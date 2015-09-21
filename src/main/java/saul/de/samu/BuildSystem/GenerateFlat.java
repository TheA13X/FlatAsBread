package saul.de.samu.BuildSystem;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import com.avaje.ebean.EbeanServer;
import java.util.HashMap;

import saul.FABMain;
import saul.MySQLMethod;

public class GenerateFlat extends ChunkGenerator {
	
	private static EbeanServer		base;
	private final static byte[][][]	chunks	= new byte[4][][];
	
	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		int normX = Math.abs(chunkX % 6);
		int normZ = Math.abs(chunkZ % 6);
		if (normX == 0 && normZ == 0) {
			gsLogic(chunkX, chunkZ);
			return chunks[0];
		} else if (normZ == 0 && normX >= 1)
			return chunks[1];
		else if (normX == 0 && normZ >= 1)
			return chunks[2];
		else
			return chunks[3];
			
	}
	
	private void gsLogic(int x, int z) {
                if (x != 0 && z != 0) {
                        Location to=getToLocation(x/6,z/6);
                        Location from=getFromLocation(x/6,z/6);
                        int id=FABMain.gs_owner.size()+1;
                        if (index(from,FABMain.gs_from) >= 0 && index(from,FABMain.gs_to) >= 0) {
				return;
			}
			MySQLMethod.insert("INSERT INTO `flatsector`(`id`, `name`, `owner`, `secX`, `secY`, `biome`, `generated`) VALUES ("+id+",NULL,NULL,"+x/6+","+z/6+",\"mixed\",0)");
                        FABMain.gs_owner.put(id,null);
                        FABMain.gs_name.put(id,null);
                        FABMain.gs_biome.put(id,"mixed");
                        FABMain.gs_from.put(id,from);
                        FABMain.gs_to.put(id,to);
                        FABMain.gs_generated.put(id,false);
		}
	}
	
	public static void loadChunks(EbeanServer serverbase) {
		chunks[0] = chunk(0);
		chunks[1] = chunk(1);
		chunks[2] = chunk(2);
		chunks[3] = chunk(3);
		base = serverbase;
	}
	
	private static void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) {
			result[y >> 4] = new byte[4096];
		}
		result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
	}
	
	private static byte[][] chunk(int i) {
		byte[][] res = new byte[16][];
		int x;
		int y;
		int z;
		
		for (x = 0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				for (y = 0; y < 64; y++) {
					if (y == 0)
						setBlock(res, x, y, z, (byte) 7);
					else if (y < 53)
						setBlock(res, x, y, z, (byte) 1);
					else
						setBlock(res, x, y, z, (byte) 3);
				}
			}
		}
		switch (i) {
			case 0:
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						if (((x >= 0 && x <= 4) || (x >= 11 && x < 16)) && ((z >= 0 && z <= 4) || (z >= 11 && z < 16))) {
							setBlock(res, x, 64, z, (byte) 2);
							if ((x == 4 && z <= 4) || (z == 4 && x <= 4) || (x == 4 && z >= 11) || (z == 4 && x >= 11) || (x == 11 && z <= 4) || (z == 11 && x <= 4) || (x == 11 && z >= 11) || (z == 11 && x >= 11))
								setBlock(res, x, 65, z, (byte) 44);
						} else {
							int rand = new Random().nextInt(3);
							if (rand == 0)
								setBlock(res, x, 64, z, (byte) 4);
							else if (rand == 1)
								setBlock(res, x, 64, z, (byte) 13);
							else
								setBlock(res, x, 64, z, (byte) 98);
						}
					}
				}
				break;
			case 1:
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						if (z <= 4 || z >= 11) {
							setBlock(res, x, 64, z, (byte) 2);
							if ((z == 4) || z == 11)
								setBlock(res, x, 65, z, (byte) 44);
						} else {
							int rand = new Random().nextInt(3);
							if (rand == 0)
								setBlock(res, x, 64, z, (byte) 4);
							else if (rand == 1)
								setBlock(res, x, 64, z, (byte) 13);
							else
								setBlock(res, x, 64, z, (byte) 98);
						}
					}
				}
				break;
			case 2:
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						if (x <= 4 || x >= 11) {
							setBlock(res, x, 64, z, (byte) 2);
							if ((x == 4) || x == 11)
								setBlock(res, x, 65, z, (byte) 44);
						} else {
							int rand = new Random().nextInt(3);
							if (rand == 0)
								setBlock(res, x, 64, z, (byte) 4);
							else if (rand == 1)
								setBlock(res, x, 64, z, (byte) 13);
							else
								setBlock(res, x, 64, z, (byte) 98);;
						}
					}
				}
				break;
			default:
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						setBlock(res, x, 64, z, (byte) 2);
					}
				}
		}
		return res;
	}
        private Location getToLocation(int secx,int secy){
            Location to;
            if (Math.signum(secx) == 1) {
                if (Math.signum(secy) == 1) {
                        to = new Location(Bukkit.getWorld("flat"), 96 * secx + 3, 64, 96 * secy + 3);
                } else {
                        to = new Location(Bukkit.getWorld("flat"), 96 * secx + 3, 64, 96 * secy + 12);
                }
            } else {
                if (Math.signum(secy) == 1) {
                        to = new Location(Bukkit.getWorld("flat"), 96 * secx + 12, 64, 96 * secy + 3);
                } else {
                        to = new Location(Bukkit.getWorld("flat"), 96 * secx + 12, 64, 96 * secy + 12);
                }
            }
            return to;
        }
        private Location getFromLocation(int secx,int secy){
            Location from;
            if (Math.signum(secx) == 1) {
                    if (Math.signum(secy) == 1) {
                            from = new Location(Bukkit.getWorld("flat"), 96 * (secx - 1) + 12, 64, 96 * (secy - 1) + 12);
                    } else {
                            from = new Location(Bukkit.getWorld("flat"), 96 * (secx - 1) + 12, 64, 96 * (secy + 1) + 3);
                    }
            } else {
                    if (Math.signum(secy) == 1) {
                            from = new Location(Bukkit.getWorld("flat"), 96 * (secx + 1) + 3, 64, 96 * (secy - 1) + 12);
                    } else {
                            from = new Location(Bukkit.getWorld("flat"), 96 * (secx + 1) + 3, 64, 96 * (secy + 1) + 3);
                    }
            }
            return from;
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
