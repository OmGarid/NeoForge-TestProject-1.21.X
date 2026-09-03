package net.fxrydarmament.testmod.firearm;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireArmModel extends GeoModel<FireArmItem> {

    @Override
    public ResourceLocation getModelResource(FireArmItem animatable) {
        ResourceLocation weaponId = animatable.getWeaponId();
        return ResourceLocation.fromNamespaceAndPath(weaponId.getNamespace(),
                "geo/firearm/" + weaponId.getPath() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireArmItem animatable) {
        ResourceLocation weaponId = animatable.getWeaponId();
        return ResourceLocation.fromNamespaceAndPath(weaponId.getNamespace(),
                "textures/firearm/" + weaponId.getPath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(FireArmItem animatable) {
        ResourceLocation weaponId = animatable.getWeaponId();
        return ResourceLocation.fromNamespaceAndPath(weaponId.getNamespace(),
                "animations/firearm/" + weaponId.getPath() + ".animation.json");
    }
}