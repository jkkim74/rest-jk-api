package com.jk.api.demojkapi.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder(){
        Event event = Event.builder()
                .name("Inflean Spring Rest API")
                .description("REST API development with Spring").build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){
        Event event = new Event();
        String name = "Event";
        event.setName(name);
        String spring = "Spring";
        event.setDescription(spring);
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(spring);

    }

}