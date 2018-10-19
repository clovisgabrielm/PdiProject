package br.ufal.ic.pdiproject;

import org.opencv.core.Rect;

import java.util.Comparator;

public class customCompare implements Comparator<Rect> {
    @Override
    public int compare(Rect r1, Rect r2) {
        Integer Area1 = r1.width * r1.height;
        Integer Area2 = r2.width * r2.height;
        return Area2.compareTo(Area1);
    }
}
