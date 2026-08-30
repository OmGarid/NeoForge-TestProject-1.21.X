package net.fxrydbasic.testmod.item;

import net.fxrydbasic.testmod.TestMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

//Item Register
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MOD_ID);

    //Register TUNGSTEN CARBIDE COMPOSITE
    public static final DeferredItem<Item> TUNGSTEN_CARBIDE = ITEMS.register("tungsten_carbide",
            () -> new Item(new Item.Properties()));
    //Register BORON CARBIDE COMPOSITE
    public static final DeferredItem<Item> BORON_CARBIDE = ITEMS.register("boron_carbide",
            () -> new Item(new Item.Properties()));

    //Item Registerer
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}