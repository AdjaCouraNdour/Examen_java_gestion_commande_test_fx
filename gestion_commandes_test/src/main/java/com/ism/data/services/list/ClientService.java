package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Client;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.services.interfaces.ClientServiceI;

public class ClientService implements ClientServiceI{

    ClientRepositoryI repo;
    
    public ClientService(ClientRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public Client getById(int id) {
        return
        repo.selectById(id); 
    }

    @Override
    public Client getByNumero(String numero) {
        return
        repo.selectByNumero(numero);
    }

    @Override
    public boolean save(Client object) {
        return repo.insert(object);
    }

    @Override
    public List<Client> show() {
        return repo.selectAll();
    }

    @Override
    public void effacer(Client object) {
        repo.remove(object);
    }

    @Override
    public boolean update(Client object) {
        return repo.update(object);

    }

    public Client getConnectedClient(int userId) {
        return repo.selectConnectedClient(userId);
      }
}
  