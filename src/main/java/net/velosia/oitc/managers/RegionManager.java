package net.velosia.oitc.managers;

import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.Region;
import net.velosia.oitc.enums.Yaml;
import net.velosia.oitc.util.Cuboid;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {

    private static Map<Region, Cuboid> regionMap = new HashMap<>();
    public static int voidRunnableID;
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

    public static boolean checkIn(Location location, Cuboid region) {
        return region.contains(location);
    }

    public static Cuboid getRegionCuboid(Region aRegion) {
        return regionMap.get(aRegion);
    }


}
