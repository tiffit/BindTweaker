package net.tiffit.bindtweaker.api.server;

import crafttweaker.CraftTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.Manager")
public class APIHubServer {
	
	@ZenMethod
	public static void printBinds(){
		printError();
	}
	
	@ZenMethod
	public static KeyBindWrapperServer createBind(String description, int keycode, String category){
		printError();
		return null;
	}
	
	@ZenMethod
	public static KeyBindWrapperServer getKeybind(String description){
		printError();
		return null;
	}
	
	public static void printError(){
		CraftTweakerAPI.logError("You attempted to use Bind Tweaker on a server!\nBind Tweaker does not work on a server!\nYou can check if you are running the client with mod.gbt.Util.isClient()");
	}
}
