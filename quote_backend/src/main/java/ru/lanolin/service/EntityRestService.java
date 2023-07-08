package ru.lanolin.service;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lanolin.domain.*;
import ru.lanolin.exceptions.FileOperationException;
import ru.lanolin.exceptions.NotFoundException;
import ru.lanolin.repos.*;

import java.util.Objects;
import java.util.UUID;

public class EntityRestService {

    @Service
    public static class QuoteEntityRestService extends IEntityRestService<QuoteEntity, UUID, QuoteEntityRepository> {

        private final SourceEntityRestService sourceEntityService;
        private final SourceTypeEntityRestService sourceTypeEntityService;
        private final UserEntityRestService userEntityService;

        @Autowired
        public QuoteEntityRestService(QuoteEntityRepository repository,
                                      SourceEntityRestService sourceEntityService,
                                      SourceTypeEntityRestService sourceTypeEntityService,
                                      UserEntityRestService userEntityService) {
            super(repository);
            this.sourceEntityService = sourceEntityService;
            this.sourceTypeEntityService = sourceTypeEntityService;
            this.userEntityService = userEntityService;
        }

        @Override
        public QuoteEntity create(QuoteEntity toCreate) {
            toCreate.setId(null);

            UUID authorId = toCreate.getAuthor().getId();
            if (!userEntityService.existById(authorId)) {
                throw new EntityExistsException("User Entity doesn't exits: " + authorId);
            }

            UUID sourceId = toCreate.getSource().getId();
            if (!sourceEntityService.existById(sourceId)) {
                throw new EntityExistsException("Source Entity doesn't exits: " + sourceId);
            }

            UUID sourceTypeId = toCreate.getSource().getType().getId();
            if (!sourceTypeEntityService.existById(sourceTypeId)) {
                throw new EntityExistsException("Source Type Entity doesn't exits: " + sourceTypeId);
            }
            return repository.save(toCreate);
        }

        @Override
        public QuoteEntity update(UUID id, QuoteEntity toUpdate) {
            QuoteEntity quoteEntity = repository.findById(id).orElseThrow(NotFoundException::new);

            updateIfNonNull(quoteEntity, toUpdate, QuoteEntity::getText, QuoteEntity::setText);
            updateIfNonNull(quoteEntity, toUpdate, qe -> qe.getSource().getId(), (qe, uuid) -> {
                SourceEntity sourceEntity = sourceEntityService.getOne(uuid)
                        .orElseThrow(() -> new EntityExistsException("Not found source entity type " + uuid));
                qe.setSource(sourceEntity);
            });
            updateIfNonNull(quoteEntity, toUpdate, qe -> qe.getAuthor().getId(), (qe, uuid) -> {
                UserEntity userEntity = userEntityService.getOne(uuid)
                        .orElseThrow(() -> new EntityExistsException("Not found user entity type " + uuid));
                qe.setAuthor(userEntity);
            });
            return repository.save(quoteEntity);
        }

    }

    @Service
    public static class SourceEntityRestService extends IEntityRestService<SourceEntity, UUID, SourceEntityRepository> {

        private final SourceTypeEntityRestService sourceTypeEntityService;

        @Autowired
        public SourceEntityRestService(SourceEntityRepository repository,
                                       SourceTypeEntityRestService sourceTypeEntityService) {
            super(repository);
            this.sourceTypeEntityService = sourceTypeEntityService;
        }

        @Override
        public SourceEntity create(SourceEntity toCreate) {
            if (repository.existsByNameIgnoreCase(toCreate.getName())) {
                throw new EntityExistsException("Entity exits by name " + toCreate.getName());
            }
            UUID sourceTypeId = toCreate.getType().getId();
            if (!sourceTypeEntityService.existById(sourceTypeId)) {
                throw new EntityExistsException("Source Type Entity doesn't exits: " + sourceTypeId);
            }
            toCreate.setId(null);
            return repository.save(toCreate);
        }

