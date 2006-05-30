//
// $Id: ClusteredBodyObject.java 3310 2005-01-24 23:08:21Z mdb $
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2004 Three Rings Design, Inc., All Rights Reserved
// http://www.threerings.net/code/narya/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.whirled.spot.data {

import com.threerings.whirled.data.ScenedBodyObject;

/**
 * Defines some required methods for a {@link ScenedBodyObject} that is to
 * participate in the Whirled Spot system.
 */
public interface ClusteredBodyObject extends ScenedBodyObject
{
    /**
     * Returns the field name of the cluster oid distributed object field.
     */
    function getClusterField () :String;

    /**
     * Returns the oid of the cluster to which this user currently
     * belongs.
     */
    function getClusterOid () :int;

    /**
     * Sets the oid of the cluster to which this user currently belongs.
     */
    function setClusterOid (clusterOid :int) :void;
}
}