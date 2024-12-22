package io.github.shulej.createsifter.content.contraptions.components.sifter;

import com.google.gson.JsonObject;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.shulej.createsifter.CreateSifter;
import io.github.shulej.createsifter.content.contraptions.components.meshes.AdvancedBaseMesh;
import io.github.shulej.createsifter.content.contraptions.components.meshes.BaseMesh;
import io.github.shulej.createsifter.foundation.data.recipe.SiftingRecipeBuilder;
import io.github.shulej.createsifter.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SiftingRecipe extends AbstractCrushingRecipe {
	public NonNullList<ProcessingOutput> results;
	ItemStack meshStack;
	ItemStack siftableIngredientStack;
	private boolean waterlogged;
	private float minimumSpeed;
	private boolean advanced;

	public SiftingRecipe(SiftingRecipeBuilder.SiftingRecipeParams params) {
		super(ModRecipeTypes.SIFTING, params); //change recipe type
		this.processingDuration = params.processingDuration;
		this.ingredients = params.ingredients;
		this.results = params.results;
		this.id = params.id;
		this.meshStack = getMeshItemStack();
		this.advanced = isAdvancedMesh(this.meshStack);
		this.siftableIngredientStack = getSiftableItemStack();
		this.waterlogged = params.waterlogged;
		this.minimumSpeed = params.minimumSpeed;
	}

	@Override
	public SiftingRecipeSerializer getSerializer() {
		return ModRecipeTypes.SIFTING.getSerializer();
	}

	public boolean matches(Container inv, Level worldIn, boolean waterlogged, float speed, boolean advanced) {
		if (inv.isEmpty())
			return false;
		if(isWaterlogged() != waterlogged)
			return false;
		if(hasSpeedRequirement() && speed < minimumSpeed)
			return false;
		if(advanced && meshStack.getItem() instanceof BaseMesh){
			return false;
		}
		return getSiftableIngredient().test(inv.getItem(0)) && getMeshIngredient().test(inv.getItem(1));
	}

	@Override
	public String toString() {
		return CreateSifter.MODID + ":sifting";
	}
	@Override
	protected int getMaxInputCount() {
		return 4;
	}

	@Override
	protected int getMaxOutputCount() {
		return SifterConfig.SIFTER_OUTPUT_CAPACITY.get();
	}

	@Override
	public boolean matches(Container inv, Level worldIn) {
		return matches(inv, worldIn, false, 0, false);
	}

	public Ingredient getSiftableIngredient(){
		for(int i = 0; i < ingredients.size();i++){
			ItemStack itemStack = ingredients.get(i).getItems()[0];
			if(!SiftingRecipe.isMeshItemStack(itemStack))
				return ingredients.get(i);
		}
		return Ingredient.EMPTY;

	}
	public Ingredient getMeshIngredient(){
		for(int i = 0; i < ingredients.size();i++){
			ItemStack itemStack = ingredients.get(i).getItems()[0];
			if(SiftingRecipe.isMeshItemStack(itemStack))
				return ingredients.get(i);
		}
		return Ingredient.EMPTY;
	}

	public ItemStack getMeshItemStack() {
		for (int i = 0; i < ingredients.size(); i++) {
			ItemStack itemStack = ingredients.get(i).getItems()[0];
			if (SiftingRecipe.isMeshItemStack(itemStack)) {
				return itemStack;
			}
		}
		return ItemStack.EMPTY;
	}

	public ItemStack getSiftableItemStack(){
		for(int i = 0; i < ingredients.size();i++){
			ItemStack itemStack = ingredients.get(i).getItems()[0];
			if(!SiftingRecipe.isMeshItemStack(itemStack))
				return itemStack;
		}
		return ItemStack.EMPTY;
	}

	public static boolean isMeshItemStack(ItemStack itemStack){
		if(itemStack.getItem() instanceof BaseMesh || itemStack.getItem() instanceof AdvancedBaseMesh)
			return true;
		return false;
	}
	private boolean isAdvancedMesh(ItemStack meshStack){
		return this.meshStack.getItem() instanceof AdvancedBaseMesh;
	}

	public boolean isWaterlogged() {
		return waterlogged;
	}

	public boolean hasSpeedRequirement(){
		if(this.minimumSpeed > SifterBlockEntity.DEFAULT_MINIMUM_SPEED){
			return true;
		}
		return false;
	}

	public boolean requiresAdvancedMesh(){
		return advanced;
	}

	@Override
	public void readAdditional(JsonObject json) {
		super.readAdditional(json);
		waterlogged = GsonHelper.getAsBoolean(json, "waterlogged", false);
		minimumSpeed = GsonHelper.getAsFloat(json, "minimumSpeed", 1);
	}

	@Override
	public void writeAdditional(JsonObject json) {
		super.writeAdditional(json);
		if (waterlogged)
			json.addProperty("waterlogged", waterlogged);
		if(hasSpeedRequirement()){
			json.addProperty("minimumSpeed", minimumSpeed);
		}
	}

	@Override
	public void readAdditional(FriendlyByteBuf buffer) {
		super.readAdditional(buffer);
		waterlogged = buffer.readBoolean();
		minimumSpeed = buffer.readFloat();
	}

	@Override
	public void writeAdditional(FriendlyByteBuf buffer) {
		super.writeAdditional(buffer);
		buffer.writeBoolean(waterlogged);
		buffer.writeFloat(minimumSpeed);
	}
	public float getSpeedRequirement(){
		return this.minimumSpeed;
	}

	public List<ProcessingOutput> getRollableResults() {
		return results;
	}
	public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
		return super.rollResults(rollableResults);
	}
	public List<ItemStack> rollResults() {
		return rollResults(this.getRollableResults());
	}

	public static boolean canHandSift(Level world, ItemStack stack, ItemStack mesh, boolean waterlogged) {
		return getMatchingInHandRecipes(world, stack, mesh, waterlogged, 0);
	}

	public static List<ItemStack> applyHandSift(Level world, Vec3 position, ItemStack stack, ItemStack mesh, boolean waterlogged) {
		ItemStackHandlerContainer tester = new ItemStackHandlerContainer(2);
		tester.setStackInSlot(0, stack);
		tester.setStackInSlot(1, mesh);
		Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(tester, world, waterlogged, 0);

		if (recipe.isPresent())
			return recipe.get().rollResults();
		return Collections.singletonList(stack);
	}

	public static boolean getMatchingInHandRecipes(Level world, ItemStack stack, ItemStack mesh, boolean waterlogged, float speed) {
		ItemStackHandlerContainer tester = new ItemStackHandlerContainer(2);
		tester.setStackInSlot(0, stack);
		tester.setStackInSlot(1, mesh);

		return ModRecipeTypes.SIFTING.find( tester, world, waterlogged, speed).isPresent();
	}

	public float getMinimumSpeed() {
		return minimumSpeed;
	}
}
