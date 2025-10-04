# Project Report

## Design Decisions

### Architecture
Explain your MVC structure:
- <b>How did you separate model, view, controller?</b>
  * I seperated model view and controller by having the model object
  represent the game states such as what words are in the board
  , what the user guess, what is the answer as well as 
  what words are available such as in the word repository.
  * In the Controller class, I made it sure to enforce the rules and flow
  of the game by controlling how much the user can input, whether the game
  ended by checking the gamemodel. I also had it listen to changes in the 
  UI by listening the UI changes from the public fxml tagged listeners.
  * For the View package, I seperated it from the controller class and model
  class by having the view show the main scene of the game by importing the
  fxml file from the resources' folder. 
- <b>What interfaces/abstractions did you create?</b>
  * I chose to abstract the wordle word since both
   the wordle word has similar features to the
   wordle answer. They both have the same length
   and an index. However, where the difference
   lies is that wordle word is that I wanted a 
   wordle guess to comapre to a wordle answer
   to be more specific during implementation.
- <b>Why did you choose Swing vs JavaFX?</b> 
  * I chose JavaFX over swing because of scene builder.
     Having the option to visually see how my UI works
     alongside the css feature made it easier to construct UI
     without trying to build the view one by one and 
     saves time. I have yet to know any useful tools for
     swing that streamlines the UI building process


### Data Structures
- <b>How do you represent game state? (arrays, maps, objects?)</b>
  * I chose to represent hashmaps and lists for my game
- Why did you choose these structures?
  * I chose hashmaps because they are fast for instant retrieval
  of words. In my repository class, It would take long to iterate
  every string to match with the string that im searching for.
  In the use of list, I treated it mostly as a stack when appending
  words to the word grid and checking if the user reach the max
  of the list.


### Algorithms
- Word validation: O(1) word retrieval. Utilizes hashmaps
- Recoloring keyboard: O(n) where n is the number of keys.
  * The recoloring keyboard algorithm has to traverse each key
  to check the current status and recolor it. Unfortunately,
  I couldn't find a better implementation to instantly jump to
  a key that had changed color. 
- Populating word repository O(n) where n is size of word list
  * parser goes through each word one by one. 
 
## Challenges Faced
1. **Implementing the Color Blind Feature:** The board color changes but not the keyboard buttons
    - **Solution:** I mostly tried to debug it using print statements.
   However, it reached a dead end as I was mindlessly trying to print
   my way out of the problem. However, I took a step back and realize
   that it is preferable to add a stylesheet to the whole scene rather than
   the individual buttons since css hierarchies exist. It took me a while
   to figure it out, but taking a short break helps to refresh the problem

2. **Implementing the word repository list:** Slow algorithm
    - **Solution:** When I had to implement the word list I was
quite stuck trying to find a way to quickly retrieve items as well
as get random words in a single hashmap. Although I wanted to use
random, if I put my words in a set, I cannot instant access with random
since it is not ordered by index. I thought about it for
a moment and I realize I could just have both structures by getting a random word in o(1) 
and checking if word exists. I was confident in this solution since storage
for this program is not an important issue.

## What We Learned
- **OOP concepts reinforced**
  * the ideas of abstraction and Polymorphism was reinforced.
  I was not very confident starting this problem by myself since
  this is the second ever large scale program I started in my CS 
  degree and I'm not fully familiar with OOP yet. However, by going
  through the problem, I realize the importance of breaking down
  your problem into subproblems by abstracting the step by step process.
  Instead of worrying about the implementation, I just wrote down
  what basic step i need to do and how to model the wordle board
  before implementing the details. Furthermore, I realize how 
  useful for objects to be in multiple forms. To traverse
  objects by similarity rather than exact data type makes it useful
  for abstracting away problems.
- Design patterns discovered
   * A useful design pattern that I discovered was the Observer pattern. 
   Although i did not fully implement it into my program, I realize I can
   pass parameters into a different class during runtime. 
   When the game switches 
   scene or implements a controller from the fxml,
   it observes the current state of the fxml file and retrieves
   input from the user. As a result of the model change
   it passes it into the final game state where changes
   depending on the state of the MainSceneController.
- Testing insights
  * During testing, I find the usefulness of test driven development.
  Having test driven development allowed me
  to test a smaller feature in order for larger features to work. For instance,
  I have a method that compares two words which contains methods that
  counts the instance of other words. However, having
  to see if the instance count method works allows better debugging since
  you can be certain whether the count instance method works or not.

## If We Had More Time
- Feature we'd add
  * A Feature i'd like to add are animations. Although the UI
  sufficiently tells user the input mismatch as well
  as instantly responding to input, there is no response if the 
  word is entered which can add further polish. 

- Refactoring we'd do
 * A refactoring that i'd like to do is strongly pushing the observable pattern.
Initially before I discovered the pattern, I couldn't figure out how to display
the contents of my GameController class to the UI. I had to change
it so that my MainController class decides the game flow, updating the UI and listening for
the UI. I wanted to make it so that the game flow is seperated from the
UI listener methods connected to my FXML file. However, I realize
that I can have class that observes the MainController class

- Performance improvements'
  * Although there are not any observable issues with the performance.
  I would like to improve the performance on updating the keyboard
  UI. 