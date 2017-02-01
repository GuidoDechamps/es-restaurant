# Event sourced proces managers



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