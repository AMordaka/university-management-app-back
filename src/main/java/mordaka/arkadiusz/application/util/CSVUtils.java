package mordaka.arkadiusz.application.util;

import com.opencsv.CSVReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;


public class CSVUtils {

    private CSVReader reader = null;

    public CSVUtils(MultipartFile file) {
        try {
            File csvFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(file.getBytes());
            fos.close();
            reader = new CSVReader(new FileReader(csvFile), ';');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public CSVReader getReader() {
        return reader;
    }
}
