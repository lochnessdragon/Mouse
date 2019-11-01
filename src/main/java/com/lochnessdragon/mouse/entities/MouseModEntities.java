package com.lochnessdragon.mouse.entities;

import com.lochnessdragon.mouse.MouseMod;
import com.lochnessdragon.mouse.init.ItemList;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;

public class MouseModEntities {
	
	public static void registerSpawnEggs(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				ItemList.iron_golem_spawn_egg = registerEntitySpawnEgg(EntityType.IRON_GOLEM, 0xD4D4D4, 0xA39E9B, "iron_golem_spawn_egg"),
				ItemList.snow_golem_spawn_egg = registerEntitySpawnEgg(EntityType.SNOW_GOLEM, 0xffffff, 0x896727, "snow_golem_spawn_egg")
				);
	}

	
	public static SpawnEggItem registerEntitySpawnEgg(EntityType<?> type, int primaryColor, int secondaryColor, String name) {
		SpawnEggItem item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC));
		item.setRegistryName(MouseMod.location(name));
		return item;
	}
}
