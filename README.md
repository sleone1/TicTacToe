To run tests:
```
mvn test
```

(sorry, you'll need maven if you don't already have it: `brew install maven` on mac)


#### Assumptions
- Board contains valid sequence of player moves
- There is only one winner (follows above assumption)
- null represents no move

#### Design choices/alternatives
- My chosen solution can be updated for other board sizes by changing the Board.SIZE constant (and test code). I chose this over a more efficient solution (at scale, not incredibly important at this size) where we define each win coordinate condition in a set, and iterate over each checking for a winner; this alternate solution would require the win conditions added/removed at any size update (and would suck to implement at scale, at least without AI).
