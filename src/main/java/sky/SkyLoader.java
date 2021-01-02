package sky;

import arc.util.*;
import arc.util.Log.LogLevel;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.gen.*;
import mindustry.mod.Mod;
import mindustry.world.Tile;
import sky.content.*;

import static mindustry.Vars.*;

public final class SkyLoader extends Mod{
    private ContentList[] content = {
            new SItems(),
            new SBlocks(),
            new SOres()
    };

    @Override
    public void loadContent(){
        for(ContentList contentList : content){
            contentList.load();
        }
    }

    @Override
    public void registerClientCommands(CommandHandler handler){

        if(!headless && !mobile){
            handler.<Player>register("log-level", "[debug/info/warn/err/none]", "Toggle logging level.", (args, player) -> {
                LogLevel level;
                try{
                    level = args.length > 0 ? LogLevel.valueOf(args[0]) : LogLevel.info;
                }catch(Throwable t){
                    player.sendMessage("[scarlet]Incorrect level name.");
                    return;
                }

                Log.level = level;
                player.sendMessage("[lightgray]Level set to [orange]" + level);
            });

            handler.<Player>register("deb", "Toggle logging level.", (args, player) -> {
                Tile t = world.tileWorld(player.mouseX, player.mouseY);
                for(Building b : t.build.proximity){
                    player.sendMessage(Strings.format("i: @ | x: @ | y: @", t.build.proximity.indexOf(b), b.tileX(), b.tileY()));
                }
            });
        }
    }
}
