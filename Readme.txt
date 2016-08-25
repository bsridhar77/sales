To Create a New Document
========================
POST http:/localhost:8080/sales
using src/main/resources/PostRequest.json

To Update an Existing Document
==============================
PUT http:/localhost:8080/sales
using src/main/resources/PutRequest.json

To Fetch an Existing Document
=============================
POST http:/localhost:8080/getsales
using src/main/resources/GetData_usingPOST.json