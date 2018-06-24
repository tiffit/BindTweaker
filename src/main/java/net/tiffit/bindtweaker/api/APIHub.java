package net.tiffit.bindtweaker.api;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.Manager")
public class APIHub {
	
	@ZenMethod
	public static void printBinds(){
		KeyBinding[] binds = getAllBinds();
		CraftTweakerAPI.logInfo("Printing All " + binds.length + " Keybinds:");
		for(KeyBinding b : binds){
			CraftTweakerAPI.logInfo(b.getKeyDescription() + ": " + b.getDisplayName());
		}
	}
	
	@ZenMethod
	public static KeyBindWrapper createBind(String description, int keycode, String category){
		ClientRegistry.registerKeyBinding(new KeyBinding(description, keycode, category));
		return KeyBindWrapper.wrap(getKeybindInternal(description));
	}
	
	@ZenMethod
	public static KeyBindWrapper getKeybind(String description){
		return KeyBindWrapper.wrap(getKeybindInternal(description));
	}
	
	public static KeyBinding[] getAllBinds(){
		return Minecraft.getMinecraft().gameSettings.keyBindings;
	}
	
	public static KeyBinding getKeybindInternal(String description){
		KeyBinding[] binds = getAllBinds();
		for(KeyBinding b : binds){
			if(b.getKeyDescription().equals(description))return b;
		}
		return null;
	}
}
