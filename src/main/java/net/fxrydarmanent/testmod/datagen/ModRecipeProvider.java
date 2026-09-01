package net.fxrydarmanent.testmod.datagen;

import net.fxrydarmanent.testmod.FXRYDArmanent;
import net.fxrydarmanent.testmod.block.ModBlocks;
import net.fxrydarmanent.testmod.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> TUNGSTEN_TRIOXIDE_LIST = List.of(ModItems.SCHEELITE_CRYSTAL, ModItems.RAW_WOLFRAMITE, ModBlocks.SCHEELITE_ORE, ModBlocks.WOLFRAMITE_ORE, ModBlocks.SCHEELITE_DEEPSLATE_ORE, ModBlocks.WOLFRAMITE_DEEPSLATE_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BORON_CARBIDE_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BORON_CARBIDE.get())
                .unlockedBy("has_boron_carbide", has(ModItems.BORON_CARBIDE)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BORON_CARBIDE.get(), 9)
                .requires(ModBlocks.BORON_CARBIDE_BLOCK)
                .unlockedBy("has_boron_carbide_block", has(ModBlocks.BORON_CARBIDE_BLOCK)).save(recipeOutput);

        oreSmelting(recipeOutput, TUNGSTEN_TRIOXIDE_LIST, RecipeCategory.MISC, ModItems.TUNGSTEN_TRIOXIDE.get(), 0.25f, 200, "tungsten");
        oreBlasting(recipeOutput, TUNGSTEN_TRIOXIDE_LIST, RecipeCategory.MISC, ModItems.TUNGSTEN_TRIOXIDE.get(), 0.25f, 200, "tungsten");


    }
    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, FXRYDArmanent.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
