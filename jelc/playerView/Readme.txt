Simple java "Who's online" app


it just parses the eternal-lands who's online page to get the names. it allows custom lists of players to be used. if you have a list that you want to include in the release, or find my lists incomplete just tell us and we will include it in the next release.

to create a list:
just create a text file in the lists director. the first line is the comment to give the list, the second is left blank (will need this for some forwards compatability) the next lines are player names. MUST be the exact spelling to work properly.



todo:
- ask for suggestions

changelog:

.85 (dns)
- added getCarry(), getHelth(), getMana() to calculate those values.
- changed parseIt() so it gets the second number not the first so it returns the correct number when someone goes up a level.

.8 (dns)
- rewrote a lot of parts to make it easyer to reuse (eg it's in a package)
   * got rid of lots of classes
   * added:
      checkOnline(List mask), getOnline(List mask), getOffline(List mask) - takes a list of strings and sees if they are in the list
   * added file playerList.java this is used to manage loading and saveing lists
- fixed null player bug (players that don't exist) so it is far less prone to crashing, update() actually returns a correct  boolean value.


.75 (dns)
- fixed more bugs in the GUI and player files, remembered what "sincronised" does, which fixed thread bugs.
- created (ie moved) getCombatLevel() (thanks brom)
- created lots of (reletively useless) functions to get levels as ints eg. getOverall()

.7 (brom)
- created getCombatLevels functions
- created int parseIt(String tmp) which parses the level strings and converts them to int's

.6 (dns)
- when selecting a player it uses a thread to update, this makes it a little faster, and more responsive.
- if it fails to get the players online list it tries again 5 times.
- it produces more meaningfull error messages insted of just dumping the stack.


problems:
-if a player doesn't exist (ie the file has a bad name) it still throws NumberFormatExceptions (need to fix this later)
apart from that it doesn't have any major bugs.
if you find a bug, pm me or brom (on the forums or in the game).