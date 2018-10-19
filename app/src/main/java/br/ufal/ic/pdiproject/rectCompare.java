package br.ufal.ic.pdiproject;

import java.util.Comparator;


public class rectCompare implements Comparator<rectPlate> {
    @Override
    public int compare(rectPlate h1, rectPlate h2) {
        Integer vol1 = h1.volume;
        Integer vol2 = h2.volume;
        return vol2.compareTo(vol1);
    }
}
