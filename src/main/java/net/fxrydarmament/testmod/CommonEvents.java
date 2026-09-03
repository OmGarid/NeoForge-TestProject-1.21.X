package net.fxrydarmament.testmod;

import net.fxrydarmament.testmod.firearm.FireArmDataLoader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

@EventBusSubscriber(modid = FXRYDArmament.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new FireArmDataLoader());
    }
}