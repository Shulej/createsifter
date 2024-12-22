package io.github.shulej.createsifter.register;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;

import io.github.shulej.createsifter.CreateSifter;
import io.github.shulej.createsifter.ponders.PonderScenes;

public class ModPonders {
	static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateSifter.MODID);

	public static void register() {
		HELPER.addStoryBoard(ModBlocks.SIFTER_BLOCK, "sifter", PonderScenes::sifter, AllPonderTags.KINETIC_APPLIANCES);
		HELPER.addStoryBoard(ModBlocks.BRASS_SIFTER_BLOCK, "sifter", PonderScenes::sifter, AllPonderTags.KINETIC_APPLIANCES);

		PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES).add(ModBlocks.SIFTER_BLOCK);
		PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES).add(ModBlocks.BRASS_SIFTER_BLOCK);
	}
}
