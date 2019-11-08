package com.lochnessdragon.mouse.golem;

import com.lochnessdragon.mouse.MouseMod;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SnowManModel;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FaceRenderLayer extends LayerRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>>{
	
	private static final ResourceLocation SAD_FACE_TEXTURE = MouseMod.location("textures/entity/snowman/snowman_sad.png");
	private static final ResourceLocation HAPPY_FACE_TEXTURE = MouseMod.location("textures/entity/snowman/snowman_regular.png");
	private static final ResourceLocation MAD_FACE_TEXTURE = MouseMod.location("textures/entity/snowman/snowman_mad.png");
	private static final ResourceLocation MONOCLE_FACE_TEXTURE = MouseMod.location("textures/entity/snowman/snowman_monocle.png");
	
	
	public FaceRenderLayer(IEntityRenderer<SnowGolemEntity, SnowManModel<SnowGolemEntity>> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(SnowGolemEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		String face = entityIn.getPersistentData().getString("face");
		
		if(face != null) {
		if(face.contentEquals("sad")) {
			this.bindTexture(SAD_FACE_TEXTURE);
		} else if(face.contentEquals("mad")) {
			this.bindTexture(MAD_FACE_TEXTURE);
		} else if(face.contentEquals("monocle")) {
			this.bindTexture(MONOCLE_FACE_TEXTURE);
		} else {
			this.bindTexture(HAPPY_FACE_TEXTURE);
		}
		} else {
			this.bindTexture(HAPPY_FACE_TEXTURE);
		}
		
		this.getEntityModel().render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		GlStateManager.pushMatrix();
//		GlStateManager.rotated(180, 0, 0, 1);
//		GlStateManager.scaled(0.6, 0.6, 0.6);
//        GlStateManager.rotatef((ageInTicks) / 20.0F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
//		GlStateManager.translated(0, entityIn.getHeight() - 0.3, 0);
		//Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(Items.APPLE), ItemCameraTransforms.TransformType.FIXED);
//		GlStateManager.popMatrix();
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
