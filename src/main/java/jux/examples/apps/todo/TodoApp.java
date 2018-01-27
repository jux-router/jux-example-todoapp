package jux.examples.apps.todo;

import jux.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jux.HttpMethod.*;

/**
 * Entry point for the TO-DO application.
 *
 * @author Sandor Nemeth
 */
public class TodoApp {

    /**
     * Main entry point.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        TodoListController ctrl = new TodoListController(new TodoList());


        Router router = Jux.router();
        router
                .handle("/todo", ctrl::listTodos).methods(GET).and()
                .handle("/todo", ctrl::createTodo).methods(POST).and()
                .handle("/todo/{id}", ctrl::getTodo).methods(GET).and()
                .handle("/todo/{id}", ctrl::updateTodo).methods(PUT).and()
                .handle("/todo/{id}", ctrl::deleteTodo).methods(DELETE);

        Jux.start(8080, router);
    }

    /**
     * Controller for the to-do list.
     */
    static class TodoListController {

        private TodoList todoList;

        public TodoListController(TodoList todoList) {
            this.todoList = todoList;
        }

        void listTodos(Exchange exchange) {
            List<TodoItem> todos = this.todoList.findAll();
            Response response = Response.ok(todos).asJson();
            exchange.response(response);
        }

        void createTodo(Exchange exchange) {
            TodoItem newItem = exchange.request().getBody(TodoItem.class);
            newItem.setId(UUID.randomUUID());
            this.todoList.add(newItem);
            exchange.response(Response.status(HttpCode.OK).asJson());
        }

        void getTodo(Exchange exchange) {
            String id = exchange.request().getParam("id").orElseThrow(() -> new IllegalStateException(""));

            Response response = this.todoList.get(id)
                    .map(item -> Response.ok(item).asJson())
                    .orElse(Response.status(HttpCode.NOT_FOUND));

            exchange.response(response);
        }

        void updateTodo(Exchange exchange) {
            TodoItem newItem = exchange.request().getBody(TodoItem.class);
            String id = exchange.request().getParam("id").orElseThrow(() -> new IllegalStateException(""));
            if (!Objects.equals(id, newItem.getId().toString())) {
                throw new IllegalStateException("ID as parameter and in the body doesn't match");
            }
            this.todoList.update(newItem);

            exchange.response(Response.status(HttpCode.OK).asJson());
        }

        void deleteTodo(Exchange exchange) {
            String id = exchange.request().getParam("id").orElseThrow(() -> new IllegalStateException(""));

            this.todoList.delete(id);
            exchange.response(Response.status(HttpCode.OK).asJson());
        }

    }

}
