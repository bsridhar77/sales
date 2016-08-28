  db.sales.aggregate(
    [ 
        { 
              $match : {
                            "_id.timestamp" : {
                                "$eq": ISODate("2016-08-27T02:00:00.000+05:30")
                            },
                            "_id.hostName" : "delhi"
                           
                        }
        } ,
       { $project: { 
        items: {
            $filter: {
               input: "$salesData",
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
                            "_id":0,
                            data: { $slice: [ "$items.volume", 1 ,2] }
                        } 
        }
    ]
);
  