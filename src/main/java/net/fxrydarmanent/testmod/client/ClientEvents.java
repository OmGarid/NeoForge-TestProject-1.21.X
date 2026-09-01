package net.fxrydarmanent.testmod.client;

import net.fxrydarmanent.testmod.item.ModItems;
import net.fxrydarmanent.testmod.item.custom.StandardInfantryArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = "fxrydarmanent", value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions standardInfantryArmorExtensions = new IClientItemExtensions() {
            private StandardInfantryArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new StandardInfantryArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        };

        event.registerItem(standardInfantryArmorExtensions, ModItems.STANDARD_INFANTRY_HELMET.get());
        event.registerItem(standardInfantryArmorExtensions, ModItems.STANDARD_INFANTRY_CHESTPLATE.get());
        event.registerItem(standardInfantryArmorExtensions, ModItems.STANDARD_INFANTRY_LEGGINGS.get());
        event.registerItem(standardInfantryArmorExtensions, ModItems.STANDARD_INFANTRY_BOOTS.get());
    }

        // Tambahin baris registerItem() lain di sini kalau ada item lain
        // yang butuh client extension (armor piece lain, senjata, dst)
}
