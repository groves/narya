//
// $Id: TileSetManager.java,v 1.4 2001/07/18 21:45:42 shaper Exp $

package com.threerings.miso.tile;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

public interface TileSetManager
{
    /**
     * Return the tileset object corresponding to the specified tileset id.
     */
    public TileSet getTileSet (int tsid);

    /**
     * Return a list of all tilesets available for use.
     */
    public ArrayList getTileSets ();

    /**
     * Return the total number of tilesets.
     */
    public int getNumTileSets ();

    /**
     * Load the tilesets described in the specified input stream into
     * the set of available tilesets.
     */
    public void loadTileSets (InputStream tis) throws IOException;
}
