package net.fxrydarmanent.testmod.datagen;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.fxrydarmanent.testmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FXRYDArmanent.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TUNGSTEN_CARBIDE_BLOCK.get())
                .add(ModBlocks.BORON_CARBIDE_BLOCK.get())
                .add(ModBlocks.SCHEELITE_ORE.get())
                .add(ModBlocks.SCHEELITE_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.TUNGSTEN_CARBIDE_BLOCK.get())
                .add(ModBlocks.BORON_CARBIDE_BLOCK.get())
                .add(ModBlocks.SCHEELITE_ORE.get())
                .add(ModBlocks.SCHEELITE_DEEPSLATE_ORE.get());


    }
}
