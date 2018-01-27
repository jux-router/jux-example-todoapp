package jux.examples.apps.todo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The to-do list keeps track of all {@link TodoItem to-dos}.
 *
 * @author Sandor Nemeth
 */
public class TodoList {

    private ConcurrentHashMap<UUID, TodoItem> items = new ConcurrentHashMap<>();

    public Optional<TodoItem> get(String id) {
        return get(UUID.fromString(id));
    }

    private Optional<TodoItem> get(UUID id) {
        return Optional.ofNullable(items.get(id));
    }

    public void add(TodoItem item) {
        set(item);
    }

    public void update(TodoItem item) {
        set(item);
    }

    private void set(TodoItem item) {
        items.put(item.getId(), item);
    }

    public List<TodoItem> findAll() {
        return this.items.values().stream()
                .sorted(Comparator.comparing(TodoItem::getId))
                .collect(Collectors.toList());
    }


    public void delete(String id) {
        delete(UUID.fromString(id));
    }

    private void delete(UUID id) {
        this.items.remove(id);
    }
}
