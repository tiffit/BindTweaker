package net.tiffit.bindtweaker.api;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass(value = "mod.bt.StartLoading")
@ZenRegister
public interface IStartLoading {

	void run();
	
}
