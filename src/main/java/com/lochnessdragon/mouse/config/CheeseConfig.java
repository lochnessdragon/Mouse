package com.lochnessdragon.mouse.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CheeseConfig {
	
	public static ForgeConfigSpec.BooleanValue ENABLED;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
		server.comment("Cheese");
		
		ENABLED = server.comment("Whether cheese should be enabled or not: true/false").define("cheese.enabled", true);
	}

}
