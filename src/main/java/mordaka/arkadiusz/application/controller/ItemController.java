package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/student/{username}/items")
    public List<ItemProfile> getParticipatesItems(@PathVariable(value = "username") String username) {
        return itemService.getParticipatesItems(username);
    }

    @GetMapping("/teacher/{username}/items")
    public List<ItemProfile> getCarriedItems(@PathVariable(value = "username") String username) {
        return itemService.getCarriedItems(username);
    }
}
