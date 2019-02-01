
## Weather on fireman stations 

There is a historical and public dataset called NOAA GHCND `ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/readme.txt` this public dataset keep the data collection by a broad of temperature stations close to an specific LONGITUDE or LATITUDE you define. In the query below I query the stations collectors close to a fireman station for 20 KM distance for teh year 2018.


##### Query sample

``` sql
select closest.id, closest.name, closest.latitude, closest.longitude, element as stats_name, value as stats_value, date as measure_date  
from `bigquery-public-data.ghcn_d.ghcnd_2018` as data right join (  
  select id, name, state, latitude, longitude, ST_DISTANCE(ST_GEOGPOINT(longitude, latitude),ST_GEOGPOINT( -73.5601720032226, 45.4934543793718 )) / 1000.0 as distance  
  from `bigquery-public-data.ghcn_d.ghcnd_stations`  
  where longitude is not null and latitude is not null) as closest on data.id = closest.id 
where  
 extract(year from data.date) =  2018 
 and closest.distance <= 20  
 and element in ('TMAX','TMIN','TAVG','PRCP','SNOW','SNWD')   
 order by closest.id, data.date 

-- SELECT * FROM firemandb.firemanstations
```

#### Measures

For a while I have catch few useful factore metrics that might be interesting for our project. I would like point over:

- TMAX : Maximum temperature on celcius deegres
- TMIN : Minimum temperature on celcius deegres
- TAVG : Avarage temperature on celcius deegres
- PRCP : The possibility of a precipitation
- SNOW : Snow factor on centimeteers 
- SNWD : Wind factor

#### Pipeline collection

We could loop on the coordanates by station pulling the years along with the suitable measures. Later we could store the output into a staging dfataset to transform or pivot the table according with our needs and finally join this as external dimension in our model.

#### Sample outoput dimension 


This weather dimension will contains the fireman station ID `caserne` in order to join this dimension with the other dimensions of the model to build up a centralized input for the ML model.

|eventdate|caserne|TMAX_mean|TMIN_mean|TAVG_mean|PRCP_mean|SNOW_mean|SNWD_mean|
|---------|-------|---------|---------|---------|---------|---------|---------|
|2015-08-23|50|21|10|15|12|0|12|
