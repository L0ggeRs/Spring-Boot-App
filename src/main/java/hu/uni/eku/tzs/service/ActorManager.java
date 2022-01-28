package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;

import java.util.List;

public interface ActorManager {
    Actor record(Actor actor) throws ActorAlreadyExistsException;

    Actor readById(int id) throws ActorNotFoundException;

    List<Actor> readAll();

    Actor modify(Actor actor);

    void delete(Actor actor) throws ActorNotFoundException;

    List<Role> readRole(int id) throws ActorNotFoundException;
}
