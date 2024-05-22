package trainingDiary.com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import trainingDiary.com.aspect.UserRoleCheck;
import trainingDiary.com.config.TestBeans;
import trainingDiary.com.dto.CurrentUser;
import trainingDiary.com.dto.WorkoutDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("WorkoutActionController tests")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutActionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRoleCheck roleCheck;

    @MockBean
    private CurrentUser currentUser;

    @DisplayName("Get all users workouts")
    @Test
    public void getAllUsersWorkoutsTest() throws Exception {
        when(currentUser.getId()).thenReturn(1);

        mockMvc.perform(get("/users/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Sql(value = "classpath:/data/workouts/deleteAll.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/data/workouts/insertData.sql", executionPhase = BEFORE_TEST_METHOD)
    @DisplayName("Delete workout")
    @Test
    public void deleteWorkoutTest() throws Exception {
        int workoutId = 1;
        when(currentUser.getId()).thenReturn(1);
        mockMvc.perform(delete("/users/workouts/{workoutId}", workoutId))
                .andExpect(status().isOk());
    }

    @Sql(value = "classpath:/data/workouts/deleteAll.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:/data/workouts/insertData.sql", executionPhase = BEFORE_TEST_METHOD)
    @DisplayName("Save workout")
    @Test
    public void saveWorkoutTest() throws Exception {
        WorkoutDto build = WorkoutDto.builder()
                .additionalInfo("additionalInfo")
                .caloriesBurned(100d)
                .build();
        when(currentUser.getId()).thenReturn(1);

        mockMvc.perform(post("/users/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(build)))
                .andExpect(status().isOk());
    }
}
