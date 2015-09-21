package saul.inc.a13xis.flatasbread;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class GenerateEmpty extends ChunkGenerator {

        @Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
		byte[][] result = new byte[world.getMaxHeight()/16][];
                int x;
                int y;
                int z;
                for (y = 0; y < world.getMaxHeight(); y++) {
                    for (x = 0; x < 16; x++) {
	    		for (z = 0; z < 16; z++) {
	    			setBlock(result, x, y, z, (byte)Material.AIR.getId());
	    		}
                    }
                }
                setBlock(result, 0,0,0,(byte)Material.GLOWSTONE.getId());
                return result;
	}
	
    void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid;
    }
}
