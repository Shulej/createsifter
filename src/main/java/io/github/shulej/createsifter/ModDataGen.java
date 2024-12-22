package io.github.shulej.createsifter;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.shulej.createsifter.foundation.data.recipe.ModProcessingRecipeGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class ModDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		Path createsifterResources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
		ExistingFileHelper helper = new ExistingFileHelper(
				Set.of(createsifterResources), Set.of("create"), false, null, null
		);
		FabricDataGenerator.Pack pack = generator.createPack();
		CreateSifter.REGISTRATE.setupDatagen(pack,  helper);
		pack.addProvider(ModProcessingRecipeGen::registerAll);
	}
}
