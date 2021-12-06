# Philips Hue Lightstrip PLus

This container exposes the Philips Hue Lightstrip PLus using a REST API. It
expects at minimum the BRIGE_URL environment variable to point to the Philips 
Hue Bridge (e.g. http://192.168.1.10) and the ID environment variable to be set
to the id of the lightstrip.

## Set the On state of the light

The following request table describes what you need to pass to get an
information about a specific light. 

| Item | Value | Required | Notes |
|------|-------|----------|-------|
| URL | /lights/{id} | yes | where {id} is an number |
| Method | PUT | yes | |
| Body | TODO | yes |

The following response table descibes what the container can respond

| HTTP Code | Body |
|-----------|------|
| 200 | |
