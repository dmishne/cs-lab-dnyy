#project properties

# Introduction #

easily changed via config.properties file in server.core package, the constants class offers an easy way to change settings without hard-coding it in the classes.


# Details #

  * OP\_WINDOW=true -> indicates GUI is activated for server
  * EFAULT\_Global\_Library\_Path=library from which files will be uploaded
  * EARLY\_AMMOUNT,MONTHLY\_AMMOUNT= ammounts gained by purhcasing a subscription
  * EFAULTHOST="jdbc:mysql://localhost/cslabdnyy" -> DB address and scheme
  * EFAULTUSER= DB user
  * EFAULTPASS= password
  * EFAULT\_PORT= listening port for clients
  * OPULARITY\_RATIO= howh much views weigh against purchases
  * EBUG - if true then it disables the logging to files.