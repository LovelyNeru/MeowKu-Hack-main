package dev.lovelyneru.MeowKu.features.modules.client;


import dev.lovelyneru.MeowKu.features.modules.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

public class FPSBypass
extends Module {
    private boolean focused = true;

    public FPSBypass() {
        super("FPSBypass","Fps?", Category.CLIENT,true,false,false);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (Minecraft.getMinecraft().world == null) {
            return;
        }
        if (!Display.isActive() && this.focused) {
            this.focused = false;
            Thread th = new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(500L);
                        Minecraft.getMinecraft().gameSettings.limitFramerate = 3;
                        Display.setTitle("[Unfocused] " + Display.getTitle());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            th.run();
        } else if (Display.isActive() && Minecraft.getMinecraft().gameSettings.limitFramerate == 3) {
            this.focused = true;
            Minecraft.getMinecraft().gameSettings.limitFramerate = 260;
            Display.setTitle(Display.getTitle().replace("[Unfocused] ", ""));
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Minecraft.getMinecraft().gameSettings.limitFramerate = 260;
    }
}
