package sky.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import sky.ctype.MultiCrafter;

import static mindustry.type.ItemStack.*;

public final class SBlocks implements ContentList{
    public static Block
    tinSmelter,
    tinCrusher,
    tinForge;

    @Override
    public void load(){

        tinSmelter = new MultiCrafter("stone-smelter"){{
            requirements(Category.crafting, with(SItems.stone, 120));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 1)).itemRequirements(with(SItems.tinDust, 3)).time(60f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 2)).itemRequirements(with(SItems.tinDust, 6)).time(75f)
            );
            size = 2;
            craftEffect = Fx.pulverizeMedium;
            consumes.power(1.3f);
        }};
    }
}
