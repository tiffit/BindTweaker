package net.tiffit.bindtweaker;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.tiffit.bindtweaker.proxy.CommonProxy;

@Mod(modid = BindTweaker.MODID, name = BindTweaker.NAME, version = BindTweaker.VERSION, dependencies = BindTweaker.DEPENDENCIES)
public class BindTweaker {
	public static final String MODID = "bindtweaker";
	public static final String NAME = "Bind Tweaker";
	public static final String VERSION = "1.2.0";
	public static final String DEPENDENCIES = "required-after:crafttweaker;";

	@SidedProxy(clientSide = "net.tiffit.bindtweaker.proxy.ClientProxy", serverSide = "net.tiffit.bindtweaker.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	@EventHandler
	public void finishLoad(FMLLoadCompleteEvent e) {
		proxy.finishLoad(e);
	}
}
