# Harbor Take Home Project

Welcome to the Harbor take home project. We hope this is a good opportunity for you to showcase your skills.

## The Challenge

Build us a REST API for calendly. Remember to support

- Setting own availability
- Showing own availability
- Finding overlap in schedule between 2 users

It is up to you what else to support.

## Expectations

We care about

- Have you thought through what a good MVP looks like? Does your API support that?
- What trade-offs are you making in your design?
- Working code - we should be able to pull and hit the code locally. Bonus points if deployed somewhere.
- Any good engineer will make hacks when necessary - what are your hacks and why?

We don't care about

- Authentication
- UI
- Perfection - good and working quickly is better

It is up to you how much time you want to spend on this project. There are likely diminishing returns as the time spent goes up.

## Submission

Prod
==========

Replace https://localhost:8080 with the following URL for production. Its hosted in GCP. Will shutdown cluster on 4th Sep 9PM IST, due to cost reasons.
```shell
https://calendly-grhqzbupoq-el.a.run.app
```

** Would love to talk about tradeoffs and though behind this MVP in person.

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
curl --location 'localhost:8080/user-availability/view/447PUKMRK15XTBAAR5MS'
```


Create Availability
==================

USER-1
```shell
curl --location 'localhost:8080/user-availability/create/447PUKMRK15XTBAAR5MS' \
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
curl --location 'localhost:8080/user-availability/create/ABDDSPBHK1WGV4XNBSTO' \
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
curl --location 'localhost:8080/user-availability/overlap/ABDDSPBHK1WGV4XNBSTO/447PUKMRK15XTBAAR5MS'
```

```agsl
Here is the "output" overlapping slot:

For User "ABDDSPBHK1WGV4XNBSTO":
Date: 1693958400 (Sep 3, 2023)
StartTime: 1693992000 (Sep 3, 2023, 12:00:00 UTC)
EndTime: 1693995600 (Sep 3, 2023, 13:00:00 UTC)

For User "447PUKMRK15XTBAAR5MS":
Date: 1693958400 (Sep 3, 2023)
StartTime: 1693992000 (Sep 3, 2023, 12:00:00 UTC)
EndTime: 1693999200 (Sep 3, 2023, 14:00:00 UTC)

*** The overlapping time slot is from 12:00:00 to 13:00:00 UTC on Sep 3, 2023. ***

```

Deploy to GCP Cloud Run
=======================
```shell
gcloud run deploy --add-cloudsql-instances harbor-xyz:asia-south1:harbor-xyz --set-env-vars SPRING_R2DBC_URL=r2dbc:postgresql://35.200.237.83:5432/postgres,SPRING_R2DBC_USERNAME=postgres,SPRING_R2DBC_PASSWORD=<YOUR_PASS>,SERVICE_PORT=8080
```