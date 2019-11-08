package com.lochnessdragon.mouse.golem;

import java.util.Random;

import com.lochnessdragon.mouse.MouseMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class SnowGolemFaceTweak {

	private static Random RAND = new Random();

	public static void registerLayers() {
		MouseMod.LOGGER.info("Registering Layers");
		LivingRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> renderer = Minecraft.getInstance()
				.getRenderManager().getRenderer(SnowGolemEntity.class);
		renderer.addLayer(new FaceRenderLayer(renderer));
	}

	@SubscribeEvent
	public static void snowGolemCreated(EntityEvent.EntityConstructing event) {

		if (event.getEntity() instanceof SnowGolemEntity) {
			SnowGolemEntity entity = (SnowGolemEntity) event.getEntity();
			if (!entity.getPersistentData().contains("face")) {
				String faceName;

				int randNum = RAND.nextInt(4);

				if (randNum == 0) {
					faceName = "sad";
				} else if (randNum == 1) {
					faceName = "mad";
				} else if (randNum == 2) {
					faceName = "monocle";
				} else {
					faceName = "happy";
				}

				StringNBT face = new StringNBT(faceName);

				entity.getPersistentData().put("face", face);
			}
		}
	}

}
