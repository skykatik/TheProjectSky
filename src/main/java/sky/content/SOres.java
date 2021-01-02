package sky.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.world.blocks.environment.OreBlock;

public final class SOres implements ContentList{
    public static OreBlock
    stone;

    @Override
    public void load(){

        stone = new OreBlock(SItems.stone){{

        }};
    }
}
