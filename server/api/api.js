/*
R Chakravorty, IBM Research, 2016
*/

var appRouter = function(app) {

   //sample api 
   app.get( "/", function( req,res ){

      res.send( "Hello World" );
   });

   /*
    Function to check the user credential

    This function is accessible by the
    /checklogin URI, relative to the server
    ip address

    The function accepts a "Request" object
    sent by any client by POST method. The user credential
    is passed in the body of the request as JSON
    object.

    The function checks the user credential and prepares
    another JSON object with required data for client-side
    processing.
   */
   

   app.post( "/checklogin", function( req, res ){

      //if the request does not contain username
      // or password, the server cannot do much, can it?

      if( !req.body.user || 
          !req.body.password ){
          console.log( "Did not receive any credential" );          
          return res.send( JSON.stringify( {"result":"error", "description" : "Credential missing" } ) );
      }

      //TODO:This function simply checks the user and
      //password against some specific example
      //perhaps this is where this server needs to
      //check in a database

      //TODO: Notice this function accepts parameters
      //in plain text - not good for security.
      //perhaps this one should accept encrypted parameters
      //instead !!!

      
      if( req.body.user == "student" &&
          req.body.password == "letmein" ){

          //send a success message and also the record shows
          //that the student has great record
          return res.send( JSON.stringify( {"result":"success", "record":"You are outstanding" } ) );

      }

      else{
          //well, it seems the user is not our student
          return res.send( JSON.stringify( {"result": "error", "description": "Incorrect credentials" } ) );
      }
      
   });
 
}
 
module.exports = appRouter;
