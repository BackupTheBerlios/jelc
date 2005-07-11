i've given you the source code of my bot.
i've tweeked it slightly, commands are in seperate classes
and they implement BotCommand, a refence to MyBot2 is required to get
the commands to reply back so this is passed as an argument to the
constructor. the process method now takes a string[] as an argument instead of a string,
this makes it much easyer to process the commands eg: if(args[0].equalsIgnoreCase("hi")).
you also should get it to return true if the function does something if you don't return true
it will display an error message saying that command does not exist
when adding features to the bot use hi.java as an example.

the admin commands commands can be usefull see
admin.java and remember to change your admin.txt text
file to contain a list of bot admins. 
bot.admin.isAdmin(name) is a good check to see if the
person is one of the admins. 
there is also the same types of thing is bot.guild.isGuild(name) to test and restrict commands
only to members of your guild.

the sendHelp(name, type) function is also useful to publish help about the comands`.


if you have any problems/suggestions tell me and if
you write a cool feature send it to me and we can
share our code.


most modules can be disabled by commenting thair registration in the MyBot2 contstructor ie 
//irc=new IRC(this); //would disable irc (and it shouldn't crash hopefully)
but some modules like guild and admin cannot be disabled as they hold important lists




CHANGELOG:
30/6/05
- BotCommand now has a new method onQuit() this is called as the bot quits to allow each module to
    save to files and to disconnect itself from irc. 
- the bot now supports IRC communication, this is procssed in IRC.java and depends on martyr java irc library
    visit http://martyr.sourceforge.net/ for info about the library
- i have most of the colour codes, to use them put in the static char in MyBot2 eg MyBot2.DARK_ORANGE+"string" would be changed to dark orange

lots of other changes that i've forgotten