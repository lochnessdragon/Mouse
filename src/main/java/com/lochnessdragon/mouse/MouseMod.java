package com.lochnessdragon.mouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lochnessdragon.mouse.config.Config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
	
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "mouse";
	
	
	public MouseMod() {
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);
		
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-client.toml").toString());
		Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("tutorialmod-server.toml").toString());
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
    {
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
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		
		@SubscribeEvent
		public static void registerItem(final RegistryEvent.Register<Item> event) {
			LOGGER.info("Mouse Mod: Registering Items");
			
			LOGGER.info("Mouse Mod: Items Registered!");
		}
		
		@SubscribeEvent
		public static void registerBlock(final RegistryEvent.Register<Block> event) {
			LOGGER.info("Mouse Mod: Registering Blocks");
			
			LOGGER.info("Mouse Mod: Blocks Registered!");
		}
		
	}
	
}
