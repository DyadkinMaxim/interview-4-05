package org.example.interview405;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.example.interview405.TestUtils.createNewCountry;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Interview405Application.class)
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CountryApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldFindAll() throws Exception {
        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name": "name1"}
                            """))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {"name": "name2"}
                            """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/countries")
                        .param("pageNumber", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].name", hasItems("name1", "name2")))
                .andExpect(jsonPath("$.totalElements").value(org.hamcrest.Matchers.greaterThanOrEqualTo(2)));
        }

    @Test
    void shouldFindById() throws Exception {
        Long id = createNewCountry(mockMvc);

        mockMvc.perform(get("/countries/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"));
    }

    @Test
    void shouldUpdate() throws Exception {
        Long id = createNewCountry(mockMvc);

        mockMvc.perform(put("/countries/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""

                                {"name" :  "Title1234"}
                  """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Title1234"));
    }

    @Test
    void shouldSaveCorrectly() throws Exception {
        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name" :  "name12"}
                  """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("name12"));
    }

    @Test
    void shouldDeleteCorrectly() throws Exception {
        Long id = createNewCountry(mockMvc);
        mockMvc.perform(delete("/countries/" + id))
                .andExpect(status().isNoContent());
    }

}

