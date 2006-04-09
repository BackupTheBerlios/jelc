here is version 5 of my codebase, sorry it has been ages since i updated the code in cvs.


The basic structure of the folders:
bot.deadface5.*                - core executables and services etc
bot.deadface5.commands.*       - the commands that it processeses
bot.deadface5.commands.mail.*  - mail commands.
bot.deadface5.game.*           - put games here (but no progress)
bot.deadface5.services.*       - services such as getting actors to see people going past (people and guild names) and time events
bot.deadface5.tools.*          - misc migration tools etc.
/cache/                        - create this folder to store playername.php cache files of stats pages
/fortunes/                     - put your fortunes here for the fortune command
/lists/                        - put your online lists for 'online listname' command
/log/                          - create this for your log files storage
/mail/                         - create this for peoples mail
/stats/                        - create this for peoples stats cached with the 'cacheme' (for people with privacy on)

Important files:
config.txt                     - bot config files, see /bot/deadface5/config.txt
guild.txt                      - fallback to ensure that guild permissions work (proby should remove the need for this)
messages.tst                   - file to store messages that people send to the bot like suggestions etc.
pklist.txt                     - file to store enemies (not really used)



Config file structure:
The config file is in "PARAMETER:VALUE" structure, lines starting with a # are ignored, check the config.txt in this folder.

Database:
This bot uses mysql as the database, use the sql from db.txt to create the structure.

Extending the bot:
For simple commands just use something from bot.deadface5.commands.* as an example, it just needs to implement the BotCommand interface.
once you have that you need to get that command registered with the bot so put it in the constructor of MyBot and call addBotListener() to register it.

For more of a conversation style of commands where a series of inputs are required there is the 'session' interface.
The idea is that you register with the account object then all messages will be sent to the session object.

