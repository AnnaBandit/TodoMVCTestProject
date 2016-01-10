package ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.Task.Status.ACTIVE;
import static ua.com.anya.TodoMVCTest_v4_mavenProfiles.pages.Task.Status.COMPLETED;

public class TodoMVC {
    public static ElementsCollection tasksList = $$("#todo-list li");
    public static ElementsCollection visibleTasks = tasksList.filter(visible);

    public static void add(String... tasksTexts){
        for(String taskText: tasksTexts){
            $("#new-todo").shouldBe(enabled).setValue(taskText).pressEnter();
        }
    }

    public static void assertVisibleTasks(String... tasksTexts){
        visibleTasks.shouldHave(exactTexts(tasksTexts));
    }

    public static void assertVisibleTasksListIsEmpty(){
        visibleTasks.shouldBe(empty);
    }

    public static void assertExistingTasks(String... tasksTexts){
        tasksList.shouldHave(exactTexts(tasksTexts));
    }

    public static void assertExistingTasksListIsEmpty(){
        tasksList.shouldBe(empty);
    }

    public static SelenideElement startEdit(String taskText, String newTaskText){
        tasksList.find(exactText(taskText)).find("label").doubleClick();
        return tasksList.find(cssClass("editing")).find(".edit").setValue(newTaskText);
    }

    public static void delete(String taskText){
        tasksList.find(exactText(taskText)).hover().find(".destroy").click();
    }

    public static void toggle(String taskText){
        tasksList.find(exactText(taskText)).find(".toggle").click();
    }

    public static void toggleAll(){
        $("#toggle-all").click();
    }

    public static void clearCompleted(){
        $("#clear-completed").click();
        $("#clear-completed").shouldBe(hidden);
    }

    public static void assertItemsLeft(Integer counter){
        $("#todo-count>strong").shouldHave(exactText(String.valueOf(counter)));
    }

    public static void openAllFilter(){
        $(By.linkText("All")).click();
    }

    public static void openActiveFilter(){
        $(By.linkText("Active")).click();
    }

    public static void openCompletedFilter(){
        $(By.linkText("Completed")).click();
    }

    public static void ensureOpenedTodoMVC(){
        if (url()!=("https://todomvc4tasj.herokuapp.com/")) {
            open("https://todomvc4tasj.herokuapp.com/");
        }
    }

    public static void given(Task... tasks){
        ensureOpenedTodoMVC();

        String js = "localStorage.setItem('todos-troopjs', '[";
        for (Task task: tasks){
            boolean isCompleted = task.getStatus() == COMPLETED;
            js += "{\"completed\":" + isCompleted + ", \"title\":\"" +  task.text + "\"},";
        }
        js = js.substring(0, js.length()-1) + "]');";

        executeJavaScript(js);
        refresh();
    }

    public static void givenAtAll(String... tasksTexts){
        given(convertTaskTextsIntoActiveTasks(tasksTexts));
    }

    public static void givenAtActive(Task... tasks){
        given(tasks);
        openActiveFilter();
    }

    public static void givenAtActive(String... tasksTexts){
        givenAtActive(convertTaskTextsIntoActiveTasks(tasksTexts));
    }

    public static void givenAtCompleted(Task... tasks){
        given(tasks);
        openCompletedFilter();
    }

    public static void givenAtCompleted(String... tasksTexts){
        given(convertTaskTextsIntoActiveTasks(tasksTexts));
        openCompletedFilter();
    }

    private static Task[] convertTaskTextsIntoActiveTasks(String...tasksTexts){
        ArrayList<Task> tasks = new ArrayList<Task>();
        for (String taskText: tasksTexts) {
            tasks.add(new Task(taskText, ACTIVE));
        }
        return tasks.toArray(new Task[tasks.size()]);
    }
}
