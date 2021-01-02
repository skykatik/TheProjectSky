package sky.content;

import arc.KeyBinds;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import sky.ctype.MultiCrafter;

import static mindustry.type.ItemStack.*;

public class SBlocks implements ContentList{
    public static Block
    tinSmelter,
    tinCrusher,
    tinForge;

    @Override
    public void load(){

        tinSmelter = new MultiCrafter("tin-smelter"){{
            requirements(Category.crafting, with(Items.copper, 50, Items.lead, 120, Items.silicon, 80));
            plans = Seq.with(
                    new OutputPlan(new ItemStack(Items.copper, 2), with(Items.lead, 10), new LiquidStack[]{new LiquidStack(Liquids.water, 10)}, 60f),
                    new OutputPlan(new LiquidStack(Liquids.oil, 10.0f), with(Items.lead, 10), new LiquidStack[]{new LiquidStack(Liquids.water, 10), new LiquidStack(Liquids.slag, 10)}, 60f)
            );
            size = 3;
            consumes.power(1.2f);
        }};
    }
}
