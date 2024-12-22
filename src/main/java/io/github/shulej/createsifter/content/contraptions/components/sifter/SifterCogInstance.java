package io.github.shulej.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

import io.github.shulej.createsifter.register.ModPartials;

public class SifterCogInstance extends SingleRotatingInstance<SifterBlockEntity> {
	public SifterCogInstance(MaterialManager materialManager, SifterBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		return getRotatingMaterial().getModel(ModPartials.SIFTER_COG, blockEntity.getBlockState());
	}
}
