package net.tiffit.bindtweaker;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.bindtweaker.api.APIHub;
import net.tiffit.bindtweaker.api.IBindCheck;
import net.tiffit.bindtweaker.api.KeyBindWrapper;
import net.tiffit.bindtweaker.api.server.APIHubServer;
import net.tiffit.bindtweaker.api.server.IBindCheckServer;
import net.tiffit.bindtweaker.api.server.KeyBindWrapperServer;

@Mod(modid = BindTweaker.MODID, name = BindTweaker.NAME, version = BindTweaker.VERSION, dependencies = BindTweaker.DEPENDENCIES)
public class BindTweaker {
	public static final String MODID = "bindtweaker";
	public static final String NAME = "Bind Tweaker";
	public static final String VERSION = "1.0.0";
	public static final String DEPENDENCIES = "required-after:crafttweaker;";

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		if(e.getSide() == Side.CLIENT){
			CraftTweakerAPI.registerClass(APIHub.class);
			CraftTweakerAPI.registerClass(KeyBindWrapper.class);
			CraftTweakerAPI.registerClass(IBindCheck.class);
			CraftTweakerAPI.registerClass(IBindCheck.CheckEvent.class);
		}else{
			CraftTweakerAPI.registerClass(APIHubServer.class);
			CraftTweakerAPI.registerClass(KeyBindWrapperServer.class);
			CraftTweakerAPI.registerClass(IBindCheckServer.class);
			CraftTweakerAPI.registerClass(IBindCheckServer.CheckEvent.class);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
}
