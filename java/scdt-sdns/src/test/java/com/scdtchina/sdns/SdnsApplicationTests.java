package com.scdtchina.sdns;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scdtchina.sdns.repository.LRUUrlPairsRepository;
import com.scdtchina.sdns.vo.FindResponse;
import com.scdtchina.sdns.vo.JsonResult;
import com.scdtchina.sdns.vo.SaveResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@ActiveProfiles("junit")
class SdnsApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(SdnsApplicationTests.class);

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private LRUUrlPairsRepository lruUrlPairsRepository;

    @BeforeEach
    public void beforeEach() {
        LOG.info("beforeEach");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //简单测试一下存储一个长url
    @Test
    @Order(1)
    public void testSimpleSave() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", "/address1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        LOG.info("status:" + status + ",content:" + content);
        JsonResult<SaveResponse> saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        Assertions.assertEquals(saveResponse.getRc(), "0000");
        Assertions.assertEquals(saveResponse.getBody().getShortUrl().length(), 8);
    }

    //简单测试一下查询一个短url，直接查询无法查到的
    @Test
    @Order(2)
    public void testSimpleFindNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", "qqqqqqqA")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        LOG.info("status:" + status + ",content:" + content);
        JsonResult<FindResponse> findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0404");
    }

    //简单测试一下查询一个短url，先保存一个，再查询
    @Test
    @Order(3)
    public void testSimpleFindAfterSave() throws Exception {
        //save
        RequestBuilder saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", "/address1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        MvcResult mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        LOG.info("status:" + status + ",content:" + content);
        JsonResult<SaveResponse> saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        Assertions.assertEquals(saveResponse.getRc(), "0000");
        Assertions.assertEquals(saveResponse.getBody().getShortUrl().length(), 8);
        //存储短url，后面比对
        String shortUrl = saveResponse.getBody().getShortUrl();
        //find
        RequestBuilder findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        status = mvcResult.getResponse().getStatus();
        content = mvcResult.getResponse().getContentAsString();
        LOG.info("status:" + status + ",content:" + content);
        JsonResult<FindResponse> findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0000");
        Assertions.assertEquals(findResponse.getBody().getNormalUrl(), "/address1");
    }

    //测试url对的容量，junit测试配置文件配置的是3对，存第四个地址的时候，第一个应该找不到了
    @Test
    @Order(4)
    public void testRepositoryCapacity() throws Exception {

        String normalUrl1 = "/address/test1";
        String normalUrl2 = "/address/test2";
        String normalUrl3 = "/address/test3";
        String normalUrl4 = "/address/test4";
        String shortUrl1, shortUrl2, shortUrl3, shortUrl4;

        RequestBuilder saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        MvcResult mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JsonResult<SaveResponse> saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl1 = saveResponse.getBody().getShortUrl();

        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl2)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl2 = saveResponse.getBody().getShortUrl();

        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl3)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl3 = saveResponse.getBody().getShortUrl();

        //再保存第四个
        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl4)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl4 = saveResponse.getBody().getShortUrl();

        //保存第四个地址之后，第一个地址应该找不到了
        RequestBuilder findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JsonResult<FindResponse> findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0404");

        //但是第二个应该还有
        findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl2)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0000");
        Assertions.assertEquals(findResponse.getBody().getNormalUrl(), normalUrl2);

    }

    //测试LRU是否有效
    @Test
    @Order(5)
    public void testRepositoryLRU() throws Exception {

        String normalUrl1 = "/address/test1";
        String normalUrl2 = "/address/test2";
        String normalUrl3 = "/address/test3";
        String normalUrl4 = "/address/test4";
        String shortUrl1, shortUrl2, shortUrl3, shortUrl4;

        RequestBuilder saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        MvcResult mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JsonResult<SaveResponse> saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl1 = saveResponse.getBody().getShortUrl();

        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl2)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl2 = saveResponse.getBody().getShortUrl();

        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl3)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl3 = saveResponse.getBody().getShortUrl();

        //先访问一下第一个
        RequestBuilder findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        JsonResult<FindResponse> findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0000");
        Assertions.assertEquals(findResponse.getBody().getNormalUrl(), normalUrl1);

        //再保存第四个
        saveRequest = MockMvcRequestBuilders.post("/save")
                .param("normalUrl", normalUrl4)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(saveRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        saveResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<SaveResponse>>() {
        });
        shortUrl4 = saveResponse.getBody().getShortUrl();

        //保存第四个地址之后，第一个地址应该还找得到
        findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0000");
        Assertions.assertEquals(findResponse.getBody().getNormalUrl(), normalUrl1);

        //但是第二个应该找不到了
        findRequest = MockMvcRequestBuilders.post("/find")
                .param("shortUrl", shortUrl2)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED);
        mvcResult = mockMvc.perform(findRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        findResponse = om.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<JsonResult<FindResponse>>() {
        });
        Assertions.assertEquals(findResponse.getRc(), "0404");

    }


}
