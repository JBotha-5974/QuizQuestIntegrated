package wrrv.quizquest;

import java.util.Comparator;

public class ItemLayerComparator implements Comparator<Item>
{
    @Override
    public int compare(Item item1, Item item2) {

        return Integer.compare(item1.getLayer(), item2.getLayer());
    }
}
