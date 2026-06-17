package api.bob.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.*;
import java.util.*;

@Service
public class MessageService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String lmStudioUrl = "http://127.0.0.1:1234/api/v1/chat";
    private String sys_prompt;
    public String getResponse(String message, String model){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> bodyUser = new HashMap<>();
        bodyUser.put("model", model);
        String messageFormate = "<|system|>\nTu es Bob, un assistant IA amical, direct et concis. Tu réponds directement à l'utilisateur au présent sans analyser sa phrase.<|end|>\n<|user|>\n" + message + "<|end|>\n<|assistant|>";
        bodyUser.put("input", messageFormate);
        bodyUser.put("stream", false);

        HttpEntity<Map<String, Object>> req = new HttpEntity<>(bodyUser, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(lmStudioUrl, req, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("output")) {
                // 1. On récupère la liste "output"
                List<Map<String, Object>> outputList = (List<Map<String, Object>>) body.get("output");

                // 2. On vérifie que la liste n'est pas vide et on prend le premier élément
                if (outputList != null && !outputList.isEmpty()) {
                    Map<String, Object> firstMessage = outputList.get(0);

                    // 3. On extrait enfin le "content" !
                    String texteIA = (String) firstMessage.get("content");
                    return texteIA;
                }

            }
            return "Structure de réponse inconnue : " + body;
        } catch (Exception e ){
            System.out.println("Erreur : " + e);
            return "Erreur";
        }



    }

}
