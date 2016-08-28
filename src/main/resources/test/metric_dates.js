db.metric.aggregate(
  [
    { $project : { month_created : { $month : "$timestamp" } } } ,
    { $group : { _id : {month_created:"$month_created"} , number : { $sum : 1 } } },
    { $sort : { "_id.month_created" : 1 } }
  ]
)
    
    db.metric.aggregate(
  [
    { $project : { dayOfMonth : { $dayOfMonth : "$timestamp" } } } ,
    { $group : { _id : {dayOfMonth:"$dayOfMonth"} , number : { $sum : 1 } } },
    { $sort : { "_id.dayOfMonth" : 1 } }
  ]
)
    
     
    db.metric.aggregate(
  [
    { $project : { year : { $year : "$timestamp" } } } ,
    { $group : { _id : {year:"$year"} , number : { $sum : 1 } } },
    { $sort : { "_id.year" : 1 } }
  ]
)
    
     db.metric.aggregate(
  [
    { $project : { hour : { $hour : "$timestamp" } } } ,
    { $group : { _id : {hour:"$hour"} , number : { $sum : 1 } } },
    { $sort : { "_id.hour" : 1 } }
  ]
)