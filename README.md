To run tests:
```
mvn test
```

(sorry, you'll need maven if you don't already have it: `brew install maven` on mac)


#### Assumptions
- Board contains valid sequence of player moves
- There is only one winner (follows above assumption)
- The game is only over when no winner is possible, but moves are left. A draw with moves left is not considered over. 
