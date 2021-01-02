package sky.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import sky.ctype.*;

import static mindustry.type.ItemStack.*;

public final class SBlocks implements ContentList{
    public static Block
    tinSmelter,
    tinCrusher,
    tinForge;

    @Override
    public void load(){

        tinSmelter = new AutoMultiCrafter("stone-smelter"){{
            requirements(Category.crafting, with(SItems.stone, 120));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 1)).itemRequirements(with(SItems.tinDust, 3)).time(60f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 2)).itemRequirements(with(SItems.tinDust, 6)).time(75f),
                    OutputPlan.create().liquidStack(new LiquidStack(Liquids.water, 2f)).itemStack(new ItemStack(SItems.tinIngot, 1))
                            .itemRequirements(with(SItems.stone, 6)).time(75f)
                    .liquidRequirements(new LiquidStack(Liquids.slag, 1f))
            );
            size = 2;
            craftEffect = Fx.pulverizeMedium;
            consumes.power(1.3f);
        }};
    }
}
