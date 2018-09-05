package mordaka.arkadiusz.application.service;

import mordaka.arkadiusz.application.payload.ItemProfile;

import java.util.List;

public interface ItemService {

    List<ItemProfile> getParticipatesItems(String username);

    List<ItemProfile> getCarriedItems(String username);
}
