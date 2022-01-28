package hu.uni.eku.tzs.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActorManagerImplTest {

    @Mock
    ActorRepository actorRepository;

    @InjectMocks
    ActorManagerImpl service;

    @Test
    void recordActorHappyPath() throws ActorAlreadyExistsException {
        //given
        Actor rock = TestDataProviderService.getRock();
        ActorEntity rockEntity = TestDataProviderService.getRockEntity();
        when(actorRepository.findById(any())).thenReturn(Optional.empty());
        when(actorRepository.save(any())).thenReturn(rockEntity);
        //when
        Actor actual = service.record(rock);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(rock);
    }

    @Test
    void recordActorAlreadyExistsException() {
        //given
        Actor rock = TestDataProviderService.getRock();
        ActorEntity rockEntity = TestDataProviderService.getRockEntity();
        when(actorRepository.findById(TestDataProviderService.THE_ROCK_ID)).thenReturn(Optional.ofNullable(rockEntity));
        //when
        assertThatThrownBy(() -> service.record(rock)).isInstanceOf(ActorAlreadyExistsException.class);
    }

    @Test
    void readByIdHappyPath() throws ActorNotFoundException {
        //given
        when(actorRepository.findById(TestDataProviderService.THE_ROCK_ID))
                .thenReturn(Optional.of(TestDataProviderService.getRockEntity()));
        Actor expected = TestDataProviderService.getRock();
        //when
        Actor actual = service.readById(TestDataProviderService.THE_ROCK_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readRoleHappyPath() throws ActorNotFoundException {
        //given
        when(actorRepository.findById(TestDataProviderService.THE_ROCK_ID))
                .thenReturn(Optional.of(TestDataProviderService.getRockRolesEntity()));
        List<Role> expected = TestDataProviderService.getRoles();
        //when
        List<Role> actual = service.readRole(TestDataProviderService.THE_ROCK_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdActorNotFoundException() {
        //given
        when(actorRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readById(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(ActorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readRoleActorNotFoundException() {
        //given
        when(actorRepository.findById(TestDataProviderService.UNKNOWN_ID)).thenReturn(Optional.empty());
        //when then
        assertThatThrownBy(() -> service.readRole(TestDataProviderService.UNKNOWN_ID)).isInstanceOf(ActorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProviderService.UNKNOWN_ID));
    }

    @Test
    void readAllHappyPath() {
        //given
        List<ActorEntity> actorEntities = List.of(
                TestDataProviderService.getRockEntity()
        );
        List<Actor> expectedActors = List.of(
                TestDataProviderService.getRock()
        );
        when(actorRepository.findAll()).thenReturn(actorEntities);
        //when
        List<Actor> actualActors = service.readAll();
        //then
        assertThat(actualActors)
                .usingRecursiveComparison()
                .isEqualTo(expectedActors);
    }

    @Test
    void modifyActorHappyPath() {
        //given
        Actor rock = TestDataProviderService.getRock();
        ActorEntity rockEntity = TestDataProviderService.getRockEntity();
        when(actorRepository.save(rockEntity)).thenReturn(rockEntity);
        //when
        Actor actual = service.modify(rock);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(rock);
    }

    @Test
    void deleteHappyPath() throws ActorNotFoundException{
        //given
        Actor rock = TestDataProviderService.getRock();
        ActorEntity rockEntity = TestDataProviderService.getRockEntity();
        actorRepository.delete(rockEntity);
        //when
        service.delete(rock);
    }
}