package net.fxrydarmament.testmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
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
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "fxrydarmament", value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions heavyPilotArmorExtensions = new IClientItemExtensions() {
            private HeavyPilotArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(
                    LivingEntity livingEntity,
                    ItemStack itemStack,
                    EquipmentSlot equipmentSlot,
                    HumanoidModel<?> original
            ) {
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

            @Override
            public boolean applyForgeHandTransform(
                    PoseStack poseStack,
                    LocalPlayer player,
                    HumanoidArm arm,
                    ItemStack itemStack,
                    float partialTick,
                    float equipProcess,
                    float swingProcess
            ) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;

                poseStack.translate(
                        (float) i * 0.7F,
                        -0.52F,
                        -0.85F
                );

                return true;
            }

        }, ModItems.FIREARM.get());
    }


    /*
    *
    *  [ KEY MAPPING AREA ]
    *
    */

    // Fire/Shoot Key
    public static final Lazy<KeyMapping> FIRE_KEY = Lazy.of(() -> new KeyMapping(
            "key.fxrydarmament.fire",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_1,
            "key.categories.fxrydarmament"
    ));

    // Reload Key
    public static final Lazy<KeyMapping> RELOAD_KEY = Lazy.of(() -> new KeyMapping(
            "key.fxrydarmament.reload",
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_R,
            "key.categories.fxrydarmament"
    ));

    // Registers Keys
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_KEY.get());
        event.register(FIRE_KEY.get());
    }


    //ADD NEW EVENTS SOMEWHERE HERE
}
