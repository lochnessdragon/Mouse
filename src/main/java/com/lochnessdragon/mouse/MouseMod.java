package com.lochnessdragon.mouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lochnessdragon.mouse.config.CheeseConfig;
import com.lochnessdragon.mouse.config.Config;
import com.lochnessdragon.mouse.entities.MouseModEntities;
import com.lochnessdragon.mouse.golem.SnowGolemFaceTweak;
import com.lochnessdragon.mouse.init.BlockList;
import com.lochnessdragon.mouse.init.ItemList;
import com.lochnessdragon.mouse.willow.WillowTreeTweak;
import com.lochnessdragon.mouse.willow.WillowSapling;
import com.lochnessdragon.mouse.willow.WillowTree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("mouse")
public class MouseMod {

	// TODO: Create Files for Cheese Block

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "mouse";

	public MouseMod() {

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mouse-client.toml").toString());
		Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mouse-server.toml").toString());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		LOGGER.info("Setting up mod: " + MODID);
		SnowGolemFaceTweak.registerLayers();
		Minecraft.getInstance().getBlockColors().register((p_210229_0_, p_210229_1_, p_210229_2_, p_210229_3_) -> {
	         return p_210229_1_ != null && p_210229_2_ != null ? BiomeColors.getFoliageColor(p_210229_1_, p_210229_2_) : FoliageColors.getDefault();
	      }, BlockList.willow_leaves);
		Minecraft.getInstance().getItemColors().register((p_210235_1_, p_210235_2_) -> {
         BlockState blockstate = ((BlockItem)p_210235_1_.getItem()).getBlock().getDefaultState();
         return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IEnviromentBlockReader)null, (BlockPos)null, p_210235_2_);
      }, ItemList.willow_leaves);
		
		WillowTreeTweak.setupSwampGeneration();
	}

	private void doClientSetup(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
		LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
	}

	public static ResourceLocation location(String name) {
		return new ResourceLocation(MODID + ":" + name);
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void registerItem(final RegistryEvent.Register<Item> event) {
			LOGGER.info("Mouse Mod: Registering Items");

			if (CheeseConfig.ENABLED.get()) {
				event.getRegistry().registerAll(
						ItemList.cheese = new Item(new Item.Properties().group(ItemGroup.FOOD)
								.food(new Food.Builder().setAlwaysEdible().fastToEat().hunger(2).saturation(1.5f)
										.effect(new EffectInstance(Effects.SPEED, 100, 3), 1f).build()))
												.setRegistryName(location("cheese")),
						ItemList.cheese_block = new BlockItem(BlockList.cheese_block,
								new Item.Properties().group(ItemGroup.DECORATIONS))
										.setRegistryName(location("cheese_block")));
			}
			
			event.getRegistry().registerAll(
					
				ItemList.willow_log = new BlockItem(BlockList.willow_log, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_log")),
				ItemList.willow_planks = new BlockItem(BlockList.willow_planks, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_planks")),
				ItemList.willow_leaves = new BlockItem(BlockList.willow_leaves, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_leaves")),
				ItemList.willow_wood = new BlockItem(BlockList.willow_wood, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_wood")),
				ItemList.stripped_willow_log = new BlockItem(BlockList.stripped_willow_log, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("stripped_willow_log")),
				ItemList.stripped_willow_wood = new BlockItem(BlockList.stripped_willow_wood, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("stripped_willow_wood")),
				ItemList.willow_sapling = new BlockItem(BlockList.willow_sapling, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(location("willow_sapling"))
				
			);
			
			MouseModEntities.registerSpawnEggs(event);
			
			LOGGER.info("Mouse Mod: Items Registered!");
		}

		@SubscribeEvent
		public static void registerBlock(final RegistryEvent.Register<Block> event) {
			LOGGER.info("Mouse Mod: Registering Blocks");

			if (CheeseConfig.ENABLED.get()) {
				event.getRegistry()
						.register(BlockList.cheese_block = new Block(Block.Properties.create(Material.CAKE)
								.hardnessAndResistance(2.0f, 3.0f).sound(SoundType.CROP))
										.setRegistryName(location("cheese_block")));
			}
			
			event.getRegistry().registerAll(
				
				BlockList.willow_log = new LogBlock(MaterialColor.GREEN, Block.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(location("willow_log")),
				BlockList.willow_planks = new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).setRegistryName(location("willow_planks")),
				BlockList.willow_leaves = new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)).setRegistryName(location("willow_leaves")),
				BlockList.willow_wood = new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(location("willow_wood")),
				BlockList.stripped_willow_log = new LogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName("stripped_willow_log"),
				BlockList.stripped_willow_wood = new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(location("stripped_willow_wood")),
				BlockList.willow_sapling = new WillowSapling(new WillowTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0f).sound(SoundType.PLANT)).setRegistryName(location("willow_sapling"))
				
			);

			LOGGER.info("Mouse Mod: Blocks Registered!");
		}

	}

}
