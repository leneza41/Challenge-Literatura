package lenez.liter.services;

public interface IDataSerializer {
    <T> T deserialize(String json, Class<T> model);
}
