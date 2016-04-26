package filmr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceClass<T, ID extends Serializable> {

    @Autowired
    JpaRepository<T, ID> repository;

    public T saveEntity(T entity){
        return repository.saveAndFlush(entity);
    }

    public T readEntity(ID id){
        return repository.getOne(id);
    }

    public List<T> readAllEntities(){
        return repository.findAll();
    }

    public void deleteEntity(ID id){
        repository.delete(id);
    }

}
