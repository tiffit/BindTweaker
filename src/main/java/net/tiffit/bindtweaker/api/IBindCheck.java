package net.tiffit.bindtweaker.api;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.BindCheck")
public interface IBindCheck {

	boolean canRun(CheckEvent event);

	@ZenClass(value = "mod.bt.BindCheckEvent")
	public static class CheckEvent{
		
		private KeyBindWrapper bind;
		
		public CheckEvent(KeyBinding bind){
			this.bind = KeyBindWrapper.wrap(bind);
		}
		
		@ZenMethod
		@ZenGetter("bind")
		public KeyBindWrapper getBind(){
			return bind;
		}
		
		@ZenMethod
		public IItemStack getHeldStack(@Optional(valueBoolean = false) boolean offhand){
			EntityPlayerSP p = Minecraft.getMinecraft().player;
			ItemStack stack = null;
			if(offhand)stack = p.getHeldItemOffhand();
			else stack = p.getHeldItemMainhand();
			if(stack == null || stack.isEmpty())return MCItemStack.EMPTY;
			return new MCItemStack(stack);
		}
		
		@ZenMethod
		@ZenGetter("player")
		public IPlayer getPlayer(){
			return new MCPlayer(Minecraft.getMinecraft().player);
		}
		
	}
	
}
