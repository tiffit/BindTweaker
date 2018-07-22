package net.tiffit.bindtweaker.api;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.tiffit.bindtweaker.EventHandler;
import net.tiffit.bindtweaker.api.IBindCheck.CheckEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass(value = "mod.bt.Keybind")
public class KeyBindWrapper{

	private static Map<String, KeyBinding> KEYBIND_ARRAY = null;
	
	public static HashMap<KeyBinding, KeyBindWrapper> WRAPPER_MAP = new HashMap<KeyBinding, KeyBindWrapper>();
	
	public KeyBinding bind;
	private boolean child = false;
	public List<KeyBindWrapper> children = new ArrayList<KeyBindWrapper>();
	
	@ZenProperty
	public String bindmenu;
	@ZenProperty
	public boolean bindmenuUseBindChecks = false;
	
	private KeyBindWrapper(KeyBinding bind){
		this.bind = bind;
	}

	@ZenMethod
	public boolean isDefault(){
		return bind.isSetToDefaultValue();
	}
	
	@ZenMethod
	public String getDisplayName(){
		return bind.getDisplayName();
	}
	
	@ZenMethod
	public String getDescription(){
		return bind.getKeyDescription();
	}
	
	@ZenMethod
	@ZenGetter("child")
	public boolean isChild(){
		return child;
	}
	
	@ZenMethod
	public void addChild(KeyBindWrapper bind){
		children.add(bind);
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		settings.keyBindings = ArrayUtils.removeElement(settings.keyBindings, bind.bind);
		if(KEYBIND_ARRAY == null){
			KEYBIND_ARRAY = ReflectionHelper.getPrivateValue(KeyBinding.class, null, "field_74516_a", "KEYBIND_ARRAY");
		}
		KEYBIND_ARRAY.remove(bind.bind.getKeyDescription(), bind.bind);
		KeyBinding.resetKeyBindingArrayAndHash();
		bind.child = true;
	}
	
	@ZenMethod
	public KeyBindWrapper withDisable(){
		try {
			Path p = new File(Minecraft.getMinecraft().mcDataDir, "options.txt").toPath();
			List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
			for(int i = 0; i < lines.size(); i++){
				String line = lines.get(i);
				String[] split = line.split(":");
				if(split.length == 2 && split[0].equals("key_" + bind.getKeyDescription())){
					lines.set(i, split[0] + ":0");
				}
			}
			Files.write(p, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@ZenMethod
	public void setBindCheck(IBindCheck check){
		if(check == null)EventHandler.BINDING_CHECK.remove(bind);
		else EventHandler.BINDING_CHECK.put(bind, check);
	}
	
	@ZenMethod
	public int getDefault(){
		return bind.getKeyCodeDefault();
	}
	
	public static KeyBindWrapper wrap(KeyBinding bind){
		WRAPPER_MAP.putIfAbsent(bind, new KeyBindWrapper(bind));
		KeyBindWrapper wrap = WRAPPER_MAP.get(bind);
		if(EventHandler.BINDING_CHECK.get(bind) == null){
			wrap.setBindCheck((CheckEvent e) -> { return true; });
		}
		return wrap;
	}
	
}
