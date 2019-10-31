package com.lochnessdragon.mouse.end;

import com.lochnessdragon.mouse.MouseMod;
import com.lochnessdragon.mouse.init.BlockList;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.EndDimension;
import net.minecraftforge.common.util.Constants.WorldEvents;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RevisedEndPortal {
	
//	   @SubscribeEvent
//	   public static void endGatewayGenerated(WorldEvent event) {
//		   if(event.getWorld().getDimension() instanceof EndDimension) {
//			   //MouseMod.LOGGER.info("End Dimension");
//		   }
//	   }
	   
	   @SubscribeEvent
	   public static void endGatewayGenerated(ChunkEvent event) {
		   if(event.getWorld().getDimension() instanceof EndDimension) {
			   //MouseMod.LOGGER.info("End Dimension");
			   
			   Block fromBlock = Blocks.BEDROCK;
			   Block toBlock = BlockList.cheese_block;
			   
			   IChunk chunk = event.getChunk();
			   
			   for (int x = 0; x < 16; x++) {
				   for (int y = 0; y < 256; y++) {
					   for (int z = 0; x < 16; x++) {
						   if(chunk.getBlockState(new BlockPos(x, y, x)) == fromBlock.getDefaultState()) {
							   chunk.setBlockState(new BlockPos(x, y, x), toBlock.getDefaultState(), false);
						   }
					   }
				   }
			   }
			   
			   chunk.setModified(true);
			   
		   }
	   }
}
