package net.fxrydarmanent.testmod;

import net.fxrydarmanent.testmod.block.ModBlocks;
import net.fxrydarmanent.testmod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CustomCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FXRYDArmanent.MOD_ID);

    //Items Tab
    public static final Supplier<CreativeModeTab> FXRYD_ITEMS_TAB = CREATIVE_MODE_TAB.register("fxryd_items_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TUNGSTEN_CARBIDE_COMPOSITE.get()))
            .title(Component.translatable("creativetab.fxrydarmanent.fxryd_items"))
            .displayItems((itemDisplayParameters, output) -> {

                //Items registered to this Tab:
                output.accept(ModItems.TUNGSTEN_CARBIDE_COMPOSITE);
                output.accept(ModItems.BORON_CARBIDE_COMPOSITE);
                output.accept(ModItems.SCHEELITE_CRYSTAL);
                output.accept(ModItems.TUNGSTEN_POWDER);
                output.accept(ModItems.COAL_POWDER);
                output.accept(ModItems.TUNGSTEN_CARBIDE_POWDER);


            })
            .build());

    //Firearms Tab
    public static final Supplier<CreativeModeTab> FXRYD_FIREARMS_TAB = CREATIVE_MODE_TAB.register("fxryd_firearms_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TORMENT_PZ.get()))
            .title(Component.translatable("creativetab.fxrydarmanent.fxryd_firearms"))
            .displayItems((itemDisplayParameters, output) -> {

                //Items registered to this Tab:
                output.accept(ModItems.TORMENT_PZ);


            })
            .build());

    //Armors Tab
    public static final Supplier<CreativeModeTab> FXRYD_ARMOR_TAB = CREATIVE_MODE_TAB.register("fxryd_armor_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HEAVY_PILOT_HELMET.get()))
            .title(Component.translatable("creativetab.fxrydarmanent.fxryd_armors"))
            .displayItems((itemDisplayParameters, output) -> {

                //Armor
                output.accept(ModItems.HEAVY_PILOT_HELMET);
                output.accept(ModItems.HEAVY_PILOT_CHESTPLATE);
                output.accept(ModItems.HEAVY_PILOT_LEGGINGS);
                output.accept(ModItems.HEAVY_PILOT_BOOTS);

            })
            .build());

    //Blocks Tab
    public static final Supplier<CreativeModeTab> FXRYD_BLOCKS_TAB = CREATIVE_MODE_TAB.register("fxryd_blocks_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.TUNGSTEN_CARBIDE_BLOCK.get()))
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(FXRYDArmanent.MOD_ID, "fxryd_items_tab"))
            .title(Component.translatable("creativetab.fxrydarmanent.fxryd_blocks"))
            .displayItems((itemDisplayParameters, output) -> {

                //Blocks registered to this Tab:
                output.accept(ModBlocks.TUNGSTEN_CARBIDE_BLOCK);
                output.accept(ModBlocks.BORON_CARBIDE_BLOCK);
                output.accept(ModBlocks.SCHEELITE_ORE);
                output.accept(ModBlocks.SCHEELITE_DEEPSLATE_ORE);

            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
