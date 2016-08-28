
db.sales.aggregate(
 [ 
      { 
             $match : {
                            "_id.timestamp" : {
                                "$eq": ISODate("2016-08-27T03:00:00.000+05:30")
                            },
                            "_id.hostName" : "agra"
                        
                      }
      } ,
		
		{
				$unwind : "$customData"
		} ,
               
               
               
               
      { 
             $match : {
                                                       "customData.type":"games"
                      }
      }    ,
                           { 
                               "$project" : 
                               {
                                    "mydata" : "$customData.mydata.8",
                                   "_id":0,
                                   myArray: [ "$customData.mydata.8", "$customData.mydata.9" ] 
                                   //db.collection.aggregate( [ { $project: { myArray: [ "$x", "$y" ] } } ] )
                              }
                                  
                                    }
     ,
   
			
                         
     
      
       ]    
);
  