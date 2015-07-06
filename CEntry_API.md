# Introduction #

example:
<h4>User function Logout:</h4>
CEntry.m\_msgType = "Logout"; 

&lt;BR/&gt;


map EMPTY; 

&lt;BR/&gt;


return value String "Logout OK"; 

&lt;BR/&gt;


End of Logout 

&lt;BR/&gt;



<h3> already gone through: </h3>

<h4>User function Login:</h4>
CEntry.m\_msgType = "Login"; 

&lt;BR/&gt;


map VALUES: 

&lt;BR/&gt;


> "user" = user handle; 

&lt;BR/&gt;


> "password" = user password; 

&lt;BR/&gt;


validity check for map to match the CEntry value of User is up to client.
return value AUser; 

&lt;BR/&gt;


End of Login 

&lt;BR/&gt;


<h4>User function ArrangePayment:</h4>
CEntry.m\_msgType = "ArrangePayment"; 

&lt;BR/&gt;


map VALUES: 

&lt;BR/&gt;


> "type" will be one of the following :

&lt;BR/&gt;


> > "type"="monthly" - no additional info requiered 

&lt;BR/&gt;


> > return value in this case will be String "Updated user's Monthly subscription details"; 

&lt;BR/&gt;


> > "type"="yearly" - no additional info requiered 

&lt;BR/&gt;


> > return value in this case will be String "Updated user's yearly subscription details"; 

&lt;BR/&gt;


> > "type"="once"

&lt;BR/&gt;


> > > "cc\_num"="123213321" - Credit Card number depicted as a long integer in String; 

&lt;BR/&gt;


> > > "cc\_expire"="12/01/2000" - Credit Card Expire date depicted as a DB DATE in String; 

&lt;BR/&gt;


> > > "cc\_id"="123321" - ID / SSN of the Card owner depicted as a long integer in String; 

&lt;BR/&gt;



> > return value in this case will be String "Updated user's credit card details"; 

&lt;BR/&gt;


> > return on failure value in this case will be String "null"; 

&lt;BR/&gt;


End of ArrangePayment 

&lt;BR/&gt;



authorization fail will return String "Not authorized to use function \"ArrangePayment()\""

> 

&lt;BR/&gt;

