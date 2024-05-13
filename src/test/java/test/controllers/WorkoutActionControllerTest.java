package test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import trainingDiary.com.dto.WorkoutDto;
import trainingDiary.com.exceptions.NotUniqueWorkoutException;
import trainingDiary.com.in.controllers.user_action.WorkoutActionController;
import trainingDiary.com.service.UserActionService;
import trainingDiary.com.service.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("WorkoutActionController tests")
@ExtendWith(MockitoExtension.class)
class WorkoutActionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private WorkoutService workoutService;

    @Mock
    private UserActionService userService;

    @InjectMocks
    private WorkoutActionController workoutActionController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(workoutActionController).build();
    }

    @DisplayName("Get all users workouts")
    @Test
    public void getAllUsersWorkoutsTest() throws Exception {
        ArrayList<WorkoutDto> expectedWorkouts = new ArrayList<>();
        when(userService.showAllWorkoutsDateSorted(anyInt())).thenReturn(expectedWorkouts);

        mockMvc.perform(get("/users/{userId}/workouts", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).showAllWorkoutsDateSorted(Mockito.anyInt());
    }

    @DisplayName("Delete workout")
    @Test
    public void deleteWorkoutTest() throws Exception {
        int userId = 1;
        int workoutId = 1;

        mockMvc.perform(delete("/users/{userId}/workouts/{workoutId}", userId, workoutId))
                .andExpect(status().isOk());

        verify(workoutService, times(1)).deleteWorkout(Mockito.anyInt(), Mockito.anyInt());
    }

    @DisplayName("Save workout")
    @Test
    public void saveWorkoutTest() throws NotUniqueWorkoutException, Exception {
        WorkoutDto build = WorkoutDto.builder()
                .additionalInfo("additionalInfo")
                .caloriesBurned(100d)
                .build();

        mockMvc.perform(post("/users/{userId}/workouts", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(build)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).addNewWorkout(Mockito.anyInt(), Mockito.any(WorkoutDto.class));
    }
}
