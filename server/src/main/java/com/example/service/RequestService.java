package com.example.service;

import com.example.controller.RequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Request;
import com.example.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestRepository requestRepository;

    // Constructor injection
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request saveRequest(Request request) {
        logger.info("in the request service: ", request);
        return requestRepository.save(request);
    }
}
