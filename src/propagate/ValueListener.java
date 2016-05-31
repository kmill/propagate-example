package propagate;

public interface ValueListener {
    // The Node argument is just in case the listener wants to know exactly what
    // it's listening to. This lets a listener create a single ValueListener
    // rather than two. (Sometimes useful to save some recalculation effort, but
    // not used in this project.)
    public void valueUpdated(Node node);
}
