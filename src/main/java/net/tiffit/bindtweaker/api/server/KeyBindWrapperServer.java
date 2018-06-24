package net.tiffit.bindtweaker.api.server;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass(value = "mod.bt.Keybind")
public class KeyBindWrapperServer{
	
	@ZenMethod
	public boolean isDefault(){
		APIHubServer.printError();
		return false;
	}
	
	@ZenMethod
	public String getDisplayName(){
		APIHubServer.printError();
		return null;
	}
	
	@ZenMethod
	public String getDescription(){
		APIHubServer.printError();
		return null;
	}
	
	@ZenMethod
	@ZenGetter("child")
	public boolean isChild(){
		APIHubServer.printError();
		return false;
	}
	
	@ZenMethod
	public void addChild(KeyBindWrapperServer bind){
		APIHubServer.printError();
	}
	
	@ZenMethod
	public void setBindCheck(IBindCheckServer check){
		APIHubServer.printError();
	}
	
	@ZenMethod
	public int getDefault(){
		APIHubServer.printError();
		return 0;
	}
	
}
