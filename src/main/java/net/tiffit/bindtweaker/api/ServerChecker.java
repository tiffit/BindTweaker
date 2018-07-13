package net.tiffit.bindtweaker.api;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.Util")
@ZenRegister
public class ServerChecker {

	public static List<IStartLoading> LOAD_EXECS = new ArrayList<IStartLoading>();
	
	@ZenMethod
	public static boolean isServer(){
		return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
	}

	@ZenMethod
	public static boolean isClient(){
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}
	
	@ZenMethod
	public static void addLoadExec(IStartLoading load){
		LOAD_EXECS.add(load);
	}
	
}
