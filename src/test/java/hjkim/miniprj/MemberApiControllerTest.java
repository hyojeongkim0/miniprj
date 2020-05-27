package hjkim.miniprj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // blank = default
@AutoConfigureMockMvc
public class MemberApiControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform(get("/customer/{id}", 1).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print()) ;
    }

    @Test
    public void testPostCustomer() throws Exception {

         String json = "{\n" +
                            "\"name\" : \"TEST2\",\n"+
                            "\"address\" : { \"city\" : \"서울\", \n"+
                                        "\"street\" : \"2길\", \n"+
                                        "\"zipcode\" : \"07221\" \n"+
                                        "}, \n"+
                            "\"lineCount\" : 2 \n" +
                        "}\n";


         mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(json))
				.andDo(print())
                .andExpect(status().isOk());
     
    }
    
}
