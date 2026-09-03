package net.fxrydarmament.testmod.client;

import net.fxrydarmament.testmod.firearm.FireArmRenderer;
import net.fxrydarmament.testmod.item.ModItems;
import net.fxrydarmament.testmod.item.custom.HeavyPilotArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = "fxrydarmament", value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions heavyPilotArmorExtensions = new IClientItemExtensions() {
            private HeavyPilotArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new HeavyPilotArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        };

        event.registerItem(heavyPilotArmorExtensions, ModItems.HEAVY_PILOT_HELMET.get());
        event.registerItem(heavyPilotArmorExtensions, ModItems.HEAVY_PILOT_CHESTPLATE.get());
        event.registerItem(heavyPilotArmorExtensions, ModItems.HEAVY_PILOT_LEGGINGS.get());
        event.registerItem(heavyPilotArmorExtensions, ModItems.HEAVY_PILOT_BOOTS.get());

        //FireArm
        event.registerItem(new IClientItemExtensions() {
            private FireArmRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new FireArmRenderer();
                return this.renderer;
            }
        }, ModItems.TORMENT_PZ.get());
    }


        // Tambahin baris registerItem() lain di sini kalau ada item lain
        // yang butuh client extension (armor piece lain, senjata, dst)
}
