db.getCollection('sales').find(
{
    "_id" : {
         "timestamp" : ISODate("2016-08-27T10:32:00.000-06:30"),
        "hostName" : "delhi"
    }
            ,"customData.type":"games"
}, 
{
  //  "_id.timestamp":1 
  //  "salesData.$.volume": 1 
    //"hobby":1
    // ,"salesData.type":"electronics"
    "_id":0,
    "customData.$.mydata": 1,
     "customData.mydata.8": 1
  }
).explain()
  
  db.sales.aggregate(
    [ 
        { 
              $match : {
                            "_id.timestamp" : {
                                "#gte": ISODate("2016-08-27T02:25:00.000+05:30")
                            },
                            "_id.hostName" : "delhi"
                           
                        }
        } ,
       { $project: { 
        items: {
            $filter: {
               input: "$customData",
               as: "item",
               cond: { $eq: [ "$$item.type", "games" ] }
            }
       
        }
    }
        
} 
 ,
 { $unwind : "$items" },
 
  { 
            $project : { 
                            "items.mydata.8" : 1 ,
                            "_id":0,
                        } 
        }
  
    ]
);
  
  db.sales.aggregate( [ { $unwind : "$salesData" } ] )
  
    db.sales.aggregate([ 
 
    { 
      $match : 
      {
                    "_id" : 
                      {
                        "timestamp" : ISODate("2016-08-26T18:00:00.000-06:30"),
                        "hostName" : "agra"
                    }
               
                }
  } ,
  { $project: { 
        items: {
            $filter: {
               input: "$salesData",
               as: "item",
               cond: { $eq: [ "$$item.type", "electronics" ] }
            }
       
        }
    }
        
}  ,

{ $unwind : "$items.volume" }//,
//{
    
   // $project : { hobby : 1 , "items.volume" : 1 }
//}

])