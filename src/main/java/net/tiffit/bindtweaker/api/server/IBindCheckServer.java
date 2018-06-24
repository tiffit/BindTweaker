package net.tiffit.bindtweaker.api.server;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.BindCheck")
public interface IBindCheckServer {

	boolean canRun(CheckEvent event);

	@ZenClass(value = "mod.bt.BindCheckEvent")
	public static class CheckEvent{
		
		@ZenMethod
		@ZenGetter("bind")
		public KeyBindWrapperServer getBind(){
			APIHubServer.printError();
			return null;
		}
		
		@ZenMethod
		public IItemStack getHeldStack(@Optional(valueBoolean = false) boolean offhand){
			APIHubServer.printError();
			return null;
		}
		
		@ZenMethod
		@ZenGetter("player")
		public IPlayer getPlayer(){
			APIHubServer.printError();
			return null;
		}
		
	}
	
}
