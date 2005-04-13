//
// $Id$
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

package com.threerings.parlor.game.data;

import com.threerings.parlor.data.TableConfig;

/**
 * Provides additional information for games with fixed teams.
 */
public interface TeamGameConfig extends TableConfig
{
    /**
     * Returns the number of players on the first n-1 of n teams.  For
     * instance, a game with four players in two partnerships would
     * return { 2 }, indicating that players 0 and 1 are partnered
     * against players 2 and 3.
     */
    public int[] getTeamCounts ();
}