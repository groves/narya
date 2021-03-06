//
// $Id$
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2012 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/narya/
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

import java.util.List;

import javax.annotation.Generated;

import com.threerings.presents.client.InvocationService;
import com.threerings.presents.client.TestService;
import com.threerings.presents.data.TestClientObject;

/**
 * Defines the server-side of the {@link TestService}.
 */
@Generated(value={"com.threerings.presents.tools.GenServiceTask"},
           comments="Derived from TestService.java.")
public interface TestProvider extends InvocationProvider
{
    /**
     * Handles a {@link TestService#getTestOid} request.
     */
    void getTestOid (TestClientObject caller, TestService.TestOidListener arg1)
        throws InvocationException;

    /**
     * Handles a {@link TestService#giveMeThePower} request.
     */
    void giveMeThePower (TestClientObject caller, InvocationService.ConfirmListener arg1)
        throws InvocationException;

    /**
     * Handles a {@link TestService#test} request.
     */
    void test (TestClientObject caller, String arg1, int arg2, List<Integer> arg3, TestService.TestFuncListener arg4)
        throws InvocationException;
}
