package jux.examples.apps.todo;

import java.util.UUID;

/**
 * Model of a basic to-do item.
 *
 * @author Sandor Nemeth
 */
public class TodoItem {

    private UUID id = UUID.randomUUID();
    private String text;
    private boolean done = false;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
            this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
