//
// $Id: FringeConfiguration.java,v 1.9 2002/04/08 19:41:52 ray Exp $

package com.threerings.miso.tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.samskivert.util.HashIntMap;

/**
 * Used to manage data about which base tilesets fringe on which others
 * and how they fringe.
 */
public class FringeConfiguration implements Serializable
{
    /**
     * The path (relative to the resource directory) at which the fringe
     * configuration should be loaded and stored.
     */
    public static final String CONFIG_PATH = "config/miso/scene/fringeconf.dat";

    public static class FringeRecord implements Serializable
    {
        /** The tileset id of the base tileset to which this applies. */
        public int base_tsid;

        /** The fringe priority of this base tileset. */
        public int priority;

        /** A list of the possible tilesets that can be used for fringing. */
        public ArrayList tilesets = new ArrayList();

        /** Used when parsing the tilesets definitions. */
        public void addTileset (FringeTileSetRecord record)
        {
            tilesets.add(record);
        }

        /** Did everything parse well? */
        public boolean isValid ()
        {
            return ((base_tsid != 0) && (priority > 0));
        }
     }

    /**
     * Used to parse the tileset fringe definitions.
     */
    public static class FringeTileSetRecord implements Serializable
    {
        /** The tileset id of the fringe tileset. */
        public int fringe_tsid;

        /** Is this a mask? */
        public boolean mask;

        /** Did everything parse well? */
        public boolean isValid ()
        {
            return (fringe_tsid != 0);
        }
    }

    /**
     * Adds a parsed FringeRecord to this instance. This is used when parsing
     * the fringerecords from xml.
     */
    public void addFringeRecord (FringeRecord frec)
    {
        _frecs.put(frec.base_tsid, frec);
    }

    /**
     * If the first base tileset fringes upon the second, return the
     * fringe priority of the first base tileset, otherwise return -1.
     */
    public int fringesOn (int first, int second)
    {
        FringeRecord f1 = (FringeRecord) _frecs.get(first);

        // we better have a fringe record for the first
        if (null != f1) {

            // it had better have some tilesets defined
            if (f1.tilesets.size() > 0) {

                FringeRecord f2 = (FringeRecord) _frecs.get(second);

                // and we only fringe if second doesn't exist or has a lower
                // priority
                if ((null == f2) || (f1.priority > f2.priority)) {
                    return f1.priority;
                }
            }
        }

        return -1;
    }

    /**
     * Get a random FringeTileSetRecord from amongst the ones
     * listed for the specified base tileset.
     */
    public FringeTileSetRecord getRandomFringe (int baseset, Random rando)
    {
        FringeRecord f = (FringeRecord) _frecs.get(baseset);

        int size = f.tilesets.size();
        return (FringeTileSetRecord) f.tilesets.get(rando.nextInt(size));
    }

    /** The mapping from base tileset id to fringerecord. */
    protected HashIntMap _frecs = new HashIntMap();
}
