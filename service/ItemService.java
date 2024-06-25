package service;

import domain.Item;
import repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService { //Service Layer
    @Autowired
    private ItemRepo itemRepo;

    public Item createItem(Item item){ //Create Board Table with Object Board
        return itemRepo.save(item);
    }

    public void deleteItem(Long id){ //Delete Data from Board Table with ID
        itemRepo.deleteById(id);
    }
}