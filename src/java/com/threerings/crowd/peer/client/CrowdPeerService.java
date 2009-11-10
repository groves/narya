//
// $Id$
//
// Narya library - tools for developing networked games
// Copyright (C) 2002-2009 Three Rings Design, Inc., All Rights Reserved
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

package com.threerings.crowd.peer.client;

import com.threerings.util.Name;

import com.threerings.presents.client.Client;
import com.threerings.presents.client.InvocationService;

import com.threerings.crowd.chat.client.ChatService;
import com.threerings.crowd.chat.data.UserMessage;
import com.threerings.crowd.chat.server.SpeakUtil;

/**
 * Bridges certain Crowd services between peers in a cluster configuration.
 */
public interface CrowdPeerService extends InvocationService
{
    /**
     * Used to forward a tell request to the server on which the destination user actually
     * occupies.
     */
    void deliverTell (Client client, UserMessage message, Name target,
                      ChatService.TellListener listener);

    /**
     * Dispatches a broadcast message on this peer.
     */
    void deliverBroadcast (Client client, Name from, byte levelOrMode, String bundle, String msg);

    /**
     * Obtains the messages that the user has heard on this peer. On success, the listener will
     * receive a {@code List} of {@link SpeakUtil.ChatHistoryEntry} instances.
     */
    void getChatHistory (Client caller, Name user, ResultListener lner);
}
