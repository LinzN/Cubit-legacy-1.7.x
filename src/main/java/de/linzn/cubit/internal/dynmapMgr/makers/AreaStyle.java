package de.linzn.cubit.internal.dynmapMgr.makers;


import de.linzn.cubit.internal.regionMgr.LandTypes;

public class AreaStyle {

    String fillColor;
    double fillTransparency;

    public AreaStyle(LandTypes landType) {
        switch (landType) {
            case SERVER:
                fillColor = "#9c27b0";
                fillTransparency = 0.55;
                break;
            case SHOP:
                fillColor = "#275db0";
                fillTransparency = 0.55;
                break;
            case WORLD:
                fillColor = "#a70000";
                fillTransparency = 0.35;
                break;
            case NOTYPE:
                fillColor = "#a70000";
                fillTransparency = 0.35;
                break;
        }

    }
}