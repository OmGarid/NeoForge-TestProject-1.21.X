package net.fxrydarmanent.testmod.item;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.fxrydarmanent.testmod.item.custom.StandardInfantryArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

//Item Register
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FXRYDArmanent.MOD_ID);


    //Register TUNGSTEN CARBIDE COMPOSITE
    public static final DeferredItem<Item> TUNGSTEN_CARBIDE = ITEMS.register("tungsten_carbide",
            () -> new Item(new Item.Properties()));

    //Register BORON CARBIDE COMPOSITE
    public static final DeferredItem<Item> BORON_CARBIDE = ITEMS.register("boron_carbide",
            () -> new Item(new Item.Properties()));

    //Register RAW WOLFRAMITE
    public static final DeferredItem<Item> RAW_WOLFRAMITE = ITEMS.register("raw_wolframite",
            () -> new Item(new Item.Properties()));

    //Register Scheelite Crystal
    public static final DeferredItem<Item> SCHEELITE_CRYSTAL = ITEMS.register("scheelite_crystal",
            () -> new Item(new Item.Properties()));

    //Register TUNGSTEN TRIOXIDE
    public static final DeferredItem<Item> TUNGSTEN_TRIOXIDE = ITEMS.register("tungsten_trioxide",
            () -> new Item(new Item.Properties()));

    //Register TUNGSTEN POWDER
    public static final DeferredItem<Item> TUNGSTEN_POWDER = ITEMS.register("tungsten_powder",
            () -> new Item(new Item.Properties()));

    //Register COAL POWDER
    public static final DeferredItem<Item> COAL_POWDER = ITEMS.register("coal_powder",
            () -> new Item(new Item.Properties()));

    //Register TUNGSTEN CARBIDE POWDER
    public static final DeferredItem<Item> TUNGSTEN_CARBIDE_POWDER = ITEMS.register("tungsten_carbide_powder",
            () -> new Item(new Item.Properties()));




    //ARMOR ITEMS

    //Register STANDARD INFANTRY HELMET
    public static final DeferredItem<Item> STANDARD_INFANTRY_HELMET = ITEMS.register("standard_infantry_helmet",
            () -> new StandardInfantryArmorItem(ModArmorMaterials.STANDARD_INFANTRY_ARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    //Register STANDARD INFANTRY CHESTPLATE
    public static final DeferredItem<Item> STANDARD_INFANTRY_CHESTPLATE = ITEMS.register("standard_infantry_chestplate",
            () -> new StandardInfantryArmorItem(ModArmorMaterials.STANDARD_INFANTRY_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    //Register STANDARD INFANTRY LEGGINGS
    public static final DeferredItem<Item> STANDARD_INFANTRY_LEGGINGS = ITEMS.register("standard_infantry_leggings",
            () -> new StandardInfantryArmorItem(ModArmorMaterials.STANDARD_INFANTRY_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    //Register STANDARD INFANTRY BOOTS
    public static final DeferredItem<Item> STANDARD_INFANTRY_BOOTS = ITEMS.register("standard_infantry_boots",
            () -> new StandardInfantryArmorItem(ModArmorMaterials.STANDARD_INFANTRY_ARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));





    //Item Registerer
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}