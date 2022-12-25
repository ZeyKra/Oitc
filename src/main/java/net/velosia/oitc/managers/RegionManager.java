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

    private static Map<Region, Cuboid> RegionMap = new HashMap<>();
    public static int VoidRunnableID;
    @Deprecated
    public static void initVoidRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                VoidRunnableID = this.getTaskId();
                for(Map.Entry<Region, Cuboid> entry : RegionMap.entrySet()) {
                    System.out.println();

                }

            }
        }.runTaskTimerAsynchronously(Oitc.Instance, 0, 2);
    }

    public static void generateCuboids() {
        Yaml config = Yaml.CONFIG;

        config.setConfigSection("region.spawn-void");
        RegionMap.put(Region.SPAWN, new Cuboid(config.getLoc("pos1"), config.getLoc("pos2")));

        config.resetConfigSection();

        config.setConfigSection("region.game-void");
        RegionMap.put(Region.GAME, new Cuboid(config.getLoc("pos1"), config.getLoc("pos2")));
        //System.out.println("loc" + config.getLoc("pos1") + " : " +  config.getLoc("pos2"));
        config.resetConfigSection();
    }

    public static boolean checkIn(Location location, Cuboid region) {
        return region.contains(location);
    }

    public static Cuboid getRegionCuboid(Region aRegion) {
        return RegionMap.get(aRegion);
    }


}
