package com.lochnessdragon.mouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lochnessdragon.mouse.config.CheeseConfig;
import com.lochnessdragon.mouse.config.Config;
import com.lochnessdragon.mouse.entities.MouseModEntities;
import com.lochnessdragon.mouse.init.BlockList;
import com.lochnessdragon.mouse.init.ItemList;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
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

			LOGGER.info("Mouse Mod: Blocks Registered!");
		}

	}

}
