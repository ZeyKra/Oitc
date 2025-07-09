package net.velosia.oitc.managers;

import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.Region;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.util.Cuboid;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages regions and their associated cuboids for the OITC game.
 * Handles region generation, location checking, and void region monitoring.
 * 
 * @author ZeyKra
 */
public class RegionManager {

    /** Map storing all defined regions and their cuboids */
    private static Map<Region, Cuboid> regionMap = new HashMap<>();
    
    /** Task ID for the void checking runnable */
    public static int voidRunnableID;
    
    /**
     * Initializes a runnable for void region checking.
     * @deprecated This method should be removed in future versions
     */
    @Deprecated
    public static void initVoidRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                voidRunnableID = this.getTaskId();
                // TODO: Implement void region checking logic
                // This method is deprecated and should be removed in future versions
            }
        }.runTaskTimerAsynchronously(Oitc.instance, 0, 2);
    }

    /**
     * Generates cuboids for all configured regions from the configuration file.
     * Loads spawn and game void regions with null safety checks.
     */
    public static void generateCuboids() {
        Yaml config = Yaml.CONFIG;

        config.setConfigSection("region.spawn-void");
        Location pos1 = config.getLoc("pos1");
        Location pos2 = config.getLoc("pos2");
        if (pos1 != null && pos2 != null) {
            regionMap.put(Region.SPAWN, new Cuboid(pos1, pos2));
        }

        config.resetConfigSection();

        config.setConfigSection("region.game-void");
        pos1 = config.getLoc("pos1");
        pos2 = config.getLoc("pos2");
        if (pos1 != null && pos2 != null) {
            regionMap.put(Region.GAME, new Cuboid(pos1, pos2));
        }
        config.resetConfigSection();
    }

    /**
     * Checks if a location is within a specified region.
     * 
     * @param location The location to check
     * @param region The cuboid region to check against
     * @return true if the location is within the region, false otherwise
     */
    public static boolean checkIn(Location location, Cuboid region) {
        return region.contains(location);
    }

    /**
     * Retrieves the cuboid associated with a specific region.
     * 
     * @param aRegion The region to get the cuboid for
     * @return The cuboid for the specified region, or null if not found
     */
    public static Cuboid getRegionCuboid(Region aRegion) {
        return regionMap.get(aRegion);
    }


}
