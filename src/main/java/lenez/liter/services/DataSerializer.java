package lenez.liter.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer implements  IDataSerializer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T deserialize(String json, Class<T> model) {
        try {
            return objectMapper.readValue(json, model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
