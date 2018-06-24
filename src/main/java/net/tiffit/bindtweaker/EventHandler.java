package net.tiffit.bindtweaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.tiffit.bindtweaker.api.IBindCheck;
import net.tiffit.bindtweaker.api.IBindCheck.CheckEvent;
import net.tiffit.bindtweaker.api.KeyBindWrapper;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class EventHandler {

	public static HashMap<KeyBinding, IBindCheck> BINDING_CHECK = new HashMap<KeyBinding, IBindCheck>();
	private static KeyBindingMap BIND_MAP = null;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void handleKeys(KeyInputEvent e) {
		if(BIND_MAP == null){
			BIND_MAP = ReflectionHelper.getPrivateValue(KeyBinding.class, null, "field_74514_b", "HASH");
		}
		for(KeyBinding bind : BINDING_CHECK.keySet()){
			KeyBindWrapper wrap = KeyBindWrapper.wrap(bind);
			if(!wrap.isChild() && checkBind(bind, null)){
				for(KeyBindWrapper child : wrap.children)checkBind(child.bind, bind);
			}
		}
	}
	
	private static boolean checkBind(KeyBinding bind, KeyBinding parent){
		if((parent != null && !isActive(parent)) || (parent == null && !isActive(bind)))return false;
		IBindCheck check = BINDING_CHECK.get(bind);
		boolean pass = check.canRun(new CheckEvent(bind));
		if(pass){
			bind.pressTime++;
			if(BIND_MAP.lookupActive(bind.getKeyCode()) == bind)bind.pressTime--;
		}
		else{
			bind.unpressKey();
		}
		return pass;
	}
	
	private static boolean isActive(KeyBinding bind){
		int code = bind.getKeyCode();
		KeyBinding active = BIND_MAP.lookupActive(code);
		List<KeyBinding> REMOVED = new ArrayList<KeyBinding>();
		while(active != null && active != bind){
			REMOVED.add(active);
			BIND_MAP.removeKey(active);
			active = BIND_MAP.lookupActive(code);
		}
		for(KeyBinding rem : REMOVED)BIND_MAP.addKey(code, rem);
		return active != null && Keyboard.isKeyDown(code);
	}

}
