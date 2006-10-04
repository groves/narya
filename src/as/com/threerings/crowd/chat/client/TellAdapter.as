package com.threerings.crowd.chat.client {

import com.threerings.util.Long;

import com.threerings.presents.client.InvocationAdapter;

public class TellAdapter extends InvocationAdapter
    implements ChatService_TellListener
{
    public function TellAdapter (failedFunc :Function, successFunc :Function)
    {
        super(failedFunc);
        _successFunc = successFunc;
    }

    // documentation inherited from interface TellListener
    public function tellSucceeded (idleTime :Long, awayMsg :String) :void
    {
        _successFunc(idleTime, awayMsg);
    }

    /** The method to call on success. */
    protected var _successFunc :Function;
}
}
