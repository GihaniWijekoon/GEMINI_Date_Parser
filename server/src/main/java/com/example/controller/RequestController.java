package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.model.Request;
import com.example.service.RequestService;
import com.example.service.ChatGPTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/requests")
public class RequestController {


    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;
    private final ChatGPTService chatGPTService;

    // Constructor
    public RequestController(RequestService requestService, ChatGPTService chatGPTService) {
        this.requestService = requestService;
        this.chatGPTService = chatGPTService;
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {

        // Call chatGPTService to get the parsed date
        String parsedDate = chatGPTService.getChatGPTResponse(request.getRequestText());
        logger.info("parsed data------: {}", parsedDate);

        // Set the parsed date in the request object (if required)
        request.setParsedDate(parsedDate); // Assuming you have a field for this in Request entity
        logger.info("set pasred data++++++++: {}", request);

        // Save the request with the parsed date
        Request savedRequest = requestService.saveRequest(request);
        logger.info("return ========: {}", savedRequest);
        return ResponseEntity.ok(savedRequest);

    }

}
