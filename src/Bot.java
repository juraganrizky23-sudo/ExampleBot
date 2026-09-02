com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:287)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:259)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:26)
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3798)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2861)
	at org.telegram.telegrambots.api.methods.updates.GetUpdates.deserializeResponse(GetUpdates.java:114)
	... 2 more
Caused by: com.fasterxml.jackson.core.JsonParseException: Numeric value (6699755661) out of range of int
 at [Source: {"ok":true,"result":[{"update_id":295485690,
"message":{"message_id":1,"from":{"id":6699755661,"is_bot":false,"first_name":"bangkyy","username":"Sokkyyy","language_code":"id"},"chat":{"id":6699755661,"first_name":"bangkyy","username":"Sokkyyy","type":"private"},"date":1788367862,"text":"/start","entities":[{"offset":0,"length":6,"type":"bot_command"}]}}]}; line: 2, column: 50]
	at com.fasterxml.jackson.core.JsonParser._constructError(JsonParser.java:1702)
	at com.fasterxml.jackson.core.base.ParserMinimalBase._reportError(ParserMinimalBase.java:558)
	at com.fasterxml.jackson.core.base.ParserBase.convertNumberToInt(ParserBase.java:928)
	at com.fasterxml.jackson.core.base.ParserBase._parseIntValue(ParserBase.java:866)
	at com.fasterxml.jackson.core.base.ParserBase.getIntValue(ParserBase.java:694)
	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$IntegerDeserializer.deserialize(NumberDeserializers.java:306)
	at com.fasterxml.jackson.databind.deser.std.NumberDeserializers$IntegerDeserializer.deserialize(NumberDeserializers.java:286)
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	... 21 more
Sep 02, 2026 4:53:16 PM org.telegram.telegrambots.logging.BotLogger severe
SEVERE: BOTSESSION
org.telegram.telegrambots.exceptions.TelegramApiRequestException: Unable to deserialize response
	at org.telegram.telegrambots.api.methods.updates.GetUpdates.deserializeResponse(GetUpdates.java:122)
	at org.telegram.telegrambots.updatesreceivers.DefaultBotSession$ReaderThread.getUpdatesFromServer(DefaultBotSession.java:262)
	at org.telegram.telegrambots.updatesreceivers.DefaultBotSession$ReaderThread.run(DefaultBotSession.java:191)
Caused by: com.fasterxml.jackson.databind.JsonMappingException: Numeric value (6699755661) out of range of int
 at [Source: {"ok":true,"result":[{"update_id":295485690,
"message":{"message_id":1,"from":{"id":6699755661,"is_bot":false,"first_name":"bangkyy","username":"Sokkyyy","language_code":"id"},"chat":{"id":6699755661,"first_name":"bangkyy","username":"Sokkyyy","type":"private"},"date":1788367862,"text":"/start","entities":[{"offset":0,"length":6,"type":"bot_command"}]}}]}; line: 2, column: 50]
 at [Source: {"ok":true,"result":[{"update_id":295485690,
"message":{"message_id":1,"from":{"id":6699755661,"is_bot":false,"first_name":"bangkyy","username":"Sokkyyy","language_code":"id"},"chat":{"id":6699755661,"first_name":"bangkyy","username":"Sokkyyy","type":"private"},"date":1788367862,"text":"/start","entities":[{"offset":0,"length":6,"type":"bot_command"}]}}]}; line: 2, column: 40] (through reference chain: org.telegram.telegrambots.api.objects.replykeyboard.ApiResponse["result"]->java.util.ArrayList[0]->org.telegram.telegrambots.api.objects.Update["message"]->org.telegram.telegrambots.api.objects.Message["from"]->org.telegram.telegrambots.api.objects.User["id"])
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:388)
	at com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:348)
	at com.fasterxml.jackson.databind.deser.BeanDeserializerBase.wrapAndThrow(BeanDeserializerBase.java:1607)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:278)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.deser.SettableBeanProperty.deserialize(SettableBeanProperty.java:504)
	at com.fasterxml.jackson.databind.deser.impl.FieldProperty.deserializeAndSet(FieldProperty.java:108)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.vanillaDeserialize(BeanDeserializer.java:276)
	at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:140)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:287)
	at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:259)
	at 
