package net.fxrydarmament.testmod.firearm;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireArmModel extends GeoModel<FireArmItem> {

    private ResourceLocation weaponId;

    public void setWeaponId(ResourceLocation weaponId) {
        this.weaponId = weaponId;
    }

    private ResourceLocation getWeaponId() {
        return weaponId;
    }

    @Override
    public ResourceLocation getModelResource(FireArmItem animatable) {

        ResourceLocation weaponId = getWeaponId();

        if (weaponId == null) {
            return ResourceLocation.fromNamespaceAndPath(
                    "fxrydarmament",
                    "geo/firearm/missing.geo.json"
            );
        }

        return ResourceLocation.fromNamespaceAndPath(
                weaponId.getNamespace(),
                "geo/firearm/" + weaponId.getPath() + ".geo.json"
        );
    }

    @Override
    public ResourceLocation getTextureResource(FireArmItem animatable) {

        ResourceLocation weaponId = getWeaponId();

        if (weaponId == null) {
            return ResourceLocation.fromNamespaceAndPath(
                    "fxrydarmament",
                    "textures/firearm/missing.png"
            );
        }

        return ResourceLocation.fromNamespaceAndPath(
                weaponId.getNamespace(),
                "textures/firearm/" + weaponId.getPath() + ".png"
        );
    }

    @Override
    public ResourceLocation getAnimationResource(FireArmItem animatable) {

        ResourceLocation weaponId = getWeaponId();

        if (weaponId == null) {
            return ResourceLocation.fromNamespaceAndPath(
                    "fxrydarmament",
                    "animations/firearm/missing.animation.json"
            );
        }

        return ResourceLocation.fromNamespaceAndPath(
                weaponId.getNamespace(),
                "animations/firearm/" + weaponId.getPath() + ".animation.json"
        );
    }
}