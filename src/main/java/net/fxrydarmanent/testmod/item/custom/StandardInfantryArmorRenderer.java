package net.fxrydarmanent.testmod.item.custom;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class StandardInfantryArmorRenderer extends GeoArmorRenderer<StandardInfantryArmorItem> {
    public StandardInfantryArmorRenderer() {
        super(new StandardInfantryArmorModel());
    }
}
