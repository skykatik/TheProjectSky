package sky.type;

import arc.func.Func;
import arc.scene.ui.layout.Table;
import arc.struct.Bits;
import arc.util.*;
import mindustry.gen.Building;
import mindustry.type.LiquidStack;
import mindustry.ui.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.Stats;
import sky.ctype.MultiCrafter;

@SuppressWarnings("unchecked")
public class ConsumeLiquidDynamic extends Consume{
    public final Func<Building, LiquidStack[]> liquids;

    public <T extends Building> ConsumeLiquidDynamic(Func<T, LiquidStack[]> liquids){
        this.liquids = (Func<Building, LiquidStack[]>)liquids;
    }

    @Override
    public void applyItemFilter(Bits filter){
        //no-op
    }

    @Override
    public ConsumeType type(){
        return ConsumeType.liquid;
    }

    @Override
    public void build(Building tile, Table table){
        LiquidStack[][] current = {liquids.get(tile)};

        table.table(cont -> {
            table.update(() -> {
                if(current[0] != liquids.get(tile)){
                    rebuild(tile, cont);
                    current[0] = liquids.get(tile);
                }
            });

            rebuild(tile, cont);
        });
    }

    private void rebuild(Building tile, Table table){
        table.clear();
        int i = 0;

        for(LiquidStack stack : liquids.get(tile)){
            table.add(new ReqImage(new ItemImage(stack.liquid.icon(Cicon.medium), (int)stack.amount),
                                   () -> tile.items != null && tile.liquids.get(stack.liquid) >= stack.amount)).padRight(8).left();

            if(++i % 4 == 0){
                table.row();
            }
        }
    }

    @Override
    public String getIcon(){
        return "icon-liquid-consume";
    }

    @Override
    public void update(Building entity){
        //no-op
    }

    @Override
    public void trigger(Building entity){
        for(LiquidStack stack : liquids.get(entity)){
            entity.liquids.remove(stack.liquid, stack.amount);
        }
    }

    @Override
    public boolean valid(Building entity){
        if(entity instanceof MultiCrafter.MultiCrafterBuild){
            MultiCrafter.MultiCrafterBuild build = (MultiCrafter.MultiCrafterBuild)entity;
            MultiCrafter multiCrafter = (MultiCrafter)entity.block;
            return build.currentPlan != -1 && !Structs.contains(multiCrafter.plans.get(build.currentPlan).liquidRequirements, stack -> build.liquids.get(stack.liquid) < stack.amount);
        }
        return entity.liquids != null && Structs.contains(liquids.get(entity), l -> entity.liquids.get(l.liquid) > l.amount);
    }

    @Override
    public void display(Stats stats){
        //no-op
    }
}
