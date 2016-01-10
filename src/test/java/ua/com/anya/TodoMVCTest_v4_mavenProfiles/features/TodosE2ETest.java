package ua.com.anya.TodoMVCTest_v4_mavenProfiles.features;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import ua.com.anya.TodoMVCTest_v4_mavenProfiles.categories.Smoke;
import ua.com.anya.TodoMVCTest_v4_mavenProfiles.testconfigs.SetBrowserWithClearedDataAfterEachTest;

import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.TodoMVC.*;

@Category(Smoke.class)
public class TodosE2ETest extends SetBrowserWithClearedDataAfterEachTest {

    @Test
    public void testTasksLifeCycleAndFiltering(){

        ensureOpenedTodoMVC();

        add("a");
        startEdit("a", "a-edited").pressEnter();
        toggle("a-edited");
        assertExistingTasks("a-edited");

        openActiveFilter();
        assertVisibleTasksListIsEmpty();

        openCompletedFilter();
        assertVisibleTasks("a-edited");

        //activate
        toggle("a-edited");
        assertVisibleTasksListIsEmpty();

        openActiveFilter();
        assertVisibleTasks("a-edited");

        toggle("a-edited");
        assertVisibleTasksListIsEmpty();

        openAllFilter();
        assertExistingTasks("a-edited");

        delete("a-edited");
        assertExistingTasksListIsEmpty();
    }
}