        @Override
        public SourceEntity update(UUID id, SourceEntity toUpdate) {
            SourceEntity sourceEntity = repository.findById(id).orElseThrow(NotFoundException::new);
            updateIfNonNull(sourceEntity, toUpdate, SourceEntity::getName, SourceEntity::setName);
            updateIfNonNull(sourceEntity, toUpdate, se -> se.getType().getId(), (se, uuid) -> {
                SourceTypeEntity type = sourceTypeEntityService
                        .getOne(uuid)
                        .orElseThrow(() -> new EntityExistsException("Not found entity type " + uuid));
                sourceEntity.setType(type);
            });
            return repository.save(sourceEntity);
        }
    }

    @Service
    public static class SourceTypeEntityRestService extends IEntityRestService<SourceTypeEntity, UUID, SourceTypeEntityRepository> {

        @Autowired
        public SourceTypeEntityRestService(SourceTypeEntityRepository repository) {
            super(repository);
        }

        @Override
        public SourceTypeEntity create(SourceTypeEntity toCreate) {
            if (repository.existsByNameIgnoreCase(toCreate.getName())) {
                throw new EntityExistsException("Entity exits by name " + toCreate.getName());
            }
            toCreate.setId(null);
            return repository.save(toCreate);
        }

        @Override
        public SourceTypeEntity update(UUID id, SourceTypeEntity toUpdate) {
            SourceTypeEntity sourceTypeEntity = repository.findById(id).orElseThrow(NotFoundException::new);
            updateIfNonNull(sourceTypeEntity, toUpdate, SourceTypeEntity::getName, SourceTypeEntity::setName);
            return repository.save(sourceTypeEntity);
        }
    }

    @Service
    public static class UserEntityRestService extends IEntityRestService<UserEntity, UUID, UserEntityRepository> {

        private final AvatarStorageService avatarStorage;

        @Autowired
        public UserEntityRestService(UserEntityRepository repository, AvatarStorageService avatarStorage) {
            super(repository);
            this.avatarStorage = avatarStorage;
        }

        @Override
        public UserEntity create(UserEntity toCreate) {
            if (repository.existsByNameIgnoreCase(toCreate.getName())) {
                throw new EntityExistsException("Entity exits by name " + toCreate.getName());
            }
            toCreate.setId(null);
            return repository.save(toCreate);
        }

        @Override
        public UserEntity update(UUID id, UserEntity toUpdate) {
            UserEntity userEntity = repository.findById(id).orElseThrow(NotFoundException::new);
            updateIfNonNull(userEntity, toUpdate, UserEntity::getName, UserEntity::setName);
            updateIfNonNull(userEntity, toUpdate, UserEntity::isIcon, UserEntity::setIcon);
            // TODO: copy AuthEntity
            return repository.save(userEntity);
        }

        @Override
        public void delete(UUID id) {
            super.delete(id);
            try {
                avatarStorage.deleteIcon(id.toString());
            } catch (FileOperationException ignored) {}
        }
    }

    @Service
    public static class AuthEntityRestService extends IEntityRestService<AuthEntity, UUID, AuthEntityRepository> {

        @Autowired
        public AuthEntityRestService(AuthEntityRepository repository) {
            super(repository);
        }

        @Override
        public AuthEntity create(AuthEntity toCreate) {
            if (repository.existsByEmail(toCreate.getEmail())) {
                throw new EntityExistsException("Entity exits by email " + toCreate.getEmail());
            }
            if (repository.existsByLogin(toCreate.getLogin())) {
                throw new EntityExistsException("Entity exits by login " + toCreate.getLogin());
            }
            toCreate.setId(null);
            return repository.save(toCreate);
        }

        @Override
        public AuthEntity update(UUID id, AuthEntity toUpdate) {
            AuthEntity authEntity = repository.findById(id).orElseThrow(NotFoundException::new);
            updateIfNonNull(authEntity, toUpdate, AuthEntity::getEmail, AuthEntity::setEmail);
            updateIfNonNull(authEntity, toUpdate, AuthEntity::getLogin, AuthEntity::setLogin);
            updateIfNonNull(authEntity, toUpdate, AuthEntity::getPassword, AuthEntity::setPassword);
            return repository.save(authEntity);
        }
    }

}
