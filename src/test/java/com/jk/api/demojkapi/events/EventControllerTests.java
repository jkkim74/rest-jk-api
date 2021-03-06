package com.jk.api.demojkapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mocMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {
                Event event = Event.builder()
                        .id(100)
                      .name("Spring")
                      .description("REST API Document With Spring")
                      .beginEnrollmentDateTime(LocalDateTime.of(2018,12,27,16,57))
                      .closeEnrollmentDateTime(LocalDateTime.of(2018,12,28,16,57))
                      .beginEventDateTime(LocalDateTime.of(2018,12,27,16,57))
                      .endEventDateTime(LocalDateTime.of(2018,12,28,16,57))
                      .basePrice(100)
                      .maxPrice(200)
                      .limitOfEnrollment(100)
                      .location("강남역 D2 스타텁 팩토리")
                        .free(true)
                        .offLine(false)
                        .build();
         // Mockito.when(eventRepository.save(event)).thenReturn(event);
         mocMvc.perform(post("/api/events/")
                              .contentType(MediaType.APPLICATION_JSON_UTF8)
                              .accept(MediaTypes.HAL_JSON)
                              .content(objectMapper.writeValueAsString(event)))
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("id").exists())
                 .andExpect(header().exists(HttpHeaders.LOCATION))
                 .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_UTF8_VALUE))
                 .andExpect(jsonPath("id").value(Matchers.not(100)))
                 .andExpect(jsonPath("free").value(Matchers.not(true)));
    }

}
