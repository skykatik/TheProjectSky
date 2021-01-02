package sky.draw.forward;

import arc.graphics.g2d.Draw;
import mindustry.world.draw.DrawBlock;
import sky.ctype.AutoMultiCrafter;

public class ForwardDrawBlock extends DrawBlock{

    public void draw(AutoMultiCrafter.MultiCrafterBuild entity){
        Draw.rect(entity.block.region, entity.x, entity.y, entity.block.rotate ? entity.rotdeg() : 0.0F);
    }
}
