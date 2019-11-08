package com.lochnessdragon.mouse.willow;

import com.lochnessdragon.mouse.MouseMod;
import com.lochnessdragon.mouse.init.BlockList;

import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;


@EventBusSubscriber
public class WillowTreeTweak {
	
	@SubscribeEvent
	public static void playerUseItem(PlayerInteractEvent.RightClickBlock event) {
//		MouseMod.LOGGER.info("Player right clicked a block");
		if(event.getItemStack().getItem() instanceof AxeItem) {
//			MouseMod.LOGGER.info("Player used an axe");
			if(event.getWorld().getBlockState(event.getPos()).getBlock() == BlockList.willow_log) {
//				MouseMod.LOGGER.info("Player clicked a willow log");
				event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
		         if (!event.getWorld().isRemote) {
		        	 event.getWorld().setBlockState(event.getPos(), BlockList.stripped_willow_log.getDefaultState().with(RotatedPillarBlock.AXIS, event.getWorld().getBlockState(event.getPos()).get(RotatedPillarBlock.AXIS)), 11);
		            if (event.getPlayer() != null) {
		               event.getItemStack().damageItem(1, event.getPlayer(), (p_220040_1_) -> {
		                  p_220040_1_.sendBreakAnimation(event.getHand());
		               });
		            }
		         }
			} else if(event.getWorld().getBlockState(event.getPos()).getBlock() == BlockList.willow_wood) {
				event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
		         if (!event.getWorld().isRemote) {
		        	 event.getWorld().setBlockState(event.getPos(), BlockList.stripped_willow_wood.getDefaultState().with(RotatedPillarBlock.AXIS, event.getWorld().getBlockState(event.getPos()).get(RotatedPillarBlock.AXIS)), 11);
		            if (event.getPlayer() != null) {
		               event.getItemStack().damageItem(1, event.getPlayer(), (p_220040_1_) -> {
		                  p_220040_1_.sendBreakAnimation(event.getHand());
		               });
		            }
		         }
			}
		}
	}
	
	public static void setupSwampGeneration() {
		Biomes.SWAMP.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(new WillowTreeFeature(NoFeatureConfig::deserialize, true), new NoFeatureConfig(), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(1, 0.1F, 0)));
	}
	
}
