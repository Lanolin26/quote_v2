package ru.lanolin.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanolin.domain.*;
import ru.lanolin.domain.dto.*;
import ru.lanolin.service.EntityRestService;
import ru.lanolin.utils.converter.EntityToDtoConverter;

import java.util.UUID;

public class EntityRestController {

    @RestController
    @RequestMapping("/api/entity/quote")
    public static class QuoteEntityRestController
            extends IEntityRestController<QuoteEntity, QuoteEntityDto, UUID, EntityRestService.QuoteEntityRestService> {

        @Autowired
        public QuoteEntityRestController(EntityRestService.QuoteEntityRestService entityService,
                                         EntityToDtoConverter<QuoteEntity, QuoteEntityDto> converter) {
            super(entityService, converter);
        }
    }

    @RestController
    @RequestMapping("/api/entity/source")
    public static class SourceEntityRestController
            extends IEntityRestController<SourceEntity, SourceEntityDto, UUID, EntityRestService.SourceEntityRestService> {

        public SourceEntityRestController(EntityRestService.SourceEntityRestService entityService,
                                          EntityToDtoConverter<SourceEntity, SourceEntityDto> converter) {
            super(entityService, converter);
        }
    }

    @RestController
    @RequestMapping("/api/entity/source-type")
    public static class SourceTypeEntityRestController
            extends IEntityRestController<SourceTypeEntity, SourceTypeEntityDto, UUID, EntityRestService.SourceTypeEntityRestService> {

        public SourceTypeEntityRestController(EntityRestService.SourceTypeEntityRestService entityService,
                                              EntityToDtoConverter<SourceTypeEntity, SourceTypeEntityDto> converter) {
            super(entityService, converter);
        }
    }

    @RestController
    @RequestMapping("/api/entity/user")
    public static class UserEntityRestController
            extends IEntityRestController<UserEntity, UserEntityDto, UUID, EntityRestService.UserEntityRestService> {

        public UserEntityRestController(EntityRestService.UserEntityRestService entityService,
                                        EntityToDtoConverter<UserEntity, UserEntityDto> converter) {
            super(entityService, converter);
        }
    }

    @RestController
    @RequestMapping("/api/entity/auth")
    public static class AuthEntityRestController
            extends IEntityRestController<AuthEntity, AuthEntityDto, UUID, EntityRestService.AuthEntityRestService> {

        public AuthEntityRestController(EntityRestService.AuthEntityRestService entityService,
                                        EntityToDtoConverter<AuthEntity, AuthEntityDto> converter) {
            super(entityService, converter);
        }
    }

}
