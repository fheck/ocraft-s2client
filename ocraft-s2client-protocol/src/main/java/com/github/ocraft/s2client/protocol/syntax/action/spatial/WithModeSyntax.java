package com.github.ocraft.s2client.protocol.syntax.action.spatial;

import com.github.ocraft.s2client.protocol.action.spatial.ActionSpatialUnitSelectionPoint;

public interface WithModeSyntax {
    ActionSpatialUnitSelectionPointBuilder withMode(ActionSpatialUnitSelectionPoint.Type type);
}