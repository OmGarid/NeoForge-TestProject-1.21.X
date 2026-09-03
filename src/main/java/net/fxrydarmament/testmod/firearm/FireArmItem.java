package net.fxrydarmament.testmod.firearm;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FireArmItem extends Item implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FireArmItem(Properties properties) {
        super(properties);
    }

    // Ambil ID item ini sendiri dari registry, misal "fxrydarmanent:torment_pz"
    public ResourceLocation getWeaponId() {
        return BuiltInRegistries.ITEM.getKey(this);
    }

    // Ambil data statistik senjata ini dari loader, berdasarkan ID di atas
    public FireArmData getWeaponData() {
        return FireArmDataLoader.get(this.getWeaponId().toString());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idle_controller", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("fxrydarmanent.tpz.idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}