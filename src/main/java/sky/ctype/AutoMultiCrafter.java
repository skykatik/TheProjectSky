package sky.ctype;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.Block;
import mindustry.world.consumers.ConsumeItemDynamic;
import mindustry.world.meta.*;
import sky.draw.forward.ForwardDrawBlock;
import sky.type.*;

import static mindustry.Vars.*;

/**
 * Auto because he find configuration plan by input resources
 */
public class AutoMultiCrafter extends Block{
    public float[] capacities;

    public float craftTime = 80;
    public Effect craftEffect = Fx.none;
    public Effect updateEffect = Fx.none;

    public ForwardDrawBlock drawer = new ForwardDrawBlock();
    public Seq<OutputPlan> plans = new Seq<>();

    public AutoMultiCrafter(String name){
        super(name);
        update = true;
        solid = true;
        hasPower = true;
        hasItems = true;
        hasLiquids = true;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);

        if(plans.contains(o -> o.getItem() != null)){
            consumes.add(new ConsumeItemDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? plans.get(e.currentPlan).itemRequirements : ItemStack.empty));
        }

        if(plans.contains(o -> o.getLiquid() != null)){
            consumes.add(new ConsumeLiquidDynamic((MultiCrafterBuild e) -> plans.get(1).liquidRequirements));
        }
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.itemCapacity);

        stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);

        stats.add(Stat.output, table -> {
            Seq<OutputPlan> unlocked = plans.select(p -> p.getLiquid() != null ? p.getLiquid().unlockedNow() : p.getItem() != null && p.getItem().unlockedNow());
            table.row();
            unlocked.each(p -> {
                if(p.getItem() != null){
                    table.add(new ItemDisplay(p.getItem(), p.getItems())).right();
                }

                if(p.getLiquid() != null){
                    table.add(new LiquidDisplay(p.getLiquid(), p.getLiquidCount(), true)).right();
                }
                table.row();
            });
        });
    }

    @Override
    public void load(){
        super.load();

        drawer.load(this);
    }

    @Override
    public void init(){
        capacities = new float[content.liquids().size + content.items().size];
        for(OutputPlan plan : plans){
            for(ItemStack stack : plan.itemRequirements){
                capacities[stack.item.id] = Math.max(capacities[stack.item.id], stack.amount * 2);
                itemCapacity = Math.max(itemCapacity, stack.amount * 2);
            }

            for(LiquidStack stack : plan.liquidRequirements){
                capacities[stack.liquid.id] = Math.max(capacities[stack.liquid.id], stack.amount * 2);
                liquidCapacity = Math.max(itemCapacity, stack.amount * 2);
            }
        }

        if(plans.contains(o -> o.getItem() != null)){
            consumes.add(new ConsumeItemDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? plans.get(e.currentPlan).itemRequirements : ItemStack.empty));
        }

        if(plans.contains(o -> o.getLiquid() != null)){
            consumes.add(new ConsumeLiquidDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? plans.get(e.currentPlan).liquidRequirements : ForwardLiquidStack.empty));
        }

        outputsLiquid = plans.contains(o -> o.getLiquid() != null);

        super.init();
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("progress", (MultiCrafterBuild e) -> new Bar("bar.progress", Pal.ammo, e::fraction));
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.icons(this);
    }

    @Override
    public boolean outputsItems(){
        return plans.contains(o -> o.getItem() != null);
    }

    public static class OutputPlan{
        public LiquidStack liquidStack;
        public ItemStack itemStack;

        public ItemStack[] itemRequirements = ItemStack.empty;
        public LiquidStack[] liquidRequirements = ForwardLiquidStack.empty;
        public float time;

        public static OutputPlan create(){
            return new OutputPlan();
        }

        public OutputPlan liquidStack(LiquidStack liquidStack){
            this.liquidStack = liquidStack;
            return this;
        }

        public OutputPlan itemStack(ItemStack itemStack){
            this.itemStack = itemStack;
            return this;
        }

        public OutputPlan itemRequirements(ItemStack... itemRequirements){
            this.itemRequirements = itemRequirements;
            return this;
        }

        public OutputPlan liquidRequirements(LiquidStack... liquidRequirements){
            this.liquidRequirements = liquidRequirements;
            return this;
        }

        public OutputPlan time(float time){
            this.time = time;
            return this;
        }

        public Item getItem(){
            return itemStack != null ? itemStack.item : null;
        }

        public Liquid getLiquid(){
            return liquidStack != null ? liquidStack.liquid : null;
        }

        public int getItems(){
            return itemStack != null ? itemStack.amount : 0;
        }

        public float getLiquidCount(){
            return liquidStack != null ? liquidStack.amount : 0.0f;
        }
    }

    public class MultiCrafterBuild extends Building{
        public float progress, time, speedScl;
        public int currentPlan = -1;

        public float fraction(){
            return currentPlan == -1 ? 0 : progress / plans.get(currentPlan).time;
        }

        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public Object config(){
            return currentPlan;
        }

        @Override
        public void display(Table table){
            super.display(table);

            TextureRegionDrawable reg = new TextureRegionDrawable();

            table.row();
            table.table(t -> {
                t.left();
                t.image().update(i -> {
                    i.setDrawable(currentPlan == -1 ? Icon.cancel : reg.set(plans.get(currentPlan).getItem() != null ? plans.get(currentPlan).getItem().icon(Cicon.medium) : plans.get(currentPlan).getLiquid().icon(Cicon.medium)));
                    i.setScaling(Scaling.fit);
                    i.setColor(currentPlan == -1 ? Color.lightGray : Color.white);
                }).size(32).padBottom(-4).padRight(2);
                t.label(() -> currentPlan == -1 ? "@none" : plans.get(currentPlan).getItem() != null ? plans.get(currentPlan).getItem().localizedName : plans.get(currentPlan).getLiquid().localizedName).wrap().width(230f).color(Color.lightGray);
            }).left();
        }

        @Override
        public void updateTile(){
            if(currentPlan < 0 || currentPlan >= plans.size){
                currentPlan = -1;
            }

            if(consValid() && currentPlan != -1){
                time += edelta() * speedScl * state.rules.unitBuildSpeedMultiplier;
                progress += edelta() * state.rules.unitBuildSpeedMultiplier;
                speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
            }else{
                speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
            }

            if(currentPlan != -1){
                OutputPlan plan = plans.get(currentPlan);

                if(progress >= plan.time && consValid()){
                    if(plan.getItem() != null){
                        for(int i = 0; i < plan.getItems(); i++){
                            offload(plan.getItem());
                        }
                    }

                    if(plan.getLiquid() != null){
                        liquids.add(plan.getLiquid(), plan.getLiquidCount());
                    }

                    craftEffect.at(x, y);
                    progress = 0f;
                    consume();
                }

                if(plan.getItem() != null && timer(timerDump, dumpTime)){
                    dump(plan.getItem());
                }

                if(plan.getLiquid() != null){
                    dumpLiquid(plan.getLiquid());
                }

                progress = Mathf.clamp(progress, 0, plan.time);
            }else{
                progress = 0f;
            }
        }

        public boolean empty(){
            if(currentPlan == -1) return false;

            Liquid liquid = plans.get(currentPlan).getLiquid();
            if(liquid != null){
                return !(liquids.get(liquid) >= liquidCapacity - 0.01f);
            }

            Item item = plans.get(currentPlan).getItem();
            if(item != null){
                return items.get(item) == 0;
            }

            return false;
        }

        @Override
        public int getMaximumAccepted(Item item){
            return (int)capacities[item.id];
        }

        private float getMaximumAccepted(Liquid liquid){
            return capacities[liquid.id];
        }

        @Override
        public boolean shouldConsume(){
            return enabled && empty();
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            OutputPlan plan = plans.find(o -> Structs.contains(o.itemRequirements, i -> i.item.equals(item)));
            if(plan != null){
                currentPlan = plans.indexOf(plan);
            }
            return plan != null && items.get(item) < getMaximumAccepted(item) &&
                   Structs.contains(plan.itemRequirements, stack -> stack.item == item);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            OutputPlan plan = plans.find(o -> Structs.contains(o.liquidRequirements, l -> l.liquid.equals(liquid)));
            if(plan != null){
                currentPlan = plans.indexOf(plan);
            }
            return plan != null && liquids.get(liquid) < getMaximumAccepted(liquid) &&
                   Structs.contains(plan.liquidRequirements, stack -> stack.liquid == liquid);
        }

        @Override
        public boolean shouldAmbientSound(){
            return cons.valid();
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
            write.f(currentPlan);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
            currentPlan = read.s();
        }
    }
}
