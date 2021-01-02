package sky.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.draw.DrawRotator;
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
            requirements(Category.crafting, with(SItems.stone, 130));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 1)).itemRequirements(with(SItems.tinRaw, 4)).time(35f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 2)).itemRequirements(with(SItems.tinDust, 6)).time(55f)
            );
            size = 2;
            itemCapacity = 3;
            hasPower = false;
            craftEffect = Fx.pulverizeMedium;
        }};

        tinForge = new AutoMultiCrafter("stone-forge"){{
            requirements(Category.crafting, with(SItems.stone, 260, SItems.tinIngot, 25, Items.copper, 50));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinPlate, 1)).itemRequirements(with(SItems.tinIngot, 2)).time(45f)
            );
            size = 2;
            itemCapacity = 3;
            hasPower = false;
            craftEffect = Fx.pulverizeMedium;
        }};

        tinForge = new AutoMultiCrafter("stone-crusher"){{
            requirements(Category.crafting, with(SItems.stone, 150, SItems.tinIngot, 30, Items.copper, 30, Items.lead, 30));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinDust, 2)).itemRequirements(with(SItems.tinRaw, 1)).time(45f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinDust, 3)).itemRequirements(with(SItems.tinIngot, 1)).time(85f)
            );
            size = 1;
            itemCapacity = 3;
            hasPower = false;
            craftEffect = Fx.pulverizeSmall;
        }};
    }
}
