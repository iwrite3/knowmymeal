package com.factbody.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepSeekRequest {

    private String model;
    private List<Message> messages;
    private double temperature;

    // UPDATED: Now properly handles the system instructions and the user's food prompt
    public DeepSeekRequest(String systemInstruction, String userPrompt) {
        this.model = "deepseek-chat";
        this.messages = List.of(
                new Message("system", systemInstruction),
                new Message("user", userPrompt)
        );
        // Lowered temperature to 0.7 for more factual and consistent nutritional data
        this.temperature = 0.7;
    }

    // Kept the original constructor just in case you need it elsewhere
    public DeepSeekRequest(String prompt) {
        this.model = "deepseek-chat";
        this.messages = List.of(new Message("user", prompt));
        this.temperature = 0.9;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public static class Message {
        private String role;
        private String content;

        public Message() {}
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}