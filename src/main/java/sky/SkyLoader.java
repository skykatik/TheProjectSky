package sky;

import arc.util.*;
import arc.util.Log.LogLevel;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.gen.Player;
import mindustry.mod.Mod;
import sky.content.*;

public final class SkyLoader extends Mod{
    private ContentList[] content = {
            new SBlocks(),
            new SItems(),
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

        if(!Vars.headless && !Vars.mobile){
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