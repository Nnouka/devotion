package com.nouks.devotion.unit.services;

import com.nouks.devotion.domain.dtos.requests.CreateCongregationDTO;
import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.repositories.CongregationRepository;
import com.nouks.devotion.domain.repositories.CongregationUserRepository;
import com.nouks.devotion.domain.services.CongregationServiceImpl;
import com.nouks.devotion.domain.services.interfaces.CongregationService;
import com.nouks.devotion.domain.services.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CongregationServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private CongregationRepository congregationRepository;
    @Mock
    private CongregationUserRepository congregationUserRepository;
    private CongregationService congregationService;
    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setFullName("minister");
        congregationService = new CongregationServiceImpl(congregationRepository);
        ((CongregationServiceImpl) congregationService).setUserService(userService);
        ((CongregationServiceImpl) congregationService).setCongregationUserRepository(congregationUserRepository);

    }
    @Test
    public void givenNewCongregation_thenCallSave(){
        when(userService.getCurrentAuthUser()).thenReturn(user);
        congregationService.createCongregation(new CreateCongregationDTO());
        verify(userService).getCurrentAuthUser();
        verify(congregationRepository).save(any());
        verify(congregationUserRepository).save(any());

    }

}
