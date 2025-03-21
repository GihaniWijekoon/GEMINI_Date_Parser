package com.example.service;

import com.example.model.Request;
import com.example.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    // Constructor injection
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }
}
