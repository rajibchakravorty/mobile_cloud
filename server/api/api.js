/*
R Chakravorty, IBM Research, Australia, 2016
*/

var appRouter = function(app) {


   app.get( "/", function( req,res ){

      res.send( "Hello World" );
   });

   app.post( "/checklogin", function( req, res ){

      console.log( "Frm checklogin:" );
      console.log( req.body );

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

      console.log( req.body.user );
      console.log( req.body.password );
      
      if( req.body.user == "student" &&
          req.body.password == "letmein" ){
          console.log( JSON.stringify( { "result": "success","record":"Great Student" } ) );
          return res.send( JSON.stringify( {"result":"success", "record":"Great Student" } ) );

      }

      else{
          return res.send( JSON.stringify( {"result": "error", "description": "Incorrect credentials" } ) );
      }
      
   });
 
}
 
module.exports = appRouter;
