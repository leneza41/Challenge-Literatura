package lenez.liter.service;

public interface IDataSerializer {
    <T> T deserialize(String json, Class<T> model);
}
