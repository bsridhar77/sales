To Create a New Sales Document
==============================
POST http:/localhost:8080/sales
using src/main/resources/PostNewSales.json

To Update an Existing Type of a Sales Document
==============================================
PUT http:/localhost:8080/sales
using src/main/resources/UpdateExistingTypeForSales.json

To Add a New type to an Existing Sales Document
===============================================
POST http:/localhost:8080/getsales
using src/main/resources/PostNewTypeForSales.json

To Fetch an Existing Sales Document
===================================
POST http:/localhost:8080/getsales
using src/main/resources/FetchSalesUsingPOST.json
