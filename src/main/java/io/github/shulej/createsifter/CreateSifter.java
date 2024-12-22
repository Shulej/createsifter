package io.github.shulej.createsifter;

import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import io.github.shulej.createsifter.content.contraptions.components.brass_sifter.BrassSifterConfig;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterConfig;
import io.github.shulej.createsifter.register.ModBlockEntities;
import io.github.shulej.createsifter.register.ModBlocks;
import io.github.shulej.createsifter.register.ModConfigs;
import io.github.shulej.createsifter.register.ModCreativeTabs;
import io.github.shulej.createsifter.register.ModItems;
import io.github.shulej.createsifter.register.ModTags;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.ForgeConfigSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateSifter implements ModInitializer {
	public static final String MODID = "createsifter";
	public static final String NAME = "Create Sifter";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(CreateSifter.MODID);

	@Override
	public void onInitialize() {
		ModConfigs.register();

		ModBlocks.register();
		ModBlockEntities.register();
		ModTags.register();
		ModItems.register();
		ModCreativeTabs.register();
		ModRecipeTypes.register();
		REGISTRATE.register();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MODID, path);
	}
}
