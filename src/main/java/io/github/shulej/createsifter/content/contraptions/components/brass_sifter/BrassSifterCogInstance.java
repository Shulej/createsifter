package io.github.shulej.createsifter.content.contraptions.components.brass_sifter;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

import io.github.shulej.createsifter.register.ModPartials;

public class BrassSifterCogInstance extends SingleRotatingInstance<BrassSifterBlockEntity> {

	public BrassSifterCogInstance(MaterialManager materialManager, BrassSifterBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		return getRotatingMaterial().getModel(ModPartials.BRASS_SIFTER_COG, blockEntity.getBlockState());
	}
}
