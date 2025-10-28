package com.gk.springaipoc;

import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private static final SystemPromptTemplate CONTEXT = new SystemPromptTemplate("""
            We are the best company that offers loans  {documents}
            """);

    public ChatService(ChatModel chatClient, VectorStore vectorStore) {
        this.chatClient = ChatClient.builder(chatClient).build();
        this.vectorStore = vectorStore;
    }

    public Message sendChatMessage(String message) {
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = getPrompt(userMessage);
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput();
    }

    private Prompt getPrompt(UserMessage userMessage) {
        Message context = getContext(userMessage.getContent());
        List<Message> messages = List.of(userMessage, context);
        ChatOptions chatOptions = getChatOptions();
        return new Prompt(messages, chatOptions);
    }

    private Message getContext(String message) {
        String context = vectorStore.similaritySearch(message)
                .stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        return CONTEXT.createMessage(Map.of("documents", context));
    }


    private ChatOptions getChatOptions() {
        return AzureOpenAiChatOptions.builder()
                .withFunction("checkCreditLimit")
                .build();
    }
}
