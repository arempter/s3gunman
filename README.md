# Test setup

## Setup login to AWS

```
export AWS_SESSION_TOKEN=token
export AWS_SECRET_ACCESS_KEY=secretkey
export AWS_ACCESS_KEY_ID=accesskey
```

## Setup S3
 
```
export S3_ENDPOINT=http://localhost:8010
export S3_PREFIX=subfolder1
export S3_BUCKET=demobucket
```

## Setup Gatling parameters

```
export GATLING_MULTIPART_ENABLED=false
export GATLING_SINGLE_UPLOAD_ENABLED=true
export GATLING_FILE_4_UPLOAD=/tmp/file20M.dd
export GATLING_DURATION=20
export GATLING_NO_OF_USERS=4
```

Note: GATLING_MULTIPART_ENABLED and GATLING_SINGLE_UPLOAD_ENABLED are mutually exclusive

Other scenario settings for GATLING_MULTIPART_ENABLED=true
```

GATLING_MULTIPART_FLAT=true - runs constantUsersPerSec during GATLING_DURATION
GATLING_MULTIPART_MIXED=true - runs rampUsers during GATLING_DURATION, then nothingFor 20 seconds, 
then constantUsersPerSec / 4, during GATLING_DURATION

Default (if both false) - runs rampUsers during GATLING_DURATION
```

## Run

1. build zip package

```
sbt universal:packageBin
```

2. run it in unzipped package 

```
. gunman_env.sh
sh bin/s3gunman
```