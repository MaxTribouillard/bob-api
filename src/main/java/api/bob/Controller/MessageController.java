package api.bob.Controller;

import api.bob.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")

public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public String hello(){
        return "API back prête";
    }

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> loadResponse(@RequestBody Map<String,String> body){
        String message = body.get("message");
        String model = "phi-3-mini-4k-instruct";
        String response = messageService.getResponse(message, model);

        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("response", response);
    return ResponseEntity.ok(jsonResponse);
    }

}
