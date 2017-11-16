package com.github.ocraft.s2client.protocol.observation.raw;

import SC2APIProtocol.Raw;
import com.github.ocraft.s2client.protocol.Strings;
import com.github.ocraft.s2client.protocol.observation.spatial.ImageData;

import java.io.Serializable;

import static com.github.ocraft.s2client.protocol.DataExtractor.tryGet;
import static com.github.ocraft.s2client.protocol.Errors.required;
import static com.github.ocraft.s2client.protocol.Preconditions.require;

public final class MapState implements Serializable {

    private static final long serialVersionUID = -4361255173604238600L;

    private final ImageData visibility;
    private final ImageData creep;

    private MapState(Raw.MapState sc2ApiMapState) {
        visibility = tryGet(
                Raw.MapState::getVisibility, Raw.MapState::hasVisibility
        ).apply(sc2ApiMapState).map(ImageData::from).orElseThrow(required("visibility"));

        creep = tryGet(
                Raw.MapState::getCreep, Raw.MapState::hasCreep
        ).apply(sc2ApiMapState).map(ImageData::from).orElseThrow(required("creep"));
    }

    public static MapState from(Raw.MapState sc2ApiMapState) {
        require("sc2api map state", sc2ApiMapState);
        return new MapState(sc2ApiMapState);
    }

    public ImageData getVisibility() {
        return visibility;
    }

    public ImageData getCreep() {
        return creep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapState mapState = (MapState) o;

        return visibility.equals(mapState.visibility) && creep.equals(mapState.creep);
    }

    @Override
    public int hashCode() {
        int result = visibility.hashCode();
        result = 31 * result + creep.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Strings.toJson(this);
    }
}