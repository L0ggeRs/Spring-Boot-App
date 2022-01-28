package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.*;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorManager actorManager;

    @Mock
    private ActorMapper actorMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private ActorController controller;

    @Test
    void readAllHappyPath() {
        //given
        when(actorManager.readAll()).thenReturn(List.of(TestDataProviderController.getTestActor()));
        when(actorMapper.actor2ActorDto(any())).thenReturn(TestDataProviderController.getTestActorDto());
        Collection<ActorDto> expected = List.of(TestDataProviderController.getTestActorDto());
        //when
        Collection<ActorDto> actual = controller.readAllActors();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdHappyPath() throws ActorNotFoundException {
        //given
        when(actorManager.readById(TestDataProviderController.ACTOR_ID)).thenReturn(TestDataProviderController.getTestActor());
        Actor expected = TestDataProviderController.getTestActor();
        //when
        Actor actual = controller.readById(TestDataProviderController.ACTOR_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readRoleHappyPath() throws ActorNotFoundException {
        //given
        when(actorManager.readRole(TestDataProviderController.ACTOR_ID)).thenReturn(List.of(TestDataProviderController.getTestRole()));
        when(roleMapper.role2RoleDto(any())).thenReturn(TestDataProviderController.getTestRoleDto());
        List<RoleDto> expected = List.of(TestDataProviderController.getTestRoleDto());
        //when
        List<RoleDto> actual = controller.readRole(TestDataProviderController.ACTOR_ID);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIdWhenActorNotFound() throws ActorNotFoundException {
        //given
        final int notFoundActorId = TestDataProviderController.ACTOR_ID;
        doThrow(new ActorNotFoundException()).when(actorManager).readById(notFoundActorId);
        //when then
        assertThatThrownBy(() -> controller.readById(notFoundActorId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void readRoleWhenActorNotFound() throws ActorNotFoundException {
        //given
        final int notFoundActorId = TestDataProviderController.ACTOR_ID;
        doThrow(new ActorNotFoundException()).when(actorManager).readRole(notFoundActorId);
        //when then
        assertThatThrownBy(() -> controller.readRole(notFoundActorId)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void createActorHappyPath() throws ActorAlreadyExistsException {
        //given
        Actor test = TestDataProviderController.getTestActor();
        ActorDto testDto = TestDataProviderController.getTestActorDto();
        when(actorMapper.actorDto2Actor(testDto)).thenReturn(test);
        when(actorManager.record(test)).thenReturn(test);
        when(actorMapper.actor2ActorDto(test)).thenReturn(testDto);
        //when
        ActorDto actual = controller.create(testDto);
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createActorThrowsActorAlreadyExistsException() throws ActorAlreadyExistsException {
        //given
        Actor test = TestDataProviderController.getTestActor();
        ActorDto testDto = TestDataProviderController.getTestActorDto();
        when(actorMapper.actorDto2Actor(testDto)).thenReturn(test);
        when(actorManager.record(test)).thenThrow(new ActorAlreadyExistsException());
        //when then
        assertThatThrownBy(() -> controller.create(testDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        //given
        ActorDto requestDto = TestDataProviderController.getTestActorDto();
        Actor test = TestDataProviderController.getTestActor();
        when(actorMapper.actorDto2Actor(requestDto)).thenReturn(test);
        when(actorManager.modify(test)).thenReturn(test);
        when(actorMapper.actor2ActorDto(test)).thenReturn(requestDto);
        ActorDto expected = TestDataProviderController.getTestActorDto();
        //when
        ActorDto response = controller.update(requestDto);
        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws ActorNotFoundException {
        //given
        Actor test = TestDataProviderController.getTestActor();
        when(actorManager.readById(TestDataProviderController.ACTOR_ID)).thenReturn(test);
        doNothing().when(actorManager).delete(test);
        //when
        controller.delete(TestDataProviderController.ACTOR_ID);
    }

    @Test
    void deleteFromQueryParamWhenActorNotFound() throws ActorNotFoundException {
        //given
        final int notFoundActorId = TestDataProviderController.ACTOR_ID;
        doThrow(new ActorNotFoundException()).when(actorManager).readById(notFoundActorId);
        //when then
        assertThatThrownBy(() -> controller.delete(notFoundActorId)).isInstanceOf(ResponseStatusException.class);
    }
}