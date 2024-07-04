package com.sw.fd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.fd.entity.Store;
import com.sw.fd.service.StoreService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class StoreDataLoader {

    @Autowired
    private StoreService storeService;

    private static final String URL = "https://www.daegufood.go.kr/kor/api/tasty.html?mode=json&addr=%EC%A4%91%EA%B5%AC";

    @PostConstruct
    public void init() throws Exception {
        // DB에 데이터가 있는지 확인
        if (storeService.getAllStores().isEmpty()) {
            List<JsonNode> dataList = fetchDataFromApi();
            for (JsonNode node : dataList) {
                Store store = new Store();
                store.setSno(node.get("cnt").asInt()); // cnt 대신 적절한 필드를 사용
                store.setSname(node.get("BZ_NM").asText());
                store.setSaddr(node.get("GNG_CS").asText());
                storeService.saveStore(store);
            }
        }
    }

    private List<JsonNode> fetchDataFromApi() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(URL);

        // Content-Type 및 Accept-Charset 헤더를 추가하여 UTF-8 인코딩을 지정
        request.setHeader("Content-Type", "application/json; charset=UTF-8");
        request.setHeader("Accept-Charset", "UTF-8");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // UTF-8 인코딩으로 응답을 변환
                String result = EntityUtils.toString(entity, "UTF-8");

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result);

                // API 응답을 디버그 출력
                System.out.println("API Response: " + result);

                // jsonNode 구조 출력
                String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
                System.out.println("JsonNode Structure: " + prettyJson);

                // "data" 노드가 존재하는지 확인하고 리스트로 변환
                JsonNode dataNode = jsonNode.get("data");
                if (dataNode != null && dataNode.isArray()) {
                    return objectMapper.convertValue(dataNode, new TypeReference<List<JsonNode>>() {});
                } else {
                    System.out.println("No data found in API response.");
                }
            }
        }
        return null;
    }
}
