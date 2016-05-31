This is an example of using propagators for computations.

The propagate package contains
- Node objects, which contain a value, and let ValueListeners know when its value has changed (by a definable appreciable amount)
- Operations, which connect Nodes together using different algebraic relations.  These are only implemented one-way.

The propagate.gui package contains
- NodeTextField, which is a TextField backed by a Node.  Changes to the text field updated the Node's value, and 
  changes to the Node change the field's text
- Test, which contains three examples of using these propagators: summation, celsius<->fahrenheit conversion (both ways),
  and a feedback loop which calculates square roots by Newton's method

Exercises:
- Create a spreadsheet application.  Each spreadsheet cell is a different Node, and equations entered into cells connect Nodes
  together by Operations, with the output updating the cell with the equation.  There are some things you need to do to make
  this robust:
    - Infinite loops either 1) must be detected and disallowed, 2) be allowed but use a cutoff scheme like in this project,
      or 3) be allowed but have a NodeManager which drives the Node updating (like in an exercise below) and which checks
      when an infinite loop is happening, so it can stop the calculation and report it to the user (without causing a
      StackOverflowError
    - whenever equations change either 1) rebuild the whole propagator network or 2) keep track of which equations listen
      to which nodes and carefully addValueListener and removeValueListener where appropriate.

- Create BooleanNodes which contain a logical true/false.  Create And,Or,Not,etc. boolean operations to connect them.
  Make some simple logical circuits.
  Create a Register operation which takes two inputs (the "value" and the "clock") and gives a single output, where the output
  is only updated with the value when the clock is updated.  Create a Clock which regularly flips a BooleanNode between
  true and false -- this might be connected to some UI.
  Now you have enough to simulate a simple CPU from scratch
  (though it'll take a while to figure out how to make it!).

- Make reversible Operations.  So, if you have the relation a+b=c, and c changes, make it update a or b so that their sum is c.
  Decide whether to update a to c-b or b to c-a, or something else entirely.  You'll have to consider whether to update c with
  the sum of a and b if it was c's change which caused the update in the first place (and similar for updating a,b if c only
  was updated because a or b were updated).  This will likely require having special ConstantNodes which cannot be updated.

- Create an Operation which only forwards a change when the input has settled down ("quiesced").
  This helps break feedback loops.  There is the problem where if the input is updated exactly once to its final value, then
  the operation cannot detect quiescence.  One possibility to remedy this is to make nodes periodically send out their
  value to their listeners.  Are there other solutions?

- Right now, when a node sees it has been changed, it notifies its listeners immediately.  This can cause issues
  when a node has multiple outputs (though no Nodes have multiple outputs at the moment).
  Implement a NodeManager which Nodes send a message to when they've been updated.  Once nodes stop telling it they've been
  updated, have the NodeManager tell the affected nodes to notify their listeners.  (This is all somewhat vague because there
  are a number of ways to do this.)