package sky.content;

import mindustry.ctype.ContentList;
import mindustry.type.Item;
import sky.Accents;
import sky.ctype.Dust;

public final class SItems implements ContentList{
    public static Item
    stone,

    tinRaw,
    tinDust,
    tinIngot,
    tinPlate,

    bronzeDust,
    bronzeIngot,
    bronzePlate;

    @Override
    public void load(){

        stone = new Item("stone", Accents.stone){{
            cost = 0.7f;
        }};

        // // // //

        tinRaw = new Dust("tin-raw", Accents.tin){{
            hardness = 1;
        }};

        tinDust = new Dust("tin-dust", Accents.tin){{

        }};

        tinIngot = new Item("tin-ingot", Accents.tin){{

        }};

        tinPlate = new Item("tin-plate", Accents.tin){{

        }};

        // // // //

        bronzeDust = new Dust("bronze-dust", Accents.bronze){{

        }};

        bronzeIngot = new Item("bronze-ingot", Accents.bronze){{

        }};

        bronzePlate = new Item("bronze-plate", Accents.bronze){{

        }};
    }
}
