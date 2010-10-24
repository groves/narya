//
// $Id$

package com.threerings.crowd.server;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.threerings.crowd.data.JabberConfig;

import static com.threerings.crowd.Log.log;

/**
 * A basic server that creates a single room and sticks everyone in it where they can chat with one
 * another.
 */
public class JabberServer extends CrowdServer
{
    public static void main (String[] args)
    {
        Injector injector = Guice.createInjector(new Module());
        JabberServer server = injector.getInstance(JabberServer.class);

        try {
            server.init(injector);
            server.run();
        } catch (Exception e) {
            log.warning("Unable to initialize server.", e);
        }
    }

    @Override // from CrowdServer
    public void init (Injector injector)
        throws Exception
    {
        super.init(injector);

        // create a single location
        _pmgr = _plreg.createPlace(new JabberConfig());
    }

    protected PlaceManager _pmgr;
}