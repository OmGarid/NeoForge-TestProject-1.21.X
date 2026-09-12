package net.fxrydarmament.testmod.client;

import net.fxrydarmament.testmod.firearm.FireArmDataLoader;
import net.fxrydarmament.testmod.firearm.network.FireRequestPayload;
import net.fxrydarmament.testmod.firearm.FireArmData;
import net.fxrydarmament.testmod.firearm.FireArmItem;
import net.fxrydarmament.testmod.firearm.component.FireArmState;
import net.fxrydarmament.testmod.firearm.component.FireArmComponents;
import net.fxrydarmament.testmod.firearm.network.ReloadRequestPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import software.bernie.geckolib.animatable.GeoItem;

// Keybinds
import static net.fxrydarmament.testmod.client.ClientEvents.RELOAD_KEY;
import static net.fxrydarmament.testmod.client.ClientEvents.FIRE_KEY;

@EventBusSubscriber(modid = "fxrydarmament", value = Dist.CLIENT)
public class FireArmInputHandler {

    private static boolean wasAttackDown = false;
    private static int fireCooldownTicks = 0;
    private static int reloadTicks = 0;


    @SubscribeEvent
    public static void onInteractionKeyMapping(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        ItemStack stack = minecraft.player.getMainHandItem();

        if (stack.getItem() instanceof FireArmItem) {
            if (event.isAttack()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        ItemStack heldStack = minecraft.player.getMainHandItem();

        if (!(heldStack.getItem() instanceof FireArmItem fireArmItem)) {
            wasAttackDown = false;
            fireCooldownTicks = 0;
            reloadTicks = 0;
            return;
        }

        boolean isAttackDown = FIRE_KEY.get().isDown();

        // Firing Ticks Decrement
        if (fireCooldownTicks > 0) {
            fireCooldownTicks--;
        }

        ResourceLocation weaponId =
                heldStack.get(FireArmComponents.FIREARM_ID.get());

        FireArmData data = weaponId != null
                ? FireArmDataLoader.get(weaponId)
                : null;

        FireArmState state =
                heldStack.get(FireArmComponents.FIREARM_STATE.get());

        if (data == null || state == null) {
            wasAttackDown = isAttackDown;
            return;
        }

        boolean isAutoMode = "auto".equals(state.currentFireMode());
        boolean shouldFire;

        // Full Auto Detection
        if (isAutoMode) {
            shouldFire = isAttackDown;
        } else {
            shouldFire = isAttackDown && !wasAttackDown;
        }

        // ReloadTicks Decrement
        if (reloadTicks > 0) {
            reloadTicks--;
        }

        // Reload
        if (RELOAD_KEY.get().consumeClick() && reloadTicks <= 0) {
            triggerReload(fireArmItem, heldStack, data);
        }

        // Firing
        if (shouldFire && fireCooldownTicks <= 0 && reloadTicks <= 0) {
            triggerFire(fireArmItem, heldStack, data);
            fireCooldownTicks = ticksFromFireRate(data.getFireRate());
        }


        wasAttackDown = isAttackDown;

        // Status Reset
        if (reloadTicks > 0) {
            wasAttackDown = isAttackDown;
        }
    }


    // Trigger ClientSide Events when Firing
    private static void triggerFire(FireArmItem item, ItemStack stack, FireArmData data) {

        // Anim Player
        item.triggerAnim(
                Minecraft.getInstance().player,
                GeoItem.getId(stack),
                "idle_controller",
                "shoot"
        );


        net.neoforged.neoforge.network.PacketDistributor.sendToServer(
                new FireRequestPayload()    // Send Payload to Server Side
        );
    }

    // Trigger ClientSide Events when Reloading
    private static void triggerReload(FireArmItem item, ItemStack stack, FireArmData data) {
        FireArmState state = stack.get(FireArmComponents.FIREARM_STATE.get()); //Get Firearm State
        // Detect Mag Ammo
        if (state.currentAmmo() == 0) {
            reloadTicks = data.getReloadEmptyTime();

            // Anim Player
            item.triggerAnim(
                    Minecraft.getInstance().player,
                    GeoItem.getId(stack),
                    "idle_controller",
                    "reloadempty"
            );
        } else {
            reloadTicks = data.getReloadTime();

            // Anim Player
            item.triggerAnim(
                    Minecraft.getInstance().player,
                    GeoItem.getId(stack),
                    "idle_controller",
                    "reload"
            );
        }

        net.neoforged.neoforge.network.PacketDistributor.sendToServer(
                new ReloadRequestPayload()  // Send Payload to Server Side
        );
    }

    // This Method convert Firerate to Cooldown time in Ticks
    private static int ticksFromFireRate(int roundsPerMinute) {
        if (roundsPerMinute <= 0) {
            return 20; // Default fallback if cant read the firerate, set to 1 bullet per sec
        }
        int ticks = Math.round((20f * 60f) / roundsPerMinute);
        return Math.max(ticks, 1);
    }
}