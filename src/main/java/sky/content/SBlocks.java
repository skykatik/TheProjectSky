package sky.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.Block;
import sky.ctype.*;
import sky.draw.forward.ForwardDrawRotator;

import static mindustry.type.ItemStack.with;

public final class SBlocks implements ContentList{
    public static Block
    tinConveyor,

    stoneSmelter,
    tinSmelter,
    tinCrusher,
    tinForge;

    @Override
    public void load(){

        stoneSmelter = new AutoMultiSmelter("stone-smelter"){{
            requirements(Category.crafting, with(SItems.stone, 130));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 1)).itemRequirements(with(SItems.tinRaw, 4)).time(45f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinIngot, 2)).itemRequirements(with(SItems.tinDust, 6)).time(65f)
            );
            size = 2;
            itemCapacity = 3;
            hasPower = false;
            hasLiquids = false;
            craftEffect = Fx.pulverizeMedium;
        }};

        tinForge = new AutoMultiCrafter("tin-forge"){{
            requirements(Category.crafting, with(SItems.stone, 260, SItems.tinIngot, 25, Items.copper, 50));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinPlate, 1)).itemRequirements(with(SItems.tinIngot, 2)).time(65f)
            );
            size = 2;
            itemCapacity = 3;
            hasPower = false;
            hasLiquids = false;
            craftEffect = Fx.pulverizeMedium;
        }};

        tinForge = new AutoMultiCrafter("tin-crusher"){{
            requirements(Category.crafting, with(SItems.stone, 150, SItems.tinIngot, 30, Items.copper, 30, Items.lead, 30));
            plans = Seq.with(
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinDust, 2)).itemRequirements(with(SItems.tinRaw, 1)).time(65f),
                    OutputPlan.create().itemStack(new ItemStack(SItems.tinDust, 3)).itemRequirements(with(SItems.tinIngot, 1)).time(95f)
            );
            size = 1;
            itemCapacity = 3;
            hasPower = false;
            hasLiquids = false;
            drawer = new ForwardDrawRotator();
            craftEffect = Fx.pulverize;
            updateEffect = Fx.pulverizeSmall;
            ambientSound = Sounds.grinding;
            ambientSoundVolume = 0.025f;
        }};
    }
}
