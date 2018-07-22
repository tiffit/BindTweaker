package net.tiffit.bindtweaker.api;

import java.util.HashMap;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.tiffit.bindtweaker.EventHandler;
import net.tiffit.bindtweaker.api.IBindCheck.CheckEvent;
import net.tiffit.bindtweaker.gui.GuiBindMenu;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.Binds")
public class APIHub {
	
	private static HashMap<String, KeyBindWrapper[]> bindmenus = new HashMap<String, KeyBindWrapper[]>();
	
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
	public static void createBindMenu(String name, KeyBindWrapper... wrappers){
		if(bindmenus.containsKey(name))CraftTweakerAPI.logError("Bind Menu " + name + " already exists!");
		else bindmenus.put(name, wrappers);
	}
	
	
	public static void openBindMenu(String name, boolean useBindChecks){
		Minecraft mc = Minecraft.getMinecraft();
		if(!bindmenus.containsKey(name))CraftTweakerAPI.logError("Attempting to open non-existant bind menu " + name);
		else{
			KeyBindWrapper[] wrappers = bindmenus.get(name);
			KeyBinding[] binds = new KeyBinding[wrappers.length];
			for(int i = 0; i < wrappers.length; i++){
				KeyBindWrapper wrapper = wrappers[i];
				IBindCheck check = EventHandler.BINDING_CHECK.get(wrapper);
				if(!useBindChecks || check == null || check.canRun(new CheckEvent(wrapper.bind))){
					binds[i] = wrapper.bind;
				}
			}
			mc.addScheduledTask(() -> mc.displayGuiScreen(new GuiBindMenu(binds)));
		}
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
