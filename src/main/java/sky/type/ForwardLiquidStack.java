package sky.type;

import mindustry.type.*;

public final class ForwardLiquidStack{
    public static final LiquidStack[] empty = new LiquidStack[0];

    public static LiquidStack[] with(Object... liquids) {
        LiquidStack[] stacks = new LiquidStack[liquids.length / 2];

        for(int i = 0; i < liquids.length; i += 2) {
            stacks[i / 2] = new LiquidStack((Liquid)liquids[i], ((Number)liquids[i + 1]).floatValue());
        }

        return stacks;
    }
}
