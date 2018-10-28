package mordaka.arkadiusz.application.controller;

import mordaka.arkadiusz.application.service.ItemService;
import mordaka.arkadiusz.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/file")
public class FileController {

    private final UserService userService;
    private final ItemService itemService;

    public FileController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @RequestMapping(value = "/addavatar", method = RequestMethod.POST)
    public ResponseEntity<?> addAvatar(Principal principal, @RequestPart(name = "file", required = false) MultipartFile file) {
        return userService.addAvatar(principal.getName(), file);
    }

    @GetMapping("/getavatar")
    public ResponseEntity<?> getAvatar(Principal principal) {
        return userService.getAvatar(principal.getName());
    }

    @RequestMapping(value = "/addPdf/{coursename}", method = RequestMethod.POST)
    public ResponseEntity<?> addPdf(Principal principal, @PathVariable(value = "coursename") String courseName, @RequestPart(name = "file", required = false) MultipartFile file) {
        return itemService.addPdf(principal.getName(), courseName, file);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPdfs() {
        return itemService.getAllPdfs();
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<?> getPdf(@PathVariable(value = "id") Long id) {
        return itemService.getPdf(id);
    }

    @DeleteMapping("pdf/{id}")
    public ResponseEntity<?> deletePdf(@PathVariable(value = "id") Long id) {
        return itemService.deletePdf(id);
    }
}

