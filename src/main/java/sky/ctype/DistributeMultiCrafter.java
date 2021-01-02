package sky.ctype;

import arc.math.Mathf;
import arc.struct.ObjectIntMap;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.*;
import mindustry.type.*;
import sky.content.SItems;

@Deprecated
public class DistributeMultiCrafter extends AutoMultiCrafter{

    public DistributeMultiCrafter(String name){
        super(name);
    }

    public static class IndexedOutputPlan extends OutputPlan{
        public ObjectIntMap<UnlockableContent> indexes = new ObjectIntMap<>();

        public IndexedOutputPlan(){
            indexes.put(SItems.tinIngot, 1);
            indexes.put(Liquids.water, 2);
        }

        public IndexedOutputPlan index(int index, ItemStack itemStack){
            indexes.put(itemStack.item, index);
            return this;
        }

        public IndexedOutputPlan index(int index, LiquidStack liquidStack){
            indexes.put(liquidStack.liquid, index);
            return this;
        }
    }

    public class DistributeMultiCrafterBuild extends MultiCrafterBuild{

        @Override
        public void dumpLiquid(Liquid liquid){
            if(liquids.get(liquid) > 1.0E-4F){
                if(!Vars.net.client() && Vars.state.isCampaign() && this.team == Vars.state.rules.defaultTeam){
                    liquid.unlock();
                }

                IndexedOutputPlan plan = currentPlan != -1 ? (IndexedOutputPlan)plans.get(currentPlan) : null;
                if(plan != null){
                    LiquidStack s = plan.liquidStack;
                    int d = cdump;
                    if(s != null){
                        d = plan.indexes.get(s.liquid);
                    }

                    incrementDump(proximity.size);
                    Building other = proximity.get(d);
                    if(other != null && other.team == team && other.block.hasLiquids && canDumpLiquid(other, liquid) && other.liquids != null){
                        float ofract = other.liquids.get(liquid) / other.block.liquidCapacity;
                        float fract = liquids.get(liquid) / block.liquidCapacity;
                        if(ofract < fract){
                            transferLiquid(other, (fract - ofract) * block.liquidCapacity / 2.0F, liquid);
                        }
                    }
                }
            }
        }

        @Override
        public boolean dump(Item todump){
            if(block.hasItems && items.total() != 0 && (todump == null || items.has(todump))){
                if(proximity.size != 0){
                    IndexedOutputPlan plan = currentPlan != -1 ? (IndexedOutputPlan)plans.get(currentPlan) : null;
                    if(plan != null){
                        ItemStack s = plan.itemStack;
                        int d = cdump;
                        if(s != null){
                            d = plan.indexes.get(s.item);
                        }
                        Building other = proximity.get(d);
                        if(todump == null){
                            for(int ii = 0; ii < Vars.content.items().size; ++ii){
                                Item item = Vars.content.item(ii);
                                if(other.team == team && items.has(item) && other.acceptItem(this, item) && canDump(other, item)){
                                    other.handleItem(this, item);
                                    items.remove(item, 1);
                                    incrementDump(proximity.size);
                                    return true;
                                }
                            }
                        }else if(other.team == team && other.acceptItem(this, todump) && canDump(other, todump)){
                            other.handleItem(this, todump);
                            items.remove(todump, 1);
                            incrementDump(proximity.size);
                            return true;
                        }

                        this.incrementDump(proximity.size);
                    }
                }
            }
            return false;
        }
    }
}
