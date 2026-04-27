import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service(Service.Level.PROJECT)
public final class AIService {

    private final Project currProject;
    private String chatResult;
    private final List<ChatMessage> chatPrompt;

    AIService(Project project) {
        currProject = project;
        chatResult = "";
        chatPrompt = new ArrayList<>();
    }

    public void serve() {
        String path = currProject.getBasePath();
        chatPrompt.add(new UserMessage("This is my project."));
        try {
            browseProject(path + File.separator + "src");

            //extension .md not specified so the answer comes in form of a text
            chatPrompt.add(new UserMessage("Write the content for a README for this project.\nDo not write a breakdown of the project. Do not write it inside ```markdown ```"));

            feedIt();

            if (!createReadme())
                throw new Exception("\nREADME file could not be created\n");
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void browseProject(String path) throws Exception {
        File dir = new File(path);
        if (!dir.isDirectory()) // might happen only at first call, since it is assured that path leads to a directory every other time
            throw new Exception("Could not find src directory at browseProject(...)");
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file: files) {
            if (file.isFile()) {
                try {
                    String fileContent = Files.readString(file.getAbsoluteFile().toPath());
                    chatPrompt.add(new UserMessage(file.getPath() + "\n" + fileContent + "\n\n"));
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if (file.isDirectory()) {
                browseProject(file.getAbsolutePath());
            }
        }
    }

    private void feedIt() {
        ChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .modelName("gemini-2.5-flash-lite")
                .build();

        chatResult = model.chat(chatPrompt).aiMessage().text();
    }

    private Boolean createReadme() {
        StringBuilder path = new StringBuilder(currProject.getBasePath());
        path.append("/README.md");
        File file = new File(path.toString());
        try (FileWriter fw = new FileWriter(path.toString())) {
            if (!file.isFile()) {
                return false;
            }
            fw.write(chatResult);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return true;
    }


}