Dominoes Game
Tony Nguyen
CS-375

version 1
How to play: Version 1 is played through standard in Players hand and board will be 
printed out intially, to play input format must follow the following,
to play Domino - p direction match other, suppose there is a Domino with a {five,five} the 
	following is an example how to play. 
	p right 5 5 , here p stands for play and "right" is the side of intended play, the 
	first 5 is an intended match to the right side. 5 5 is refering to the {five,five}
	domino. There is also a "left" direction, which specifies a play on the left side of 
	board.
to draw - type in the letter "d" to draw.
note: It's assumed that the user will not purposely try to break the program with questionable
      input.
Entry Point: Game class
Known bugs:
	board does not visually match the values of dominos but keeps track of what was matched
	by printing left / right available play.

version 2
to play - p direction match other, suppose there is a Domino with a {five,five} the 
	following is an example how to play. 
	p right 5 5 , here p stands for play and "right" is the side of intended play, the 
	first 5 is an intended match to the right side. 5 5 is refering to the {five,five}
	domino. There is also a "left" direction, which specifies a play on the left side of 
	board.
to draw - type in the letter "d" to draw.
note: It's assumed that the user will not purposely try to break the program with questionable
      input, also the GUI should not respond until it accepts input.
Entry Point: Game class
Known bugs: 
the domino switches positions occaisionally when an additional domino is added.

version 3
How to play - Use buttons on the GUI to play, 
to play Domino - select a Domino from the hand, which is the set of Dominoes directly above the
	controls, then select which direction the play is to be matched, and finnally select 
	which Value to match, the computer will play after human has made a move.
to draw - click the button "draw".
Status - The state of the game should be displayed at the very top of the window, if the game 
	ends there should be text explaining who won.
Entry Point: Game class
Known bugs:
sometimes visually switches dominoes position on board when a new Domino is played.



