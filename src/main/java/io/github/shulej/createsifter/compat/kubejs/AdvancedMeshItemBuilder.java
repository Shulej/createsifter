package io.github.shulej.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import io.github.shulej.createsifter.content.contraptions.components.meshes.AdvancedCustomMesh;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class AdvancedMeshItemBuilder extends ItemBuilder {

	public AdvancedMeshItemBuilder(ResourceLocation i) {
		super(i);
	}

	@Override
	public Item createObject() {
		return new AdvancedCustomMesh(createItemProperties());
	}
}
