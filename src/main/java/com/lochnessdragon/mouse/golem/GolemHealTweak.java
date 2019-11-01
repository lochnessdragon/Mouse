package com.lochnessdragon.mouse.golem;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class GolemHealTweak {

	public static Random rand = new Random();

	@SubscribeEvent
	public static void healGolem(PlayerInteractEvent.EntityInteract event) {

		if (event.getTarget() instanceof IronGolemEntity) {
			if (event.getItemStack().getItem() == Items.IRON_INGOT) {
				IronGolemEntity entity = (IronGolemEntity) event.getTarget();
				if (entity.getHealth() + 4f <= entity.getMaxHealth()) {
					// Each iron ingot should do 4 hearts of healing
					entity.heal(4f);
					if(!event.getPlayer().abilities.isCreativeMode) {
						event.getItemStack().shrink(1);
					}
					spawnParticleArray(ParticleTypes.HEART, entity, event.getWorld());
					event.getWorld().playSound(event.getPlayer(), new BlockPos(entity.posX, entity.posY, entity.posZ), SoundEvents.ENTITY_VILLAGER_CELEBRATE, SoundCategory.NEUTRAL, 1, 1);
				} else {
					spawnParticleArray(ParticleTypes.POOF, entity, event.getWorld());
					event.getWorld().playSound(event.getPlayer(), new BlockPos(entity.posX, entity.posY, entity.posZ), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL, 1, 1);
				}
			}
		}
		
		if (event.getTarget() instanceof SnowGolemEntity) {
			if (event.getItemStack().getItem() == Items.SNOWBALL) {
				SnowGolemEntity entity = (SnowGolemEntity) event.getTarget();
				if (entity.getHealth() + 1f <= entity.getMaxHealth()) {
					// Each snowball should do 1 hearts of healing
					entity.heal(1f);
					if(!event.getPlayer().abilities.isCreativeMode) {
						event.getItemStack().shrink(1);
					}
					spawnParticleArray(ParticleTypes.HEART, entity, event.getWorld());
					event.getWorld().playSound(event.getPlayer(), new BlockPos(entity.posX, entity.posY, entity.posZ), SoundEvents.ENTITY_VILLAGER_CELEBRATE, SoundCategory.NEUTRAL, 1, 1);
				} else {
					spawnParticleArray(ParticleTypes.POOF, entity, event.getWorld());
					event.getWorld().playSound(event.getPlayer(), new BlockPos(entity.posX, entity.posY, entity.posZ), SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.NEUTRAL, 1, 1);
				}
			}
		}

	}
	
	private static void spawnParticleArray(IParticleData iparticledata, Entity entity, World world) {
		for (int i = 0; i < 7; ++i) {
			double d0 = rand.nextGaussian() * 0.02D;
			double d1 = rand.nextGaussian() * 0.02D;
			double d2 = rand.nextGaussian() * 0.02D;
			world.addParticle(iparticledata,
					entity.posX + (double) (rand.nextFloat() * entity.getWidth() * 2.0F)
							- (double) entity.getWidth(),
					entity.posY + 0.5D + (double) (rand.nextFloat() * entity.getHeight()),
					entity.posZ + (double) (rand.nextFloat() * entity.getWidth() * 2.0F)
							- (double) entity.getWidth(),
					d0, d1, d2);
		}
	}

}
