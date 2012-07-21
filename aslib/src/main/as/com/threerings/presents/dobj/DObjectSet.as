
package com.threerings.presents.dobj {

import com.threerings.io.ObjectInputStream;
import com.threerings.io.ObjectOutputStream;
import com.threerings.io.Streamable;

public class DObjectSet implements Streamable {
    public var entries :Array = [];
    public function writeObject (out :ObjectOutputStream) :void {
        throw new Error("DObjectSets can't be written");
    }

    public function readObject (ins :ObjectInputStream) :void {
        var len :int = ins.readInt();
        for (var ii :int = 0; ii < len; ii++) entries.push(ins.readObject());
    }

}
}
