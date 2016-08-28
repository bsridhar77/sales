//lookup works on all the fields of _id composite field
db.getCollection('metricsByMin').find(
{
  "_id" : {
        "timestamp" : ISODate("2016-08-25T16:14:21.877Z"),
        "servicename" : "billing",
        "ip" : "30.122.32.138",
        "port" : "8051"
    }  
}

).explain()

//lookup does not work if I query on just one of the fields in the composite field _id
//In this case a equal comparision on "timestamp" field of the _id composite filed
db.getCollection('metricsByMin').find(
{
  "_id" : {
        "timestamp" : ISODate("2016-08-24T21:44:21.877-18:30"),
        }
}
)

db.metricsByMin.createIndex( { _id: 1 } )

//lookup does not work if I query on just one of the fields in the composite field _id
//In this case a date comparision on the "timestamp" field of _id composite field
db.getCollection('metricsByMin').find(
{
  "_id" : {
        "timestamp" : {
                "$gte":ISODate("2016-08-24T21:44:21.877-18:30")
        }
    }  
}
)

//lookup does not work if there is a date comparison of one of the fields of _id composite field
//and other fields it is equal comparison
db.getCollection('metricsByMin').find(
{
  "_id" : {
         "servicename" : "billing",
        "ip" : "30.122.32.138",
        "port" : "8051",
        "timestamp" : {
                "$gte":ISODate("2016-08-24T21:44:21.877-18:30")
        },
     
    }  
}
)


//lookup works if I query on just one of the fields in the composite field _id
//In this case a equal comparision on "timestamp" field of the _id composite filed
//But, by using dot notation as below
db.getCollection('metricsByMin').find(
{
  "_id.timestamp" : ISODate("2016-08-24T21:44:21.877-18:30")
}
).explain()


//lookup works if I query on just one of the fields in the composite field _id
//In this case a date comparision on "timestamp" field of the _id composite filed
//But, by using dot notation as below
db.getCollection('metricsByMin').find(
{
  "_id.timestamp" : {
        "$gte":ISODate("2016-08-25T16:14:21.877Z")
  }
}
).explain()


//lookup works if I query on all the fields in the composite _id field
//In this case a equals comparision on "timestamp" field of the _id composite filed
//And other fields are also equal comaparison
//But, by using dot notation as below
db.getCollection('metricsByMin').find(
{
        "_id.timestamp" : ISODate("2016-08-25T16:14:21.877Z"),
        "_id.servicename" : "billing",
        "_id.ip" : "30.122.32.138",
        "_id.port" : "8051"
 
}
).explain()


//lookup works if I query on all the fields in the composite _id field
//In this case a date comparision on "timestamp" field of the _id composite filed
//And other fields are equal comaparison
//But, by using dot notation as below
db.getCollection('metricsByMin').find(
{
  "_id.timestamp" : {
                "$gte":ISODate("2016-08-25T16:14:21.877Z")
        },
        "_id.servicename" : "billing",
        "_id.ip" : "30.122.32.138",
        "_id.port" : "8051"
 
}
)
//Works