README
-------------------------------------------------------
How passengers are set up:

every time that a reservation is made with "res" or "resfcl", a default
passenger object is created for it. If "respsngr" is used, then it will
use the passenger made with the info given in the command.

format for "respsngr":
"respsngr flightNum name passportNum"
example:
respsngr UA123 Matt AB0000

-------------------------------------------------------
Cancelling reservations:

cnclpsngr:
this command is formatted the same way as respsngr.
this will cancel one of the passenger's reservations on the flight given

cancel:
this command will cancel one reservation of a default passenger
(either a first class or economy passenger with an empty name and
 passportNum)

--------------------------------------------------------
flightReservationSystemMethods class:

all of code that would be executed in flightReservationSystem for each command,
was put into a seperate class all in seperate methods. This is just to add
readability.

--------------------------------------------------------
Functionality:

All of the commands should be fully functional with no errors.

--------------------------------------------------------
Commenting:

All methods should have javadoc comments, and normal comments explaining unclearness

--------------------------------------------------------
Bonus:

The whole errorMsg system has been replaced with custom checked exceptions that
are all handled within FlightReservationSystem.
