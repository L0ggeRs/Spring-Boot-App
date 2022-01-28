package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.RoleEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorManagerImpl implements ActorManager {

    private final String message = "Cannot find actor with ID %s";

    private final ActorRepository actorRepository;

    private static Actor convertActorEntity2Model(ActorEntity actorEntity) {
        return new Actor(
                actorEntity.getId(),
                actorEntity.getFirstName(),
                actorEntity.getLastName(),
                actorEntity.getGender()
        );
    }

    private static ActorEntity convertActorModel2Entity(Actor actor) {
        return ActorEntity.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .gender(actor.getGender())
                .build();
    }

    private static Role convertRoleEntity2Model(RoleEntity roleEntity) {
        return new Role(
                roleEntity.getRole(),
                new Actor(
                        roleEntity.getActor().getId(),
                        roleEntity.getActor().getFirstName(),
                        roleEntity.getActor().getLastName(),
                        roleEntity.getActor().getGender()
                ),
                new Movie(
                        roleEntity.getMovie().getId(),
                        roleEntity.getMovie().getName(),
                        roleEntity.getMovie().getYear(),
                        roleEntity.getMovie().getRank()
                )
        );
    }

    @Override
    public Actor record(Actor actor) throws ActorAlreadyExistsException {
        if (actorRepository.findById(actor.getId()).isPresent()) {
            throw new ActorAlreadyExistsException();
        }
        ActorEntity actorEntity = actorRepository.save(
                ActorEntity.builder()
                        .id(actor.getId())
                        .firstName(actor.getFirstName())
                        .lastName(actor.getLastName())
                        .gender(actor.getGender())
                        .build()
        );
        return convertActorEntity2Model(actorEntity);
    }

    @Override
    public Actor readById(int id) throws ActorNotFoundException {
        Optional<ActorEntity> entity = actorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ActorNotFoundException(String.format(message, id));
        }
        return convertActorEntity2Model(entity.get());
    }

    @Override
    public List<Actor> readAll() {
        return actorRepository.findAll().stream().map(ActorManagerImpl::convertActorEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> readRole(int id) throws ActorNotFoundException {
        Optional<ActorEntity> entity = actorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ActorNotFoundException(String.format(message, id));
        }
        return entity.get().getRoles().stream()
                .map(ActorManagerImpl::convertRoleEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Actor modify(Actor actor) {
        ActorEntity entity = convertActorModel2Entity(actor);
        return convertActorEntity2Model(actorRepository.save(entity));
    }

    @Override
    public void delete(Actor actor) throws ActorNotFoundException {
        actorRepository.delete(convertActorModel2Entity(actor));
    }
}
