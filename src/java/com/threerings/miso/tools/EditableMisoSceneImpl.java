//
// $Id: EditableMisoSceneImpl.java,v 1.23 2002/09/23 21:54:50 mdb Exp $

package com.threerings.miso.scene.tools;

import java.awt.Point;
import java.awt.Rectangle;

import java.util.HashMap;
import java.util.Iterator;

import com.threerings.media.tile.NoSuchTileException;
import com.threerings.media.tile.NoSuchTileSetException;
import com.threerings.media.tile.ObjectTile;
import com.threerings.media.tile.Tile;
import com.threerings.media.tile.TileUtil;

import com.threerings.miso.Log;

import com.threerings.miso.tile.BaseTile;
import com.threerings.miso.tile.BaseTileSet;
import com.threerings.miso.tile.MisoTileManager;

import com.threerings.miso.scene.DisplayMisoSceneImpl;
import com.threerings.miso.scene.MisoSceneModel;
import com.threerings.miso.scene.SceneObject;
import com.threerings.miso.scene.util.IsoUtil;

/**
 * The default implementation of the {@link EditableMisoScene} interface.
 */
public class EditableMisoSceneImpl
    extends DisplayMisoSceneImpl implements EditableMisoScene
{
    /**
     * Constructs an instance that will be used to display and edit the
     * supplied miso scene data. The tiles identified by the scene model
     * will be loaded via the supplied tile manager.
     *
     * @param model the scene data that we'll be displaying.
     * @param tmgr the tile manager from which to load our tiles.
     */
    public EditableMisoSceneImpl (MisoSceneModel model, MisoTileManager tmgr)
    {
        super(model, tmgr);
    }

    /**
     * Constructs an instance that will be used to display and edit the
     * supplied miso scene data. The tiles identified by the scene model
     * will not be loaded until a tile manager is provided via {@link
     * #setTileManager}.
     *
     * @param model the scene data that we'll be displaying.
     */
    public EditableMisoSceneImpl (MisoSceneModel model)
    {
        super(model);
    }

    // documentation inherited
    public void setMisoSceneModel (MisoSceneModel model)
    {
        super.setMisoSceneModel(model);
    }

    // documentation inherited
    public BaseTileSet getDefaultBaseTileSet ()
    {
        return _defaultBaseTileSet;
    }

    // documentation inherited
    public void setDefaultBaseTileSet (BaseTileSet defaultBaseTileSet,
                                       int setId)
    {
        _defaultBaseTileSet = defaultBaseTileSet;
        _defaultBaseTileSetId = setId;
    }

    // documentation inherited
    public void setBaseTiles (Rectangle r, BaseTileSet set, int setId)
    {
        int setcount = set.getTileCount();

        for (int x = r.x; x < r.x + r.width; x++) {
            for (int y = r.y; y < r.y + r.height; y++) {
                try {
                    int index = _rando.nextInt(setcount);
                    _base.setTile(x, y, (BaseTile) set.getTile(index));
                    _model.setBaseTile(
                        x, y, TileUtil.getFQTileId(setId, index));

                } catch (NoSuchTileException nste) {
                    // not going to happen
                    Log.warning("A tileset is lying to me " +
                                "[error=" + nste + "].");
                }
            }
        }

        _fringer.fringe(_model, _fringe, r, _rando);
    }

    // documentation inherited
    public void setBaseTile (int x, int y, BaseTile tile, int fqTileId)
    {
        _base.setTile(x, y, tile);
        _model.setBaseTile(x, y, fqTileId);
        _fringer.fringe(_model, _fringe, new Rectangle(x, y, 1, 1), _rando);
    }

    // documentation inherited
    public SceneObject addSceneObject (
        ObjectTile tile, int x, int y, int fqTileId)
    {
        // create a scene object record and add it to the list
        EditableSceneObject scobj = (EditableSceneObject)
            createSceneObject(x, y, tile);
        scobj.fqTileId = fqTileId;
        scobj.index = _objects.size();
        _objects.add(scobj);

        // toggle the "covered" flag on in all base tiles below this
        // object tile
        setObjectTileFootprint(tile, x, y, true);

        return scobj;
    }

    // documentation inherited
    public void clearBaseTile (int x, int y)
    {
        // implemented as a set of one of the random base tiles
        setBaseTiles(new Rectangle(x, y, 1, 1),
                     _defaultBaseTileSet, _defaultBaseTileSetId);
    }

    // documentation inherited
    public boolean removeSceneObject (SceneObject scobj)
    {
        if (_objects.remove(scobj)) {
            // toggle the "covered" flag off on the base tiles in this object
            // tile's footprint
            setObjectTileFootprint(scobj.tile, scobj.x, scobj.y, false);
            return true;
        } else {
            return false;
        }
    }

    // documentation inherited
    public MisoSceneModel getMisoSceneModel ()
    {
        // we need to flush the object layer to the model prior to
        // returning it
        int ocount = _objects.size();

        // but only do it if we've actually got some objects
        if (ocount > 0) {
            int[] otids = new int[ocount*3];
            String[] actions = new String[ocount];

            for (int ii = 0; ii < ocount; ii++) {
                EditableSceneObject scobj = (EditableSceneObject)
                    _objects.get(ii);
                otids[3*ii] = scobj.x;
                otids[3*ii+1] = scobj.y;
                otids[3*ii+2] = scobj.fqTileId;
                actions[ii] = scobj.action;
            }

            // stuff the new arrays into the model
            _model.objectTileIds = otids;
            _model.objectActions = actions;
        }

        // and we're ready to roll
        return _model;
    }

    // documentation inherited
    protected SceneObject createSceneObject (int x, int y, ObjectTile tile)
    {
        return new EditableSceneObject(x, y, tile);
    }

    // documentation inherited
    protected SceneObject expandObject (
        int col, int row, int tsid, int tid, int fqTid, String action)
        throws NoSuchTileException, NoSuchTileSetException
    {
        // do the actual object creation
        EditableSceneObject scobj = (EditableSceneObject)
            super.expandObject(col, row, tsid, tid, fqTid, action);

        // we need this to track object layer mods
        scobj.fqTileId = fqTid;

        // pass on the objecty goodness
        return scobj;
    }

    /** Used to report information on objects in this scene. */
    protected static class EditableSceneObject extends SceneObject
    {
        public int fqTileId;

        public EditableSceneObject (int x, int y, ObjectTile tile)
        {
            super(x, y, tile);
        }
    }

    /** The default tileset with which to fill the base layer. */
    protected BaseTileSet _defaultBaseTileSet;

    /** The tileset id of the default base tileset. */
    protected int _defaultBaseTileSetId;
}
