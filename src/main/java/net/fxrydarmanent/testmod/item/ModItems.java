package net.fxrydarmanent.testmod.item;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.fxrydarmanent.testmod.firearm.FireArmItem;
import net.fxrydarmanent.testmod.item.custom.HeavyPilotArmor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

//Item Register
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FXRYDArmanent.MOD_ID);


    //Register TUNGSTEN CARBIDE COMPOSITE
    public static final DeferredItem<Item> TUNGSTEN_CARBIDE_COMPOSITE = ITEMS.register("tungsten_carbide",
            () -> new Item(new Item.Properties()));

    //Register BORON CARBIDE COMPOSITE
    public static final DeferredItem<Item> BORON_CARBIDE_COMPOSITE = ITEMS.register("boron_carbide",
            () -> new Item(new Item.Properties()));

    //Register Scheelite Crystal
    public static final DeferredItem<Item> SCHEELITE_CRYSTAL = ITEMS.register("scheelite_crystal",
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



    //Firearms
    public static final DeferredItem<Item> TORMENT_PZ = ITEMS.registerItem("torment_pz",
            FireArmItem::new, new Item.Properties().stacksTo(1));




    //ARMOR ITEMS

    //Register STANDARD INFANTRY HELMET
    public static final DeferredItem<Item> HEAVY_PILOT_HELMET = ITEMS.register("heavy_pilot_helmet",
            () -> new HeavyPilotArmor(ModArmorMaterials.HEAVY_PILOT_ARMOR, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));

    //Register STANDARD INFANTRY CHESTPLATE
    public static final DeferredItem<Item> HEAVY_PILOT_CHESTPLATE = ITEMS.register("heavy_pilot_chestplate",
            () -> new HeavyPilotArmor(ModArmorMaterials.HEAVY_PILOT_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)));

    //Register STANDARD INFANTRY LEGGINGS
    public static final DeferredItem<Item> HEAVY_PILOT_LEGGINGS = ITEMS.register("heavy_pilot_leggings",
            () -> new HeavyPilotArmor(ModArmorMaterials.HEAVY_PILOT_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)));

    //Register STANDARD INFANTRY BOOTS
    public static final DeferredItem<Item> HEAVY_PILOT_BOOTS = ITEMS.register("heavy_pilot_boots",
            () -> new HeavyPilotArmor(ModArmorMaterials.HEAVY_PILOT_ARMOR, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)));





    //Item Registerer
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}