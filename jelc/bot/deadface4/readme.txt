Here's version 4 of my deadface code and i've made quite a fiew architectural changes since version 2.

In version 2 of the code the bot command  method had the signature: boolean process(String name,String[] args ,int type);
in version 3-4 it is now: boolean process(Replyer reply,String[] args);
this introduced a Replyer interface that is passed around to reperesent different channels of communication allowing the development of
different backends to the bot. i have implemented  a pm, #gm, irc, console, socket (so you can telnet to it) it should be possible to
implement other backends like email quite easily. it should also be possible have multiple connections and possibly conections with other games.

In version 4 i introduced the Config class, this is used to store information like hostname, port, username etc makeing it easyer for me
to port the application between accounts. This will be extended to allow turning on/off some features about the modules.

The main change is there is an Account and AccountManager objects to store account information. i am slowly moveing all the different information
to be stored. I'm working on moveing all the data which is devided between all the classes to a single file to store each users data.

Another related change is the introduction of the Session interface, this contains the method public void processSession(String arg);
The idea behind this interface is that you can register a class to recieve input instead of the normal processing of commands.
An example of this is in the MailSender class recieves the inputs instead of normal processing.