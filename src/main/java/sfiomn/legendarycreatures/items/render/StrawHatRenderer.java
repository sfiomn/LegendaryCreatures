package sfiomn.legendarycreatures.items.render;

import sfiomn.legendarycreatures.items.StrawHatItem;
import sfiomn.legendarycreatures.items.render.model.StrawHatModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class StrawHatRenderer extends GeoArmorRenderer<StrawHatItem> {
    public StrawHatRenderer() {
        super(new StrawHatModel());
    }
}
