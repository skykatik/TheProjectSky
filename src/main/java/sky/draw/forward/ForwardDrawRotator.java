package sky.draw.forward;

import arc.Core;
import arc.graphics.g2d.*;
import mindustry.world.Block;
import sky.ctype.AutoMultiCrafter;

public final class ForwardDrawRotator extends ForwardDrawBlock{
    public TextureRegion rotator;

    @Override
    public void draw(AutoMultiCrafter.MultiCrafterBuild entity){
        Draw.rect(entity.block.region, entity.x, entity.y);
        Draw.rect(rotator, entity.x, entity.y, entity.progress * 2f);
    }

    @Override
    public void load(Block block){
        rotator = Core.atlas.find(block.name + "-rotator");
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{block.region, rotator};
    }
}
