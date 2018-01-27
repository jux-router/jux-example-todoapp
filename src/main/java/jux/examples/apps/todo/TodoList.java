/**
 * Copyright © ${project.inceptionYear} Sandor Nemeth (sandor.nemeth.1986 at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
