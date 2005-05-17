//
// $Id: YoObjectAccess.java 17796 2004-11-11 02:28:30Z mdb $
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

package com.threerings.crowd.server;

import com.threerings.presents.dobj.AccessController;
import com.threerings.presents.dobj.DEvent;
import com.threerings.presents.dobj.DObject;
import com.threerings.presents.dobj.InvocationRequestEvent;
import com.threerings.presents.dobj.MessageEvent;
import com.threerings.presents.dobj.NamedEvent;
import com.threerings.presents.dobj.Subscriber;

import com.threerings.crowd.Log;
import com.threerings.crowd.data.BodyObject;

/**
 * Defines the various object access controllers used by the Crowd server.
 */
public class CrowdObjectAccess
{
    /**
     * Our default access controller. Disallows modification of any object
     * but allows anyone to subscribe.
     */
    public static AccessController DEFAULT = new AccessController()
    {
        // documentation inherited from interface
        public boolean allowSubscribe (DObject object, Subscriber subscriber)
        {
            // allow anyone to subscribe
            return true;
        }

        // documentation inherited from interface
        public boolean allowDispatch (DObject object, DEvent event)
        {
            // if the event came from the server, it's cool
            if (event.getSourceOid() == -1) {
                return true;

            } else {
                // if it came from the client, it better be a
                // non-modification event
                return (event instanceof MessageEvent ||
                        event instanceof InvocationRequestEvent);
            }
        }
    };

    /**
     * Provides access control for user objects.
     */
    public static AccessController USER = new AccessController()
    {
        // documentation inherited from interface
        public boolean allowSubscribe (DObject object, Subscriber sub)
        {
            boolean allowed = true;
            // if the subscriber is a client, ensure that they are this
            // same user
            if (sub instanceof CrowdClient) {
                String cluser = ((CrowdClient)sub).getUsername().toString();
                String obuser = ((BodyObject)object).username.toString();
                allowed = obuser.equalsIgnoreCase(cluser);
                if (!allowed) {
                    Log.warning("Refusing BodyObject subscription request " +
                                "[owner=" + obuser + ", sub=" + sub + "].");
                }
            }
            return allowed;
        }

        // documentation inherited from interface
        public boolean allowDispatch (DObject object, DEvent event)
        {
            // if the event came from the server, it's cool
            if (event.getSourceOid() == -1) {
                return true;

            } else {
                // the client is only allowed to modify the RECEIVERS field
                return (event instanceof NamedEvent) &&
                    ((NamedEvent)event).getName().equals(BodyObject.RECEIVERS);
            }
        }
    };
}