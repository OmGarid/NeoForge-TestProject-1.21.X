package net.fxrydarmament.testmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = "fxrydarmament", value = Dist.CLIENT)
public class CameraSyncHandler {

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {

        final float STRENGTH = 60.0f;

        // Ambil rotasi yang sudah di-update oleh FireArmRenderer
        float pitch = CameraBoneSync.getPitch(); // Rotasi X
        float yaw = CameraBoneSync.getYaw();     // Rotasi Y
        float roll = CameraBoneSync.getRoll();   // Rotasi Z

        // Tambahkan rotasi bone ke rotasi kamera player
        // Catatan: Anda mungkin perlu menyesuaikan multiplier atau axis tergantung
        // bagaimana bone 'camera' Anda diorientasikan di Blockbench
        event.setYaw(event.getYaw() + CameraBoneSync.getRotY() * STRENGTH);
        event.setPitch(event.getPitch() - CameraBoneSync.getRotX() * STRENGTH);
        event.setRoll(event.getRoll() + CameraBoneSync.getRotZ() * STRENGTH);
    }
}