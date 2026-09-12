package net.fxrydarmament.testmod.firearm;

import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.fxrydarmament.testmod.firearm.component.FireArmComponents;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FireArmItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    public FireArmItem(Properties properties) {
        super(properties);
        software.bernie.geckolib.animatable.SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(
            AnimatableManager.ControllerRegistrar controllerRegistrar
    ) {
        AnimationController<FireArmItem> controller =
                new AnimationController<>(
                        this,
                        "idle_controller",
                        0,
                        state -> state.setAndContinue(
                                RawAnimation.begin()
                                        .thenLoop("idle")
                        )
                );

        controller.triggerableAnim(
                "shoot",
                RawAnimation.begin()
                        .thenPlay("shoot")
        );

        controller.triggerableAnim(
                "reload",
                RawAnimation.begin()
                        .thenPlay("reload")
        );

        controller.triggerableAnim(
                "reloadempty",
                RawAnimation.begin()
                        .thenPlay("reloadempty")
        );

        controllerRegistrar.add(controller);
    }

    @Override
    public Component getName(ItemStack stack) {

        ResourceLocation weaponId =
                stack.get(FireArmComponents.FIREARM_ID.get());

        if (weaponId != null) {
            FireArmData data =
                    FireArmDataLoader.get(weaponId);

            if (data != null) {
                return Component.literal(data.getWeaponName());
            }
        }

        return super.getName(stack);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}