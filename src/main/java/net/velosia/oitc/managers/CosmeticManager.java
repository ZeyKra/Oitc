package net.velosia.oitc.managers;

import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.util.DynamicLocation;
import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.ECosmetic;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CosmeticManager {

    public static void summon(ECosmetic cosmetic, Player player) {

        switch (cosmetic)  {
            case TETE_DE_FLAME:
                BleedEffect bleedEffect = new BleedEffect(Oitc.effectManager);
                bleedEffect.setEntity(player);

                //bleedEffect.offset = new Vector(-1, -1 ,-1);
                bleedEffect.hurt = false;
                bleedEffect.particleCount = 40;
                bleedEffect.iterations = 0;
                bleedEffect.height = 0;

                bleedEffect.updateLocations = false;
                bleedEffect.updateDirections = false;
                // Add a callback to the effect
                bleedEffect.callback = () -> {
                    player.sendMessage("finis de saigné?");
                };
                System.out.println(bleedEffect.height + " : " + bleedEffect.getLocation() + " : " + bleedEffect.updateLocations + " : " + bleedEffect.relativeOffset);
                // Bleeding takes 15 seconds
                // period * iterations = time of effect
                bleedEffect.start();

        }

    }
}
