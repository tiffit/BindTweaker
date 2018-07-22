package net.tiffit.bindtweaker.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.tiffit.bindtweaker.EventHandler;


@SuppressWarnings("deprecation")
public class GuiBindMenu extends GuiScreen {

	private final KeyBinding[] binds;
	private int largestNameWidth = -1;
	
	public GuiBindMenu(KeyBinding[] binds) {
		this.binds = binds;
	}
	
	@Override
	public void initGui() {
		for(int i = 0; i < binds.length; i++)buttonList.add(new BindButton(i, binds[i]));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void keyTyped(char chr, int keyCode) throws IOException {
		super.keyTyped(chr, keyCode);
		if(Character.isDigit(chr)){
			int pressedVal = Character.getNumericValue(chr);
			if(pressedVal < binds.length){
				pressBind(pressedVal);
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		pressBind(button.id);
	}
	
	private void pressBind(int id){
		mc.displayGuiScreen(null);
		EventHandler.forceNext = binds[id];
		FMLCommonHandler.instance().fireKeyInput();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private int getLargestNameWidth(){
		if(largestNameWidth == -1){
			largestNameWidth = fontRenderer.getStringWidth(getBindName(binds[0]));
			for(int i = 1; i < binds.length; i++){
				int newW = fontRenderer.getStringWidth(getBindName(binds[i]));
				if(newW > largestNameWidth)largestNameWidth = newW;
			}
		}
		return largestNameWidth;
	}
	
	private String getBindName(KeyBinding bind){
		return I18n.translateToLocal(bind.getKeyDescription()) + TextFormatting.GOLD + " (" + I18n.translateToLocal(bind.getKeyCategory()) + ")";
	}
	
	public class BindButton extends GuiButton{

		public BindButton(int id, KeyBinding bind) {
			super(id, GuiBindMenu.this.width/2 - (getLargestNameWidth()/2 + 20), GuiBindMenu.this.height/2 + id*25 - binds.length*25/2, getLargestNameWidth()+40, 20, getBindName(bind));
		}
		
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            FontRenderer fr = mc.fontRenderer;
            int color = 14737632;
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if (this.hovered)color = 16777120;
            drawGradientRect(x, y, x + width, y + height, 0xff0000aa, 0x00aaaaaa);
            this.drawString(fr, id + "|", x + 3, this.y + (this.height - 8) / 2, color);
            this.drawCenteredString(fr, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, color);
		}
		
	}
	
}
