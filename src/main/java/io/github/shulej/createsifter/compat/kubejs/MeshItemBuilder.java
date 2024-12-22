package io.github.shulej.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import io.github.shulej.createsifter.content.contraptions.components.meshes.CustomMesh;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeshItemBuilder extends ItemBuilder {

	private ResourceLocation resourceLocation;
	public MeshItemBuilder(ResourceLocation i) {
		super(i);
		this.resourceLocation = i;
	}

	@Override
	public Item createObject() {
		return new CustomMesh(createItemProperties());
	}
}
