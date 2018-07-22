package net.tiffit.bindtweaker.proxy;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.tiffit.bindtweaker.api.APIHub;
import net.tiffit.bindtweaker.api.IBindCheck;
import net.tiffit.bindtweaker.api.KeyBindWrapper;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		CraftTweakerAPI.registerClass(APIHub.class);
		CraftTweakerAPI.registerClass(KeyBindWrapper.class);
		CraftTweakerAPI.registerClass(IBindCheck.class);
		CraftTweakerAPI.registerClass(IBindCheck.CheckEvent.class);
	}
	
	@Override
	public void finishLoad(FMLLoadCompleteEvent e) {
		CraftTweakerAPI.tweaker.loadScript(false, "bindtweaker");
	}
	
}
