### Some of the commands we might get clear working with SDK GCP for this project

##### Account
- `gcloud config set create newaccount`
- `gcloud config set project projectname`
- `gcloud config	set account tatatta@gmail.com`
- `gcloud auth login`
- `gcloud config configuration activate project`


##### Computing
- `gcloud compute instances list`
- `gcloud compute instances start vm`
- `gcloud compute instances stop vm`

##### Datalab
- `datalab connect --zone northamerica-northeast1-a --project projectname --no-user-checking vm`


##### APIs

- `gcloud components list`

##### BigQuery

- `bq query --nouse_legacy_sql 'select * from `firemandb.firemanstations` limit 10'`
