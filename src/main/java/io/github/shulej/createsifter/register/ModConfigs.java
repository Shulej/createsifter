package io.github.shulej.createsifter.register;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import com.electronwill.nightconfig.core.io.WritingMode;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import io.github.shulej.createsifter.CreateSifter;
import io.github.shulej.createsifter.content.contraptions.components.brass_sifter.BrassSifterConfig;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ModConfigs {
	public static ForgeConfigSpec COMMON;
	public static void register() {
		registerServerConfigs();
		registerCommonConfigs();
		registerClientConfigs();
	}
	private static void registerClientConfigs() {
		ForgeConfigSpec.Builder	CLIENT_BUILDER = new ForgeConfigSpec.Builder();
		SifterConfig.registerClientConfig(CLIENT_BUILDER);
		BrassSifterConfig.registerClientConfig(CLIENT_BUILDER);
		ForgeConfigRegistry.INSTANCE.register(CreateSifter.MODID, ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
	}

	private static void registerCommonConfigs() {
		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
		SifterConfig.registerCommonConfig(COMMON_BUILDER);
		BrassSifterConfig.registerCommonConfig(COMMON_BUILDER);
		COMMON = COMMON_BUILDER.build();
		ForgeConfigRegistry.INSTANCE.register(CreateSifter.MODID, ModConfig.Type.COMMON, COMMON);
		ModConfigs.loadConfig(COMMON, FabricLoader.getInstance().getConfigDir().resolve(CreateSifter.MODID + "-common.toml"));
	}

	private static void registerServerConfigs() {
		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

		ForgeConfigRegistry.INSTANCE.register(CreateSifter.MODID, ModConfig.Type.SERVER, SERVER_BUILDER.build());
	}

	public static void loadConfig(ForgeConfigSpec spec, java.nio.file.Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		configData.load();
		spec.setConfig(configData);
	}
}
