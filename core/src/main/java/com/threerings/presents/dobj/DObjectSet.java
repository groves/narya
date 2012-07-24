
package com.threerings.presents.dobj;

import com.threerings.io.ObjectInputStream;
import com.threerings.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import com.threerings.io.SimpleStreamableObject;
import com.threerings.io.Streamable;
import java.lang.Iterable;
import com.threerings.presents.dobj.DObject;
import java.util.Set;
import com.google.common.collect.Sets;
import static com.threerings.presents.Log.log;


public class DObjectSet<E extends DObject> implements Iterable<E>, Streamable {
    public static <E extends DObject> DObjectSet<E> newSet()  { return new DObjectSet<E>(); }

    public Iterator<E> iterator() { return _objs.iterator(); }

    // TODO handle post registration adds
    public void add(E obj) { log.warning("Adding", "obj", obj, "added", _objs.add(obj), "size", _objs.size()); }

    /** Custom writer method. @see Streamable. */
    public void writeObject (ObjectOutputStream out) throws IOException {
        out.writeInt(_objs.size());
        for (E obj : _objs) out.writeObject(obj);
    }

    /** Custom reader method. @see Streamable. */
    public void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        for (int ii = 0; ii < size; ii++) {
            @SuppressWarnings("unchecked") E entry = (E)in.readObject();
            _objs.add(entry);
        }
    }

    @Override public String toString () {
        StringBuilder buf = new StringBuilder("(");
        String prefix = "";
        for (E elem : _objs) {
            if (elem != null) {
                buf.append(prefix);
                prefix = ", ";
                buf.append(elem);
            }
        }
        buf.append(")");
        return buf.toString();
    }

    private final Set<E> _objs = Sets.newHashSet();
}
