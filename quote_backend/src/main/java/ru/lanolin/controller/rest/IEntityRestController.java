package ru.lanolin.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import ru.lanolin.service.IEntityRestService;
import ru.lanolin.utils.converter.EntityToDtoConverter;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class IEntityRestController<
        Entity, EntityDTO, Index, Service extends IEntityRestService<Entity, Index, ?>
        > {

    private final Service entityService;
    private final EntityToDtoConverter<Entity, EntityDTO> converter;

    @GetMapping(params = {"page", "size"})
    public Page<EntityDTO> getAll(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) @PageableDefault Pageable page) {
        return entityService.getAll(page).map(converter::toDTO);
    }

    @GetMapping("/{id}")
    public Optional<EntityDTO> getOne(@PathVariable Index id) {
        return entityService.getOne(id).map(converter::toDTO);
    }

    @PutMapping()
    public EntityDTO create(@RequestBody EntityDTO toCreate) {
        Entity entity = converter.toEntity(toCreate);
        Entity saved = entityService.create(entity);
        return converter.toDTO(saved);
    }

    @PostMapping("/{id}")
    public EntityDTO update(@PathVariable Index id, @RequestBody EntityDTO toUpdate) {
        Entity entity = converter.toEntity(toUpdate);
        Entity updated = entityService.update(id, entity);
        return converter.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Index id) {
        entityService.delete(id);
    }
}
