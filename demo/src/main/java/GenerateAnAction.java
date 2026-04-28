import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class GenerateAnAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent event) {

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "Generating README.md") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    AIService service = new AIService(project);
                    service.serve();

                    ApplicationManager.getApplication().invokeLater(() -> {
                        Messages.showInfoMessage(project, "Your README.md is ready!", "README.md Generated");
                    });
                }
                catch(Exception ex) {
                    ApplicationManager.getApplication().invokeLater(() -> {
                        Messages.showErrorDialog(project, ex.getMessage(), "Something went wrong");
                    });
                }
            }
        });

        Messages.showMessageDialog(
                project,
                "Your README.md is being generated, this might take a minute...",
                "Action Generate README called!",
                Messages.getInformationIcon()
        );
    }
}