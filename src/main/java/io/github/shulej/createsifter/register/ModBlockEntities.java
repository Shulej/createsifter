package io.github.shulej.createsifter.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import io.github.shulej.createsifter.CreateSifter;
import io.github.shulej.createsifter.content.contraptions.components.brass_sifter.BrassSifterBlockEntity;
import io.github.shulej.createsifter.content.contraptions.components.brass_sifter.BrassSifterCogInstance;
import io.github.shulej.createsifter.content.contraptions.components.brass_sifter.BrassSifterRenderer;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterCogInstance;
import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterRenderer;

public class ModBlockEntities {
	public static final BlockEntityEntry<SifterBlockEntity> SIFTER = CreateSifter.REGISTRATE
			.blockEntity("sifter", SifterBlockEntity::new)
			.instance(() -> SifterCogInstance::new)
			.validBlocks(ModBlocks.SIFTER_BLOCK)
			.renderer(() -> SifterRenderer::new)
			.register();

	public static final BlockEntityEntry<BrassSifterBlockEntity> BRASS_SIFTER = CreateSifter.REGISTRATE
			.blockEntity("brass_sifter", BrassSifterBlockEntity::new)
			.instance(() -> BrassSifterCogInstance::new)
			.validBlocks(ModBlocks.BRASS_SIFTER_BLOCK)
			.renderer(() -> BrassSifterRenderer::new)
			.register();

	public static void register() {

	}
}
