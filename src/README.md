
Create User
===========
```shell
curl --location 'localhost:8080/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "test2",
    "email": "test2@test.com",
    "timeZone": "IST"
}'
```

View Availability of a user
===========================
```shell
curl --location 'localhost:8080/user-availability/view/KYIRICDLC2M329UOSBF7'
```


Create Availability
==================

USER-1
```shell
curl --location 'localhost:8080/user-availability/create/KYIRICDLC2M329UOSBF7' \
--header 'Content-Type: application/json' \
--data ' [
            {
                "date": 1693958400,  // Sep 3, 2023, 00:00:00 UTC
                "startTime": 1693969200,  // Sep 3, 2023, 03:00:00 UTC
                "endTime": 1693972800  // Sep 3, 2023, 04:00:00 UTC
            },
            {
                "date": 1693958400,
                "startTime": 1693992000,  // Sep 3, 2023, 12:00:00 UTC
                "endTime": 1693995600  // Sep 3, 2023, 13:00:00 UTC
            }
]'
```

USER-2
```shell
curl --location 'localhost:8080/user-availability/create/D3KDECCVPNV51GZMGCXZ' \
--header 'Content-Type: application/json' \
--data '[
            {
                "date": 1693958400,
                "startTime": 1693962000,  // Sep 3, 2023, 01:00:00 UTC
                "endTime": 1693969200  // Sep 3, 2023, 03:00:00 UTC
            },
            {
                "date": 1693958400,
                "startTime": 1693992000,  // Sep 3, 2023, 12:00:00 UTC
                "endTime": 1693999200  // Sep 3, 2023, 14:00:00 UTC
            }
]'
```

Check Overlapping
=================
```shell
curl --location 'localhost:8080/user-availability/overlap/D3KDECCVPNV51GZMGCXZ/KYIRICDLC2M329UOSBF7'
```

```agsl
Here is the output overlapping slot:

For User "D3KDECCVPNV51GZMGCXZ":
Date: 1693958400 (Sep 3, 2023)
StartTime: 1693992000 (Sep 3, 2023, 12:00:00 UTC)
EndTime: 1693995600 (Sep 3, 2023, 13:00:00 UTC)

For User "KYIRICDLC2M329UOSBF7":
Date: 1693958400 (Sep 3, 2023)
StartTime: 1693992000 (Sep 3, 2023, 12:00:00 UTC)
EndTime: 1693999200 (Sep 3, 2023, 14:00:00 UTC)

*** The overlapping time slot is from 12:00:00 to 13:00:00 UTC on Sep 3, 2023. ***

```