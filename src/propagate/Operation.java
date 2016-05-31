package propagate;

public class Operation {

    /**
     * Forwards the value of a node to another node
     * 
     * @param in
     *            the input node to forward to out
     * @param out
     *            the output node which receives the value of the input node
     */
    public static void forward(Node in, Node out) {
        in.addValueListener(new ValueListener() {
            public void valueUpdated(Node node) {
                out.setValue(in.getValue());
            }
        });
    }

    /**
     * Computes a+b and stores it into out (only whenever a or b changes)
     * 
     * @param a
     * @param b
     * @param out
     *            the sum of a and b
     */
    public static void sum(Node a, Node b, Node out) {
        ValueListener listener = new ValueListener() {
            public void valueUpdated(Node node) {
                out.setValue(a.getValue() + b.getValue());
            }
        };
        a.addValueListener(listener);
        b.addValueListener(listener);
    }

    /**
     * Computes a+b and stores it into a node (which is returned).
     * 
     * @param a
     * @param b
     * @return the node which is updated with a+b
     */
    public static Node sum(Node a, Node b) {
        Node out = new Node();
        sum(a, b, out);
        return out;
    }

    /**
     * Computes a*b and stores it into out (only whenever a or b changes)
     * 
     * @param a
     * @param b
     * @param out
     *            the product of a and b
     */
    public static void prod(Node a, Node b, Node out) {
        ValueListener listener = new ValueListener() {
            public void valueUpdated(Node node) {
                out.setValue(a.getValue() * b.getValue());
            }
        };
        a.addValueListener(listener);
        b.addValueListener(listener);
    }

    /**
     * Computes a/b and stores it into out (only whenever a or b changes)
     * 
     * @param a
     * @param b
     * @param out
     *            the quotient of a and b
     */
    public static void quot(Node a, Node b, Node out) {
        ValueListener listener = new ValueListener() {
            public void valueUpdated(Node node) {
                out.setValue(a.getValue() / b.getValue());
            }
        };
        a.addValueListener(listener);
        b.addValueListener(listener);
    }
}
