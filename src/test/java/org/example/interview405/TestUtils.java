package org.example.interview405;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.Long;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtils {

    public static Long createNewCountry(MockMvc mockMvc) throws Exception {
        MvcResult result = mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {"name" :  "name1"
                  }
                  """))
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = JsonPath.read(
                result.getResponse().getContentAsString(),
                "$.id");
        return id.longValue();
    }

}
