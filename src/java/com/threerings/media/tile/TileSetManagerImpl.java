//
// $Id: TileSetManagerImpl.java,v 1.12 2001/09/17 05:18:21 mdb Exp $

package com.threerings.media.tile;

import java.awt.Image;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

import com.samskivert.util.*;

import com.threerings.media.ImageManager;
import com.threerings.media.Log;

public abstract class TileSetManagerImpl implements TileSetManager
{
    /**
     * Initialize the <code>TileSetManager</code>.
     *
     * @param config the config object.
     * @param imgmgr the image manager.
     */
    public void init (Config config, ImageManager imgmgr)
    {
	_imgmgr = imgmgr;
        _config = config;
    }

    public int getNumTilesInSet (int tsid)
    {
	TileSet tset = getTileSet(tsid);
	return (tset != null) ? tset.getNumTiles() : -1;
    }

    public TileSet getTileSet (int tsid)
    {
	return (TileSet)_tilesets.get(tsid);
    }

    public Tile getTile (int tsid, int tid)
    {
	TileSet tset = getTileSet(tsid);
	return (tset == null) ? null : tset.getTile(_imgmgr, tid);
    }

    public ArrayList getAllTileSets ()
    {
	ArrayList list = new ArrayList();
	CollectionUtil.addAll(list, _tilesets.elements());
	return list;
    }

    public int getNumTileSets ()
    {
	return _tilesets.size();
    }

    /** The config object. */
    protected Config _config;

    /** The image manager. */
    protected ImageManager _imgmgr;

    /** The available tilesets keyed by tileset id. */
    protected HashIntMap _tilesets = new HashIntMap();
}
