package propagate;

import java.util.ArrayList;

public class Node {
    private double m_value;
    private double m_cutoff;
    private ArrayList<ValueListener> m_listeners;

    public Node() {
        this(0.0, 0.0);
    }

    public Node(double value) {
        this(value, 0.0);
    }

    public Node(double value, double cutoff) {
        m_value = value;
        m_cutoff = cutoff;
        m_listeners = new ArrayList<ValueListener>();
    }

    public double getValue() {
        return m_value;
    }

    public void setValue(double v) {
        if (Math.abs(v - m_value) >= m_cutoff) {
            m_value = v;
            notifyListeners();
        }
    }

    public void addValueListener(ValueListener listener) {
        m_listeners.add(listener);
    }

    private void notifyListeners() {
        for (ValueListener listener : m_listeners) {
            listener.valueUpdated(this);
        }
    }
}
