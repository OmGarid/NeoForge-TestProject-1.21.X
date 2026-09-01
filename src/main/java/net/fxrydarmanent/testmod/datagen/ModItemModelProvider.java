package net.fxrydarmanent.testmod.datagen;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.fxrydarmanent.testmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FXRYDArmanent.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //Raw Materials
        basicItem(ModItems.SCHEELITE_CRYSTAL.get());
        basicItem(ModItems.RAW_WOLFRAMITE.get());
        basicItem(ModItems.TUNGSTEN_TRIOXIDE.get());
        basicItem(ModItems.COAL_POWDER.get());
        basicItem(ModItems.TUNGSTEN_POWDER.get());
        basicItem(ModItems.TUNGSTEN_CARBIDE_POWDER.get());

        //Ingots
        basicItem(ModItems.BORON_CARBIDE.get());
        basicItem(ModItems.TUNGSTEN_CARBIDE.get());

        //Armor Items
        basicItem(ModItems.STANDARD_INFANTRY_HELMET.get());
        basicItem(ModItems.STANDARD_INFANTRY_CHESTPLATE.get());
        basicItem(ModItems.STANDARD_INFANTRY_LEGGINGS.get());
        basicItem(ModItems.STANDARD_INFANTRY_BOOTS.get());
    }
}
