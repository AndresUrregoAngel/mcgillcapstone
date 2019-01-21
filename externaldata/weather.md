
## Weather on fireman stations 

There is a historical and public dataset called [NOAA GHCND](https://governmentshutdown.noaa.gov/) this public dataset keep the data collection by a broad of temperature stations close to an specific LONGITUDE or LATITUDE you define. In the query below I query the stations collectors close to a fireman station for 20 KM distance for teh year 2018.





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
 and element in ('TMAX','TMIN','TAVG','PRCP','SNOW','SNWD','WSFG','AWDR','AWND','TSUN')   
 OR element like 'WT%' OR element like 'WV%'  
 order by closest.id, data.date 

-- SELECT * FROM firemandb.firemanstations
```
