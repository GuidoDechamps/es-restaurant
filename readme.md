# Event sourced proces managers


## Add ids to events
|MessageType|ID |CorrID| CauseId|
|-----------|---|------|--------|
|OrderPlaced| 7 | 19   |  -1    |
|CoockFood  | 8 | 19   |   7    |
|FoodCooked | 9 | 19   |   8    |
|PricedOrder| 10| 19   |   9    |


ID: unique event id
CorrId: Correlation
CauseId: Id of event you are responding to
    
usually placed in headers, not in payload of message/event

## CorrelationIdPrinter Get orderId.
in separate branch. Publish/subscribe on correlationIds

## Introduce commands

Commands added => invert relationship. Now cook nows where to send the order to.
Changing order of operations: pay for food first => it requires big software change. All actors need to change.
4 microservices/teams need to coordinate a release. + downtime.
WHTF? :-)

=> Enter process manager (midget)

Cut dependencies between actors. Actors only say that they are done. Midget handles flow.

## Implement Process Manager

* started by event (OrderPlaced)
* subscribe to the MidgetHouse
    * Map of correlationId. Midgets listen to all events from process correlationId
    * subscribe midget to correlationId or subscribe house.
    * Threaded handlers important to make actors single threaded. 