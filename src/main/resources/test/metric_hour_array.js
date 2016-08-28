//db.collection.aggregate( [ { $project: { myArray: [ "$x", "$y" ] } } ] )

  db.sales.aggregate(
    [ 
        { 
              $match : {
                           
                            "_id.hostName" : "agra"
                           
                        }
        } ,
        {
				$unwind : "$salesData"
	} ,
        
            { 
             $match : {
                                                       "salesData.type":"games"
                      }
      },
        
        
                           { 
                               "$project" : 
                               {
                                   "_id":0,
                          
                                   "mydata": { $slice: [ "$salesData.volume", 0 ,2] }
                              }
                                  
                                    }
 
 
    ]
);
  
                                    
db.sales.aggregate(                                    
                                    
                 [ { "$match" : { "$and" : [ { "_id.hostName" : "agra"} , { }]}} , { "$unwind" : "$salesData"} , { "$match" : { "customData.type" : "games"}} , { "$project" : { "_id" : 0 , "mydata" : { "$slice" : [ "$salesData.volume" , 0 , 2]}}}]
                 
                                    
                    )