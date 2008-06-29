//
// $Id$
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2008 Three Rings Design, Inc., All Rights Reserved
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

package com.threerings.presents.server;

import com.samskivert.util.Invoker;
import com.samskivert.util.ResultListener;

import com.threerings.presents.server.net.AuthingConnection;

/**
 * Handles certain special kinds of authentications and passes the remainder through to the default
 * authenticator.
 */
public abstract class ChainedAuthenticator extends Authenticator
{
    /**
     * Called by the {@link ConnectionManager} to initialize our delegate.
     */
    public void setChainedAuthenticator (Authenticator author)
    {
        _delegate = author;
    }

    @Override // from Authenticator
    public void authenticateConnection (Invoker invoker, AuthingConnection conn,
                                        ResultListener<AuthingConnection> onComplete)
    {
        // if we handle this sort of authentication, then do so
        if (shouldHandleConnection(conn)) {
            super.authenticateConnection(invoker, conn, onComplete);

        } else {
            // otherwise pass the request on to our delegate
            _delegate.authenticateConnection(invoker, conn, onComplete);
        }
    }

    /**
     * Derived classes should implement this method and return true if the supplied connection is
     * one that they should authenticate.
     */
    protected abstract boolean shouldHandleConnection (AuthingConnection conn);

    protected Authenticator _delegate;
}