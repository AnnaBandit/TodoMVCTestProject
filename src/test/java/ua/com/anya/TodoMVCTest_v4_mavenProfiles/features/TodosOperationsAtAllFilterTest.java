package ua.com.anya.TodoMVCTest_v4_mavenProfiles.features;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import ua.com.anya.TodoMVCTest_v4_mavenProfiles.categories.Buggy;
import ua.com.anya.TodoMVCTest_v4_mavenProfiles.categories.Smoke;
import ua.com.anya.TodoMVCTest_v4_mavenProfiles.testconfigs.SetBrowserWithClearedDataAfterEachTest;

import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.Task.Status.ACTIVE;
import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.Task.Status.COMPLETED;
import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.Task.aTask;
import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.TodoMVC.*;

public class TodosOperationsAtAllFilterTest extends SetBrowserWithClearedDataAfterEachTest {

    @Test
    public void testCreate(){
        ensureOpenedTodoMVC();

        add("a", "b");
        assertExistingTasks("a", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testEdit(){
        givenAtAll("a", "b", "c");

        startEdit("b", "b-edited").pressEnter();
        assertExistingTasks("a", "b-edited", "c");
        assertItemsLeft(3);
    }

    @Test
    @Category(Smoke.class)
    public void testCompleteAll(){
        givenAtAll("a", "b");

        toggleAll();
        assertExistingTasks("a", "b");
        assertItemsLeft(0);
        openCompletedFilter();
        assertVisibleTasks("a", "b");
    }

    @Test
    @Category(Smoke.class)
    public void testClearCompleted(){
        given(aTask("a", COMPLETED),
                aTask("b", COMPLETED),
                aTask("c", ACTIVE));

        clearCompleted();
        assertExistingTasks("c");
        assertItemsLeft(1);
    }

    @Test
    @Category(Smoke.class)
    public void testActivateAll(){
        given(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        toggleAll();
        assertItemsLeft(2);
        openActiveFilter();
        assertVisibleTasks("a", "b");
    }

    @Test
    @Category(Buggy.class)
    public void testActivate(){
        given(aTask("a", COMPLETED),
                aTask("b", COMPLETED));

        toggle("a");
        assertItemsLeft(1);
        openActiveFilter();
        assertVisibleTasks("a", "b"); //here is a "bug"
    }

    @Test
    @Category(Smoke.class)
    public void testCancelEditingByESC(){
        givenAtAll("a");

        startEdit("a", "a-edited").pressEscape();
        assertExistingTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndSaveByClickingOutside(){
        givenAtAll("a", "b");

        startEdit("b", "b-edited");
        add("c");
        assertExistingTasks("a", "b-edited", "c");
        assertItemsLeft(3);
    }

    @Test
    public void testDeleteWhileEditing(){
        givenAtAll("a", "b");

        startEdit("a", "").pressEnter();
        assertExistingTasks("b");
        assertItemsLeft(1);
    }

    @Test
    public void testDelete(){
        ensureOpenedTodoMVC();

        add("a", "b");
        assertExistingTasks("a", "b");

        delete("a");
        assertExistingTasks("b");
        assertItemsLeft(1);
    }
}
