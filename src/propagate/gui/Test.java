package propagate.gui;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import propagate.Node;
import propagate.NodeOutputter;
import propagate.Operation;

public class Test extends JFrame {
    public Test() {
        super("propagator test");

        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        {
            Container d = new Container();
            d.setLayout(new FlowLayout());
            c.add(d);

            Node a = new Node();
            Node b = new Node();
            Node out = new Node();
            Operation.sum(a, b, out);

            d.add(new JLabel("Sum test. "));
            d.add(new JLabel("a:"));
            d.add(new NodeTextField(a));
            d.add(new JLabel("b:"));
            d.add(new NodeTextField(b));
            d.add(new JLabel("out:"));
            d.add(new NodeTextField(out));

            // Just to test:
            out.addValueListener(new NodeOutputter("sum"));
        }

        {
            Container d = new Container();
            d.setLayout(new FlowLayout());
            c.add(d);

            // The conversion formula between celsius and fahrenheit is
            // fahr = 32 + 9/5 * cels.
            // We use the cutoff to make sure the infinite loop is cut off.
            // (Infinite loops could occur due to rounding errors.)
            //
            // Notice it's two-way: make sure and try updating both celsius and
            // fahrenheit in the GUI!
            Node cels = new Node(0.0, 0.01);
            Node fahr = new Node(0.0, 0.01);
            {
                Node x = new Node();
                Operation.prod(new Node(9.0 / 5), cels, x);
                Operation.sum(x, new Node(32), fahr);
            }
            {
                // cels = (fahr - 32) * 5/9
                Node x = new Node();
                Operation.sum(fahr, new Node(-32), x);
                Operation.prod(x, new Node(5.0 / 9), cels);
            }

            d.add(new JLabel("Temperature test. "));
            d.add(new JLabel("Celsius:"));
            d.add(new NodeTextField(cels));
            d.add(new JLabel("Fahrenheit:"));
            d.add(new NodeTextField(fahr));
        }

        {
            Container d = new Container();
            d.setLayout(new FlowLayout());
            c.add(d);

            // Let's compute square root using Newton's method. This takes some
            // care because of feedback loops, which is why we have the cutoff
            // (once changes get too small, we stop propagating).
            //
            // Newton's method can be algebraically manipulated into x_{n+1} =
            // (x_n + x/x_n) / 2.
            //
            // This is not reversible like the celsius/fahrenheit calculation.
            // This is because xn tells everyone each of its values as it is
            // updated. Exercise: add a new method to ValueListener which is
            // called when the value quiesces -- that is, when the new value is
            // within the cutoff -- and use this to create an Operation which
            // updates a node on quiescence. Though, there is the problem that
            // you can't detect quiescence if the Node is only updated once. Can
            // you solve this problem?

            Node x = new Node();
            Node xn = new Node(0.0, 1e-10); // ten digits of precision
            xn.addValueListener(new NodeOutputter("xn")); // so we can see xn as
                                                          // it updates

            // This is to initialize xn with whatever is in x when x is updated:
            Operation.forward(x, xn);

            Node recip = new Node();
            Operation.quot(x, xn, recip);
            Node sum = new Node();
            Operation.sum(xn, recip, sum);
            // This updates xn and creates the feedback loop:
            Operation.quot(sum, new Node(2), xn);

            // The following doesn't make the UI reversible:
            // Operation.prod(xn, xn, x);
            // This is because xn can be values which aren't even close to the
            // square root of x yet.

            d.add(new JLabel("Square root test. "));
            d.add(new JLabel("x:"));
            d.add(new NodeTextField(x));
            d.add(new JLabel("sqrt:"));
            d.add(new NodeTextField(xn));
        }

        validate();
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Test t = new Test();
    }
}
