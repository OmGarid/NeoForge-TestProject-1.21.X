package net.fxrydarmanent.testmod.item.custom;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class StandardInfantryArmorModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable animatable) {
        return ResourceLocation.fromNamespaceAndPath(FXRYDArmanent.MOD_ID, "geo/armor/standard_infantry_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable animatable) {
        return ResourceLocation.fromNamespaceAndPath(FXRYDArmanent.MOD_ID, "textures/armor/standard_infantry_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable animatable) {
        return ResourceLocation.fromNamespaceAndPath(FXRYDArmanent.MOD_ID, "animations/armor/standard_infantry_armor.animation.json");
    }
}
