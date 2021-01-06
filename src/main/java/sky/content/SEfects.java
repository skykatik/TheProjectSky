package sky.content;

import arc.math.Mathf;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.StatusEffect;
import sky.Accents;

public class SEfects implements ContentList{
    public static StatusEffect
    steam;

    @Override
    public void load(){

        steam = new StatusEffect("steam"){{
            color = Accents.steam;
            damage = 0.3f;
            effect = Fx.smokeCloud;
            init(() -> {
                opposite(StatusEffects.wet, StatusEffects.freezing);
                trans(StatusEffects.none, (unit, time, newTime, result) -> {
                    unit.damagePierce(8.0f);
                    Fx.smokeCloud.at(unit.x + Mathf.range(unit.bounds() / 2.0f), unit.y + Mathf.range(unit.bounds() / 2.0f));
                    result.set(this, Math.min(time + newTime, 300.0f));
                });
            });
        }};
    }
}
