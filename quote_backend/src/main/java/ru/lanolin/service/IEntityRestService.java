package ru.lanolin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class IEntityRestService<
        Entity,
        Index,
        Repository extends JpaRepository<Entity, Index>
        > {
    protected final Repository repository;

    public <T> void updateIfNonNull(Entity inDb, Function<Entity, T> getValue, BiConsumer<Entity, T> updateValue) {
        T getV = getValue.apply(inDb);
        if (Objects.nonNull(getV)) {
            updateValue.accept(inDb, getV);
        }
    }

    public boolean existById(Index id) {
        return repository.existsById(id);
    }

    public Page<Entity> getAll(Pageable page) {
        return repository.findAll(page);
    }

    public Optional<Entity> getOne(Index id) {
        return repository.findById(id);
    }

    public void delete(Index id) {
        repository.deleteById(id);
    }

    public abstract Entity create(Entity toCreate);

    public abstract Entity update(Index id, Entity toUpdate);
}
