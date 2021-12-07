# Philips Hue Bridge

This container exposes the Philips Hue Bridge using a REST API. It expects at 
minimum the BASE_URL environment variable to point to the Philips Hue Bridge 
(e.g. http://192.168.1.10/api). If the USERNAME environment variable is set then
 it will use that to interact with the bridge when authentication is required.

If you have not authenticated with the bridge before then perform the following
steps.

1. Press the link button on the Philips Hue Bridge.
2. Send the `/authenticate/` request (see [Authenticate with the bridge](#authenticate-with-the-bridge))
3. Capture the value of the authenticate response
4. Restart the  Philips Hue Bridge container with the USERNAME variable set to the captured value.

## Authenticate with the bridge

The following request table describes what you need to pass to authenticate with
the bridge. 

| Item | Value | Required |
|------|-------|----------|
| URL | /authenticate | yes |
| Method | POST | yes |
| Query parameters | username=&lt;username&gt; | yes |

The following response table descibes what the container can respond

| HTTP code | Body | Link button pressed |
|-----------|------|---------------|
| 200 | [ { "error" : { "type" : 101, "address" : "", "description" : "link button not pressed" } } | no |
| 200 | TODO | yes |

## Get information from the bridge

The following request table describes what you need to pass to get an
informational response from the bridge. 

| Item | Value | Required |
|------|-------|----------|
| URL | /config | yes |
| Method | GET | yes |

The following response table descibes what the container can respond

| HTTP Code | Body | Authenticated |
|------|------|---------------|
| 200 | { "name" : "THE_NAME", "datastoreversion" : "THE_DATASTORE_VERSION", "swversion" : "THE_SW_VERSION", "apiversion" : "THE_API_VERSION", "mac" : "THE_MAC_ADDRESS", "bridgeid" : "THE_BRIDGE_ID", "factorynew" : false, "replacesbridgeid" : null, "modelid" : "THE_MODEL_ID", "starterkitid" : "" } | no |
| 200 | { .... } | yes |

## Get information about a light

The following request table describes what you need to pass to get an
information about a specific light. 

| Item | Value | Required | Notes |
|------|-------|----------|-------|
| URL | /lights/{id} | yes | where {id} is an number |
| Method | GET | yes | |

The following response table descibes what the container can respond

| HTTP Code | Body |
|-----------|------|
| 200 | { .... } |

## Set a state of a light

The following request table describes what you need to pass to set a state for a specific light. 

| Item | Value | Required | Notes |
|------|-------|----------|-------|
| URL | /lights/{id}/state | yes | where {id} is an number |
| Method | PUT | yes | |
| Body | { .... } | yes | |

The following response table descibes what the container can respond

| HTTP Code | Body |
|-----------|------|
| 200 | |
