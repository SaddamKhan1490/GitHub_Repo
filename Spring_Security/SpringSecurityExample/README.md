# Spring Security Example 

Below are steps of typical Spring Security project:-

1> User makes request for specific REST endpoint URL.

2> Seeing the incoming HTTP request spring starts loading its Spring Configuration. 

3> Then request reaches Spring Security layer where Authorization i.e. roles : admin, user,etc... and Authentication i.e. DB Password, In-memory Password, etc... takes place to filter unnecessary content. 

4> Finally request will be transfered to Application resources i.e. REST endpoint which we have.
