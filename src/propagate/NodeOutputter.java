package propagate;

public class NodeOutputter implements ValueListener {
    private String m_name;

    public NodeOutputter(String name) {
        m_name = name;
    }

    @Override
    public void valueUpdated(Node node) {
        System.out.println(m_name + ": " + node.getValue());
    }

}
