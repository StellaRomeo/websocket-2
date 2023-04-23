package co.develhope.websocket2.controller;

import co.develhope.websocket2.entities.ClientMessageDTO;
import co.develhope.websocket2.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendBroadcast(@RequestBody MessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/topic/broadcast", messageDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO handleClientMessage( ClientMessageDTO message) {
        return new MessageDTO( message.getClientName(), message.getClientAlert(), message.getClientMsg());

    }

}