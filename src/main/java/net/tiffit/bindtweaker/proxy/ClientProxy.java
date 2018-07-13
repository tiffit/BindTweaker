package net.tiffit.bindtweaker.proxy;

import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.tiffit.bindtweaker.api.IStartLoading;
import net.tiffit.bindtweaker.api.ServerChecker;

public class ClientProxy extends CommonProxy {

	@Override
	public void finishLoad(FMLLoadCompleteEvent e) {
		for(IStartLoading exec : ServerChecker.LOAD_EXECS){
			exec.run();
		}
	}
	
}
