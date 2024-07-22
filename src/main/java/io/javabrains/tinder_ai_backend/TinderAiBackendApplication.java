package io.javabrains.tinder_ai_backend;

import io.javabrains.tinder_ai_backend.conversations.ChatMessage;
import io.javabrains.tinder_ai_backend.conversations.Conversation;
import io.javabrains.tinder_ai_backend.conversations.ConversationRepository;
import io.javabrains.tinder_ai_backend.profiles.Gender;
import io.javabrains.tinder_ai_backend.profiles.Profile;
import io.javabrains.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;
    @Autowired
	private OpenAiChatModel chatModel;
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ChatResponse response = chatModel.call(
				new Prompt(
						"Generate the names of 5 famous pirates.",
						OpenAiChatOptions.builder()
								.withModel("gpt-4o")
								.withTemperature(0.4f)
								.build()
				));
		System.out.println(response.getResult().getOutput());
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Profile profile = new Profile(
				"1",
				"Avirup",
				"Mallik",
				31,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				"foo.jpg",
				"INTP"
		);
		profileRepository.save(profile);
		 profile = new Profile(
				"2",
				"Rahul",
				"Dutta",
				31,
				"Indian",
				Gender.MALE,
				"Bank Employee",
				"foo1.jpg",
				"INTP"
		);

		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation("1",profile.id(), List.of(new ChatMessage("Hello",profile.id(), LocalDateTime.now())));
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);

	}
}
