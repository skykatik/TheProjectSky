package sky.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.world.blocks.environment.OreBlock;

public final class SOres implements ContentList{
    public static OreBlock
    stone,

    tinOre;

    @Override
    public void load(){

        stone = new OreBlock(SItems.stone){{
            oreDefault = true;
            oreThreshold = 0.84f;
            oreScale = 23.23f;
        }};

        tinOre = new OreBlock(SItems.tinRaw){{
            oreDefault = true;
            oreThreshold = 0.85f;
            oreScale = 24.428f;
        }};
    }
}
