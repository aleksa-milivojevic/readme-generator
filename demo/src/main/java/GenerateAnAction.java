import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                AIService service = new AIService(project);
                service.serve();
            }
        }).start();

        Messages.showMessageDialog(
                project,
                "Your README.md is being generated, this might take a minute...",
                "Action Generate README called!",
                Messages.getInformationIcon()
        );
    }
}