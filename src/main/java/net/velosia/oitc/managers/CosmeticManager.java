package net.velosia.oitc.managers;

import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.util.DynamicLocation;
import net.velosia.oitc.Oitc;
import net.velosia.oitc.enums.ECosmetic;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Manages cosmetic effects for players in the OITC game.
 * Handles the creation and management of visual effects using EffectLib.
 * 
 * @author ZeyKra
 */
public class CosmeticManager {

    /**
     * Summons a cosmetic effect for the specified player.
     * 
     * @param cosmetic The type of cosmetic effect to summon
     * @param player The player to apply the effect to
     */
    public static void summon(ECosmetic cosmetic, Player player) {
        if (player == null || cosmetic == null) {
            return;
        }

        switch (cosmetic)  {
            case TETE_DE_FLAME:
                try {
                    BleedEffect bleedEffect = new BleedEffect(Oitc.effectManager);
                    bleedEffect.setEntity(player);

                    bleedEffect.hurt = false;
                    bleedEffect.particleCount = 40;
                    bleedEffect.iterations = 0;
                    bleedEffect.height = 0;

                    bleedEffect.updateLocations = false;
                    bleedEffect.updateDirections = false;
                    
                    // Add a callback to the effect
                    bleedEffect.callback = () -> {
                        // Localize this message or remove if not needed
                        player.sendMessage("Bleeding effect finished");
                    };
                    
                    // Start the bleeding effect (takes 15 seconds)
                    // period * iterations = time of effect
                    bleedEffect.start();
                } catch (Exception e) {
                    Oitc.instance.getLogger().warning("Failed to create bleeding effect for player " + player.getName() + ": " + e.getMessage());
                }
                break;
        }
    }
}
