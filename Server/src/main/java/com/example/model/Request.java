package com.example.model;

import jakarta.persistence.*;
import lombok.*;

// Generates setters automatically
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor  // Generates an all-arguments constructor
@ToString  // Generates toString() method automatically
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestText;
    private String parsedDate;

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public String getParsedDate() {
        return parsedDate;
    }

    public void setParsedDate(String parsedDate) {
        this.parsedDate = parsedDate;
    }

}
