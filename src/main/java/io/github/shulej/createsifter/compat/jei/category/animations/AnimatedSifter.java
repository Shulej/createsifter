package io.github.shulej.createsifter.compat.jei.category.animations;

import com.jozufozu.flywheel.core.PartialModel;

import com.tterrag.registrate.util.entry.BlockEntry;

import io.github.shulej.createsifter.content.contraptions.components.sifter.SifterBlock;
import io.github.shulej.createsifter.register.ModBlocks;
import io.github.shulej.createsifter.register.ModPartials;

public class AnimatedSifter extends BaseAnimatedSifter<SifterBlock> {
	@Override
	PartialModel getMeshModel() {
		return ModPartials.SIFTER_MESH;
	}

	@Override
	PartialModel getCogModel() {
		return ModPartials.SIFTER_COG;
	}

	@Override
	BlockEntry<SifterBlock> getSifterBlock() {
		return ModBlocks.SIFTER_BLOCK;
	}
}
