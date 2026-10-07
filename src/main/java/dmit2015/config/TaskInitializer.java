package dmit2015.config;

import dmit2015.model.Task;
import dmit2015.service.TaskJpaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import net.datafaker.Faker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class TaskInitializer {
    private final Logger logger = Logger.getLogger(TaskInitializer.class.getName());

    @Inject
    private TaskJpaService taskJpaService;

    public void initialize(@Observes @Initialized(ApplicationScoped.class) Object event) {
        logger.info("Initializing tasks");

        if (taskJpaService.count() == 0) {
            /* You have three options for creating the test data:
                Option 3) Generate the test data using DataFaker.
                          When used with Integration Testing you will need to save the generated data
                          to a file that can be read later to compare with expected values.
             */

            try {
                var faker = new Faker();
                for (int count = 1; count <=32; count++) {
                    Task currentTask = Task.of(faker);
                    taskJpaService.createTask(currentTask);
                }

            } catch (Exception ex) {
                logger.warning(ex.getMessage());
            }

            logger.info("Created " + taskJpaService.count() + " records.");
        }
    }
}