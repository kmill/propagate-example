package propagate.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextField;

import propagate.Node;
import propagate.ValueListener;

public class NodeTextField extends JTextField {
    public NodeTextField(Node node) {
        super(12);
        updateFromNode(node);

        // add listener to text field. This binds to the enter key.
        this.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(getText());
                    node.setValue(x);
                } catch (NumberFormatException ex) {
                    updateFromNode(node);
                }
            }
        });

        node.addValueListener(new ValueListener() {
            @Override
            public void valueUpdated(Node node) {
                updateFromNode(node);
            }
        });
    }

    void updateFromNode(Node node) {
        this.setText(Double.toString(node.getValue()));
    }
}
