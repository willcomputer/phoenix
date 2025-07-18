package interactable.items;

import interactable.ParentInteractable;
import util.UtilityFunctions;

public class Chest extends ParentInteractable {

    public Chest(String name, int x, int y, boolean collision) {
        super(name, x, y, collision);
        super.setName("Chest");
        super.setImage(UtilityFunctions.readImageFile("./assets/items/", "chest", ".png"));
    }

    @Override
    public void handleCollision() {

    }

    @Override
    public void handleInteraction() {

    }

    @Override
    public String getInteractMessage() {
        return "Chest Ahead";
    }
}
