package sky.content;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.ctype.ContentList;
import mindustry.graphics.g3d.HexMesh;
import mindustry.type.Planet;
import sky.generators.SkylandPlanetGenerator;

public final class SPlanets implements ContentList{
    public static Planet
    skyland;

    @Override
    public void load(){

        // skyland = new Planet("skyland", Planets.serpulo, 4, 1.5f){{
        //     generator = new SkylandPlanetGenerator();
        //     meshLoader = () -> new HexMesh(this, 6);
        //     atmosphereColor = Color.valueOf("004d99");
        //     atmosphereRadIn = 0.02f;
        //     atmosphereRadOut = 0.3f;
        //     startSector = 13;
        // }};
    }
}
