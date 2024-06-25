
import domain.Item;
import repository.ItemRepo;
import service.ItemService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
public class EntireController {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/info/{id}")
    public Object get(@PathVariable Long id) {
        if (id == 0) {          // home -> get all items
            List<Item> itemList = itemRepo.findAll();            // get all items
            return itemList;
        }
        else {
            Optional<Item> item = itemRepo.findById(id); //Find Data with ID
            Item itemEntity = item.get(); //Get
            return itemEntity;
        }
    }

    @CrossOrigin
    @PostMapping(path = "/upload", produces = "application/json; utf-8")
    public String upload(@RequestBody Item item){

        itemService.createItem(item);       //generate new item
        return "Success";
    }



    @CrossOrigin
    @PostMapping(path = "/modify/{id}", produces = "application/json; utf-8")
    public String modify(@RequestBody Item item, @PathVariable Long id){

        try {
            Optional<Item> updateBoard = itemRepo.findById(id); //Find Data with ID
            Item board1 = updateBoard.get(); //Get

            File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + board1.getImageTitle());
            file.delete();                  // delete image file

            board1.setContents(item.getContents()); //Set Board contents with Modify one

            board1.setTitle(item.getTitle()); //Set Board title with Modify one
            board1.setImageTitle(item.getImageTitle()); //Set Board title with Modify one
            itemRepo.save(board1); //Save Board

        } catch (Exception e){ //Exception Handling
            e.printStackTrace();
        }

        itemService.createItem(item);
        return "Success";
    }

    @CrossOrigin
    @PostMapping(path = "/delete", produces = "application/json; utf-8")
    public String delete(@RequestBody Item item){
        long id = item.getId();
        Optional<Item> board = itemRepo.findById(id); //Find Data with ID
        Item boardEntity = board.get(); //Get

        File file = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + boardEntity.getImageTitle());
        file.delete();                  // delete image file
        itemService.deleteItem(id); //Delete Data with ID
        return "Success";
    }


    @PostMapping(path="/uploadImage", produces = "text/plain;charset=UTF-8")
    public String uploadimgtoserver(@RequestPart MultipartFile picture,HttpServletRequest req) throws IllegalStateException, IOException {

        String originalFileName = picture.getOriginalFilename();       // get file name
        String path = System.getProperty("user.dir") + "/src/main/resources/static/" + originalFileName; // path

        File newFile = new File(path);
        picture.transferTo(newFile);       // transmission of file data

        return "success";
    }



}
