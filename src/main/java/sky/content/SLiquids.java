package sky.content;

import mindustry.ctype.ContentList;
import mindustry.type.Liquid;
import sky.Accents;

public class SLiquids implements ContentList{
    public static Liquid
    steam;

    @Override
    public void load(){

        steam = new Liquid("steam", Accents.steam){{
            temperature = 0.85f;
            viscosity = 0.0f;
            effect = SEfects.steam;
            lightColor = Accents.steam.a(0.0f);
        }};
    }
}
