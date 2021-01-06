package sky;

import arc.Events;
import arc.math.Mathf;
import arc.util.*;
import arc.util.Log.LogLevel;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.mod.Mod;
import mindustry.world.blocks.liquid.Conduit;
import sky.content.*;

import static mindustry.Vars.*;

public final class SkyLoader extends Mod{
    private final ContentList[] content = {
            new SEfects(),
            new SItems(),
            new SLiquids(),
            new SBlocks(),
            new SOres(),
            new SPlanets()
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
        }
    }
}
