package com.lochnessdragon.mouse.willow;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import com.lochnessdragon.mouse.MouseMod;
import com.lochnessdragon.mouse.init.BlockList;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.templates.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;

public class WillowTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

	public static final BlockState LOG_BLOCK = BlockList.willow_log.getDefaultState();
	public static final BlockState WOOD_BLOCK = BlockList.willow_wood.getDefaultState();
	public static final BlockState LEAF_BLOCK = BlockList.willow_leaves.getDefaultState();
	public static final Block VINE_BLOCK = Blocks.VINE;

	public WillowTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49920_1_,
			boolean doBlockNofityOnPlace) {
		super(p_i49920_1_, doBlockNofityOnPlace);
		this.setSapling((IPlantable) BlockList.willow_sapling);
	}

	@Override
	public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position,
			MutableBoundingBox boundsIn) {
		
		this.setDirtAt(worldIn, position.down(), position);
		
		//position = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR, position);		

		if (position.getY() >= 1 && position.getY() <= worldIn.getMaxHeight()) {
			// Is there space?, if so, generate the tree
			// Should we generate roots?
			if (rand.nextInt(2) == 1) {
				genRoots(changedBlocks, worldIn, rand, position, boundsIn);
				
			}
			// Generate trunk and branches
			int trunkHeight = genTrunk(changedBlocks, worldIn, rand, position, boundsIn);
			
			genBranches(changedBlocks, worldIn, rand, position, boundsIn, trunkHeight);

			return true;
		} else {
			return false;
		}

		// if (position.getY() >= 1 && position.getY() + i + 1 <=
		// worldIn.getMaxHeight()) {
		// for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
		// int k = 1;
		// if (j == position.getY()) {
		// k = 0;
		// }
		//
		// if (j >= position.getY() + 1 + i - 2) {
		// k = 3;
		// }
		//
		// BlockPos.MutableBlockPos blockpos$mutableblockpos = new
		// BlockPos.MutableBlockPos();
		//
		// for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
		// for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
		// if (j >= 0 && j < worldIn.getMaxHeight()) {
		// blockpos$mutableblockpos.setPos(l, j, i1);
		// if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos)) {
		// if (isWater(worldIn, blockpos$mutableblockpos)) {
		// if (j > position.getY()) {
		// flag = false;
		// }
		// } else {
		// flag = false;
		// }
		// }
		// } else {
		// flag = false;
		// }
		// }
		// }
		// }
		//
		// if (!flag) {
		// return false;
		// } else if (isSoil(worldIn, position.down(), getSapling()) && position.getY()
		// < worldIn.getMaxHeight() - i - 1) {
		// this.setDirtAt(worldIn, position.down(), position);
		//
		// for(int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; ++l1) {
		// int k2 = l1 - (position.getY() + i);
		// int i3 = 2 - k2 / 2;
		//
		// for(int k3 = position.getX() - i3; k3 <= position.getX() + i3; ++k3) {
		// int l3 = k3 - position.getX();
		//
		// for(int j1 = position.getZ() - i3; j1 <= position.getZ() + i3; ++j1) {
		// int k1 = j1 - position.getZ();
		// if (Math.abs(l3) != i3 || Math.abs(k1) != i3 || rand.nextInt(2) != 0 && k2 !=
		// 0) {
		// BlockPos blockpos = new BlockPos(k3, l1, j1);
		// if (isAirOrLeaves(worldIn, blockpos) || isTallPlants(worldIn, blockpos)) {
		// this.setLogState(changedBlocks, worldIn, blockpos,
		// LEAF_BLOCK.getDefaultState(), boundsIn);
		// }
		// }
		// }
		// }
		// }
		//
		// for(int i2 = 0; i2 < i; ++i2) {
		// BlockPos blockpos3 = position.up(i2);
		// if (isAirOrLeaves(worldIn, blockpos3) || isWater(worldIn, blockpos3)) {
		// this.setLogState(changedBlocks, worldIn, blockpos3,
		// LOG_BLOCK.getDefaultState(), boundsIn);
		// }
		// }
		//
		// for(int j2 = position.getY() - 3 + i; j2 <= position.getY() + i; ++j2) {
		// int l2 = j2 - (position.getY() + i);
		// int j3 = 2 - l2 / 2;
		// BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new
		// BlockPos.MutableBlockPos();
		//
		// for(int i4 = position.getX() - j3; i4 <= position.getX() + j3; ++i4) {
		// for(int j4 = position.getZ() - j3; j4 <= position.getZ() + j3; ++j4) {
		// blockpos$mutableblockpos1.setPos(i4, j2, j4);
		// if (isLeaves(worldIn, blockpos$mutableblockpos1)) {
		// BlockPos blockpos4 = blockpos$mutableblockpos1.west();
		// BlockPos blockpos5 = blockpos$mutableblockpos1.east();
		// BlockPos blockpos1 = blockpos$mutableblockpos1.north();
		// BlockPos blockpos2 = blockpos$mutableblockpos1.south();
		// if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos4)) {
		// this.addVine(worldIn, blockpos4, VineBlock.EAST);
		// }
		//
		// if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos5)) {
		// this.addVine(worldIn, blockpos5, VineBlock.WEST);
		// }
		//
		// if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos1)) {
		// this.addVine(worldIn, blockpos1, VineBlock.SOUTH);
		// }
		//
		// if (rand.nextInt(4) == 0 && isAir(worldIn, blockpos2)) {
		// this.addVine(worldIn, blockpos2, VineBlock.NORTH);
		// }
		// }
		// }
		// }
		// }
		//
		// return true;
		// } else {
		// return false;
		// }
		// } else {
		// return false;
		// }
	}

	private void genBranches(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand,
			BlockPos position, MutableBoundingBox boundsIn, int trunkMaxHeight) {
		int numOfBranches = rand.nextInt(2) + 6;
		int branchLength;
		Vector3d dir;
		double X, Y, Z;
		for(int a = 0; a < numOfBranches; a++) {
			branchLength  = rand.nextInt(6) + 5;
			dir = randomizeVector3d_posY(rand);
			dir.normalize();
			// Cast X, Y, Z to doubles
			X = position.getX();
			Y = (position.getY() + trunkMaxHeight);
			Z = position.getZ();
		
			for (int i = 0; i < branchLength; i++) {
				this.setLogState(changedBlocks, worldIn, new BlockPos(X, Y, Z), WOOD_BLOCK, boundsIn);
				generateLeaves(changedBlocks, worldIn, rand, new BlockPos(X, Y, Z), boundsIn, dir, false);
				X += dir.getX();
				Y += dir.getY();
				Z += dir.getZ();
			}
			generateLeaves(changedBlocks, worldIn, rand, new BlockPos(X, Y, Z), boundsIn, dir, true);
		}
	}
	
	private void generateLeaves(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand,
			BlockPos root, MutableBoundingBox boundsIn, Vector3d branchVector, boolean shouldGenerateEnd) {
		this.setLogState(changedBlocks, worldIn, root.up(), LEAF_BLOCK, boundsIn);
		
		Vector2d perpendicularVector = new Vector2d(branchVector.getZ(), (-branchVector.getX()));
		perpendicularVector.normalize();
		int leafLength = rand.nextInt(5) + 4;
		
		BlockPos leafPos = new BlockPos((root.getX() + perpendicularVector.getX()), root.getY(), (root.getZ() + perpendicularVector.getY()));
		
		for(int i = 0; i < leafLength; i++) {
			if(isAirOrLeaves(worldIn, leafPos)) {
				this.setLogState(changedBlocks, worldIn, leafPos, LEAF_BLOCK, boundsIn);
				leafPos = leafPos.down();
			}
		}
		
		leafLength = rand.nextInt(5) + 4;
		
		leafPos = new BlockPos((root.getX() - perpendicularVector.getX()), root.getY(), (root.getZ() - perpendicularVector.getY()));
		
		for(int i = 0; i < leafLength; i++) {
			if(isAirOrLeaves(worldIn, leafPos)) {
				this.setLogState(changedBlocks, worldIn, leafPos, LEAF_BLOCK, boundsIn);
				leafPos = leafPos.down();
			}
		}
		
	}

	private Vector3d randomizeVector3d_posY(Random rand) {
		return new Vector3d((rand.nextDouble() - 0.5) * 2, rand.nextDouble(), (rand.nextDouble() - 0.5) * 2);
	}

	private Direction.Axis getLoxAxis(BlockPos p_197170_1_, BlockPos p_197170_2_) {
		Direction.Axis direction$axis = Direction.Axis.Y;
		int i = Math.abs(p_197170_2_.getX() - p_197170_1_.getX());
		int j = Math.abs(p_197170_2_.getZ() - p_197170_1_.getZ());
		int k = Math.max(i, j);
		if (k > 0) {
			if (i == k) {
				direction$axis = Direction.Axis.X;
			} else if (j == k) {
				direction$axis = Direction.Axis.Z;
			}
		}

		return direction$axis;
	}
	
	private void genRoots(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position,
			MutableBoundingBox boundsIn) {
		int numOfRoots = rand.nextInt(10);
		
		
		// Casting the x , z to double
		double lastX, lastZ;
		lastX = position.getX();
		lastZ = position.getZ();
		boolean hasBumpedUp = false;
		
		for(int a = 0; a < numOfRoots; a++ ) {
			Vector2d rot = new Vector2d((rand.nextDouble() - 0.5) * 2, (rand.nextDouble() - 0.5) * 2); // new Vector2d(1.0, 1.0);//new Vector2d((rand.nextDouble() - 0.5), (rand.nextDouble() - 0.5));
			rot.normalize();
//			MouseMod.LOGGER.info(rot.x + ":" + rot.y);
			int length = rand.nextInt(6);
			BlockPos log_pos = new BlockPos(position.getX(), position.getY() - 1, position.getZ());
			//rot.scale(length);
//			MouseMod.LOGGER.info(rot.x + ":" + rot.y);
//			MouseMod.LOGGER.info("length: " + length);
			
			
			for (int b = 0; b < length; b++) {
				if(!hasBumpedUp) {
					if(rand.nextInt(6) == 0) {
						// Bump the next block up
						log_pos = new BlockPos(lastX, log_pos.getY() + 1, lastZ);
						hasBumpedUp = true;
					}
				} else if(hasBumpedUp) {
					if(rand.nextInt(5) == 0) {
						// Bump the next block down
						log_pos = new BlockPos(lastX, log_pos.getY() - 1, lastZ);
						hasBumpedUp = false;
					}
				}
				
				lastX += (rot.getX()); // / length
				lastZ += (rot.getY());
//				MouseMod.LOGGER.info("Next x: " + (lastX)); // + (rot.getX() / length)
//				MouseMod.LOGGER.info("Next z: " + (lastZ));
				
				if(b + 1 == length && hasBumpedUp) {
					log_pos = new BlockPos(lastX, log_pos.getY() - 1, lastZ); //new BlockPos(position.getX(), position.getY() - 1, position.getZ() - b - 1)
				} else {
					log_pos = new BlockPos(lastX, log_pos.getY(), lastZ); //new BlockPos(position.getX(), position.getY() - 1, position.getZ() - b - 1)
				}
				this.setLogState(changedBlocks, worldIn,
					log_pos,
					WOOD_BLOCK.with(LogBlock.AXIS, this.getLoxAxis(position, log_pos)), boundsIn);
			}
			
			hasBumpedUp = false;
			
			lastX = position.getX();
			lastZ = position.getZ();
		}
	}
	
	private int genTrunk(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position,
			MutableBoundingBox boundsIn) {
		this.setLogState(changedBlocks, worldIn, position, WOOD_BLOCK, boundsIn);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(rand.nextBoolean()) {
					//MouseMod.LOGGER.info("trunk generated one block at: X:" + (position.getX() + (i-1)) + "Z: " + (position.getZ() + (j-1)));
					this.setLogState(changedBlocks, worldIn, new BlockPos((position.getX() + (i-1)),  position.getY(),  position.getZ() + (j-1)), WOOD_BLOCK, boundsIn);
				}
			}
		}
		
		int trunkHeight = rand.nextInt(3) + 6;
		BlockPos lastPos = position;
		for(int a = 0; a < trunkHeight; a++) {
			this.setLogState(changedBlocks, worldIn, lastPos, WOOD_BLOCK, boundsIn);
			lastPos = lastPos.up();
		}
		
		return trunkHeight;
		
	}

	private int getGreatestDistance(BlockPos posIn) {
	      int i = MathHelper.abs(posIn.getX());
	      int j = MathHelper.abs(posIn.getY());
	      int k = MathHelper.abs(posIn.getZ());
	      if (k > i && k > j) {
	         return k;
	      } else {
	         return j > i ? j : i;
	      }
	   }
	
	private void addVine(IWorldGenerationReader worldIn, BlockPos pos, BooleanProperty prop) {
		BlockState blockstate = VINE_BLOCK.getDefaultState().with(prop, Boolean.valueOf(true));
		this.setBlockState(worldIn, pos, blockstate);
		int i = 4;

		for (BlockPos blockpos = pos.down(); isAir(worldIn, blockpos) && i > 0; --i) {
			this.setBlockState(worldIn, blockpos, blockstate);
			blockpos = blockpos.down();
		}

	}

}
