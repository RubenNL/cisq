Feature: Training for Lingo
	As a Lingo participant
	I want to practice guessing 5, 6 and 7 letter words
	In order to prepare for my TV appearance

Scenario: Start a new game
	When I start a new game
	THen the word to guess has "5" letters
	And I should see the first letter
	And my score is "0"
	
Scenario Outline: Start a new round
	Given I am playing a game
	And the last round was won
	And the last word had "<previous length>" letters
	When I start a new round
	Then the word to guess has "<next length>" letters
	
	Examples:
		| previous length | next length |
		| 5				  | 6			|
		| 6				  | 7			|
		| 7				  | 5			|
	# Failure path
	Given I am playing a game   
	And the round was lost   
	Then I cannot start a new round
	
Scenario Outline: Guessing a word
	Given that am playing a game
	When I give a <guess>
	Then I want to receive <feedback>
	Examples:
		| word            | guess       | feedback
		| Banana          | Baantje     | Ba.....
		| Banana          | Baadden     | Ba.....
		| Banana          | bagatel     | Ba.a...
 
