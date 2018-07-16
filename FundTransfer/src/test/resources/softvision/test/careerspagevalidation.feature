Feature: Validate Softvision careers page 

Scenario: Validate the urls 
	Given the user is on Softvision careers page 
	When the user checks for the broken links in the page 
	Then the user should be shown all the broken links 
	
Scenario: Spell-check the page 
	Given the user is on Softvision careers page 
	When the user checks for spelling errors in the page 
	Then the user should be shown all the misspelled words and suggestions 
	
Scenario Outline: Validate dropdowns 
	Given the user is on Softvision careers page 
	When user validates that the options are sorted in "<dropdown>" dropdown 
	Then the sort validation result should be displayed 
	When user checks whether the "<dropdown>" dropdown is single or multi-select 
	Then the select type validation result should be displayed 
	When user checks for the default selection in "<dropdown>" dropdown 
	Then the default option should be displayed 
	
	Examples: 
		| dropdown		|
		| Locations		|  
		| Guilds		| 
    