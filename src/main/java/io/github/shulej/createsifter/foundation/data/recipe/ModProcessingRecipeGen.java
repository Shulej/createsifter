package io.github.shulej.createsifter.foundation.data.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import io.github.shulej.createsifter.CreateSifter;
import io.github.shulej.createsifter.ModRecipeTypes;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SiftingRecipeSerializer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModProcessingRecipeGen extends CreateRecipeProvider {
	protected static final List<ModProcessingRecipeGen> GENERATORS = new ArrayList<>();

	public ModProcessingRecipeGen(FabricDataOutput generator) {
		super(generator);
	}

	public static DataProvider registerAll(FabricDataOutput output) {
		GENERATORS.add(new SiftingRecipeGen(output));

		return new DataProvider() {
			@Override
			public CompletableFuture<?> run(CachedOutput dc) {
				return CompletableFuture.allOf(GENERATORS.stream()
						.map(gen -> gen.run(dc))
						.toArray(CompletableFuture[]::new));
			}

			@Override
			public String getName() {
				return "Create Sifter's Processing Recipes";
			}
		};
	}

	protected GeneratedRecipe create(String namespace, Supplier<ItemLike> singleIngredient, UnaryOperator<SiftingRecipeBuilder> transform) {
		SiftingRecipeSerializer serializer = getSerializer();
		GeneratedRecipe generatedRecipe = c -> {
			ItemLike itemLike = singleIngredient.get();
			transform.apply(new SiftingRecipeBuilder(serializer.getFactory(), new ResourceLocation(namespace, RegisteredObjects.getKeyOrThrow(itemLike.asItem()).getPath()))).withItemIngredients(Ingredient.of(itemLike))
					.build(c);
		};
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	GeneratedRecipe create(Supplier<ItemLike> singleIngredient, UnaryOperator<SiftingRecipeBuilder> transform) {
		return create(CreateSifter.MODID, singleIngredient, transform);
	}

	protected GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name, UnaryOperator<SiftingRecipeBuilder> transform) {
		SiftingRecipeSerializer serializer = getSerializer();
		GeneratedRecipe generatedRecipe = c -> transform.apply(new SiftingRecipeBuilder(serializer.getFactory(), name.get())).build(c);
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	protected GeneratedRecipe create(ResourceLocation name, UnaryOperator<SiftingRecipeBuilder> transform) {
		return createWithDeferredId(() -> name, transform);
	}

	GeneratedRecipe create(String name, UnaryOperator<SiftingRecipeBuilder> transform) {
		return create(CreateSifter.asResource(name), transform);
	}

	protected IRecipeTypeInfo getRecipeType() {
		return ModRecipeTypes.SIFTING.getType();
	}

	protected SiftingRecipeSerializer getSerializer() {
		return getRecipeType().getSerializer();
	}

	protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
		return () -> {
			ResourceLocation registryName = RegisteredObjects.getKeyOrThrow(item.get().asItem());
			return CreateSifter.asResource(registryName.getPath() + suffix);
		};
	}

	@Override
	public String getName() {
		return "Create Sifter's Processing Recipes: " + getRecipeType().getId()
				.getPath();
	}
}
