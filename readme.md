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

Commands added => invert relationship. Now cook nows where to send the messageToPublish to.
Changing messageToPublish of operations: pay for food first => it requires big software change. All actors need to change.
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
    
    
## random drop, duplication of messages

copy paste TTL with random numbers..

## Midget holds state of order as process
it should throw events when invalid events, state.
Now wrong in Cook and in WidgetFactory


##  Version proces manager

Don't change process
Make new version of process manager (process definition)
What with long running process? Then versions is required. Then it gets ugly.
==> Event sourced process manager
It can reinterpert then.

Make topic based pub sub into Event sourced
Change Map to Map<string, List< message based>> history

getHistoryFor (Topic)

## Homework

For event sourcing : deterministic guid generatie, command in same event uid. 
Try topic replace by event store.

Competing consumers

Still debug locally, can be ran in separate processed.
All typical integration issues can be found.
