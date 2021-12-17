# Philips Hue Lightstrip PLus

This container exposes the Philips Hue Lightstrip PLus using a REST API. It
expects at minimum the BRIGE_URL environment variable to point to the Philips 
Hue Bridge (e.g. http://192.168.1.10) and the ID environment variable to be set
to the id of the lightstrip.

## Turn the light on or off

The following request table describes what you need to pass to get an
information about a specific light. 

| Item | Value | Required | Notes |
|------|-------|----------|-------|
| URL | /on | yes | |
| Method | PUT | yes | |
| Body | boolean | yes | true to turn on, false to turn off |

The following response table descibes what the container can respond

| HTTP Code | Body |
|-----------|------|
| 200 | |

## Set the brightness

The following request table describes what you need to pass to get an
information about a specific light. 

| Item | Value | Required | Notes |
|------|-------|----------|-------|
| URL| /brightness | yes | |
| Method | PUT | yes | |
| Body | number | yes | value from 0 to 255 |

The following response table descibes what the container can respond

| HTTP Code | Body |
|-----------|------|
| 200 | |
