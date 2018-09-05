package mordaka.arkadiusz.application.service.impl;

import mordaka.arkadiusz.application.model.Item;
import mordaka.arkadiusz.application.model.User;
import mordaka.arkadiusz.application.payload.ItemProfile;
import mordaka.arkadiusz.application.service.ItemService;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final UserService userService;

    public ItemServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<ItemProfile> getParticipatesItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getStudent().getItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getTeacher().getUser().getName() + " " + item.getTeacher().getUser().getSurname(), item.getTeacher().getUser().getId()));
        }
        return itemProfiles;
    }

    @Override
    public List<ItemProfile> getCarriedItems(String username) {
        User user = userService.findUser(username);
        List<ItemProfile> itemProfiles = new ArrayList<>();
        for (Item item : user.getTeacher().getCarriedItems()) {
            itemProfiles.add(new ItemProfile(item.getId(), item.getSubjectName(), item.getGrade(), item.getStudent().getUser().getName() + " " + item.getStudent().getUser().getSurname(), item.getStudent().getUser().getId()));
        }
        return itemProfiles;
    }
}
