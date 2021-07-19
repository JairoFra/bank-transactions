Feature: the status of a transaction can be retrieved


  Scenario: I search for a transaction that is not stored in our system from channel ATM
    Given a transaction that is not stored in our system
	When I check the status from "ATM" channel
	Then the system returns the status "INVALID"
	
  Scenario: I search for a transaction that is not stored in our system from channel CLIENT
    Given a transaction that is not stored in our system
	When I check the status from "CLIENT" channel
	Then the system returns the status "INVALID"

  Scenario: I search for a transaction that is not stored in our system from channel INTERNAL
    Given a transaction that is not stored in our system
	When I check the status from "INTERNAL" channel
	Then the system returns the status "INVALID"
	
	
	
  Scenario: I search for a transaction with date before today from channel CLIENT
    Given a transaction that is stored in our system and its date is before today
	When I check the status from "CLIENT" channel
	Then the system returns the status "SETTLED"
	And the system returns the amount substracting the fee
	
  Scenario: I search for a transaction with date before today from channel ATM
    Given a transaction that is stored in our system and its date is before today
	When I check the status from "ATM" channel
	Then the system returns the status "SETTLED"
	And the system returns the amount substracting the fee
	
  Scenario: I search for a transaction with date before today from channel INTERNAL
    Given a transaction that is stored in our system and its date is before today
	When I check the status from "INTERNAL" channel
	Then the system returns the status "SETTLED"
	And the system returns the amount and the fee
	
		
		
  Scenario: I search for a transaction with date equals to today from channel CLIENT
    Given a transaction that is stored in our system and its date is today
	When I check the status from "CLIENT" channel
	Then the system returns the status "PENDING"
	And the system returns the amount substracting the fee
	
  Scenario: I search for a transaction with date equals to today from channel ATM
    Given a transaction that is stored in our system and its date is today
	When I check the status from "ATM" channel
	Then the system returns the status "PENDING"
	And the system returns the amount substracting the fee
	
  Scenario: I search for a transaction with date equals to today from channel INTERNAL
    Given a transaction that is stored in our system and its date is today
	When I check the status from "INTERNAL" channel
	Then the system returns the status "PENDING"
	And the system returns the amount and the fee
	

		
  Scenario: I search for a transaction with date after today from channel CLIENT
    Given a transaction that is stored in our system and its date is after today
	When I check the status from "CLIENT" channel
	Then the system returns the status "FUTURE"
	And the system returns the amount substracting the fee
  
  Scenario: I search for a transaction with date after today from channel ATM
    Given a transaction that is stored in our system and its date is after today
	When I check the status from "ATM" channel
	Then the system returns the status "PENDING"
	And the system returns the amount substracting the fee
	
  Scenario: I search for a transaction with date after today from channel INTERNAL
    Given a transaction that is stored in our system and its date is after today
	When I check the status from "INTERNAL" channel
	Then the system returns the status "FUTURE"
	And the system returns the amount and the fee
	
	
	
	
	
	
	