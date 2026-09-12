package net.fxrydarmament.testmod.firearm.network;

import net.fxrydarmament.testmod.firearm.FireArmData;
import net.fxrydarmament.testmod.firearm.FireArmDataLoader;
import net.fxrydarmament.testmod.firearm.FireArmItem;
import net.fxrydarmament.testmod.firearm.component.FireArmComponents;
import net.fxrydarmament.testmod.firearm.component.FireArmState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class FireArmNetworking {

    private static final String RELOAD_TICKS_TAG = "fxrydarmament_reload_ticks";

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(
                FireRequestPayload.TYPE,
                FireRequestPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        handleFireRequest((ServerPlayer) context.player()))
        );

        registrar.playToServer(
                ReloadRequestPayload.TYPE,
                ReloadRequestPayload.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() ->
                        startReload((ServerPlayer) context.player()))
        );
    }


    private static void handleFireRequest(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof FireArmItem fireArmItem)) {
            return;
        }

        ResourceLocation weaponId =
                stack.get(FireArmComponents.FIREARM_ID.get());

        if (weaponId == null) {
            return;
        }

        FireArmData data = FireArmDataLoader.get(weaponId);

        if (data == null) {
            return;
        }

        FireArmState state =
                stack.get(FireArmComponents.FIREARM_STATE.get());

        if (state == null) {
            return;
        }

        // Guard: ammo habis
        if (state.currentAmmo() <= 0) {
            return;
        }

        // Guard: gak boleh nembak selagi reload
        if (player.getPersistentData().contains(RELOAD_TICKS_TAG)) {
            return;
        }

        // Kurangin ammo
        stack.set(
                FireArmComponents.FIREARM_STATE.get(),
                state.withAmmo(state.currentAmmo() - 1)
        );

        // Raycast sederhana dari mata player
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        double range = 100.0;

        Vec3 end = start.add(look.scale(range));

        player.level().clip(new net.minecraft.world.level.ClipContext(
                start,
                end,
                net.minecraft.world.level.ClipContext.Block.OUTLINE,
                net.minecraft.world.level.ClipContext.Fluid.NONE,
                player
        ));

        // Cek entity di jalur
        EntityHitResult entityHit =
                findEntityHit(player, start, end);

        if (entityHit != null) {
            entityHit.getEntity().hurt(
                    player.damageSources().playerAttack(player),
                    data.getDamage()
            );
        }

        fireArmItem.triggerAnim(
                player,
                software.bernie.geckolib.animatable.GeoItem.getOrAssignId(stack, player.serverLevel()),
                "idle_controller",
                "shoot"
        );
    }

    // Dipanggil pas player pencet tombol reload (mulai proses reload)
    public static void startReload(ServerPlayer player) {
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof FireArmItem fireArmItem)) {
            return;
        }

        // Guard: jangan restart reload kalau lagi reload
        if (player.getPersistentData().contains(RELOAD_TICKS_TAG)) {
            return;
        }

        ResourceLocation weaponId =
                stack.get(FireArmComponents.FIREARM_ID.get());

        if (weaponId == null) {
            return;
        }

        FireArmData data = FireArmDataLoader.get(weaponId);

        if (data == null) {
            return;
        }

        FireArmState state =
                stack.get(FireArmComponents.FIREARM_STATE.get());

        if (state == null) {
            return;
        }

        int reloadDuration = state.currentAmmo() == 0
                ? data.getReloadEmptyTime()
                : data.getReloadTime();

        player.getPersistentData().putInt(RELOAD_TICKS_TAG, reloadDuration);

        // TAMBAHAN: broadcast animasi reload/reloadempty ke semua player
        fireArmItem.triggerAnim(
                player,
                software.bernie.geckolib.animatable.GeoItem.getOrAssignId(stack, player.serverLevel()),
                "idle_controller",
                state.currentAmmo() == 0 ? "reloadempty" : "reload"
        );
    }

    // Dipanggil tiap tick (dari FireArmServerEvents) buat countdown reload timer
    public static void tickReload(ServerPlayer player) {
        if (!player.getPersistentData().contains(RELOAD_TICKS_TAG)) {
            return;
        }

        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof FireArmItem)) {
            player.getPersistentData().remove(RELOAD_TICKS_TAG);
            return;
        }

        int ticks = player.getPersistentData()
                .getInt(RELOAD_TICKS_TAG);

        ticks--;

        if (ticks > 0) {
            player.getPersistentData().putInt(
                    RELOAD_TICKS_TAG,
                    ticks
            );
            return;
        }

        player.getPersistentData().remove(RELOAD_TICKS_TAG);

        ResourceLocation weaponId =
                stack.get(FireArmComponents.FIREARM_ID.get());

        if (weaponId == null) {
            return;
        }

        FireArmData data = FireArmDataLoader.get(weaponId);

        if (data == null) {
            return;
        }

        FireArmState state =
                stack.get(FireArmComponents.FIREARM_STATE.get());

        if (state == null) {
            return;
        }

        stack.set(
                FireArmComponents.FIREARM_STATE.get(),
                state.withAmmo(data.getMagazineCapacity())
        );
    }


    private static EntityHitResult findEntityHit(
            ServerPlayer player,
            Vec3 start,
            Vec3 end
    ) {
        return net.minecraft.world.entity.projectile.ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                start,
                end,
                player.getBoundingBox()
                        .expandTowards(player.getLookAngle().scale(100))
                        .inflate(1.0),
                entity -> !entity.isSpectator()
                        && entity.isPickable()
                        && entity != player
        );
    }
}