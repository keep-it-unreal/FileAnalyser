package fileManager;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
class FileController {

    @PostMapping("/imports")
    String importNewFile(@RequestBody String path) throws IOException {
        String s = File.separator;
        String file = "";
        String[] pathWithoutSep = path.split("/");
        for (String e:pathWithoutSep) {
            file += e + s;
        }
        file = file.substring(0, file.length() - 1);
        FileAnalyser fileAnalyser = new FileAnalyserImpl(file);
        fileAnalyser.saveSummary("summary.txt");
        return Files.readString(Path.of("summary.txt"));
    }

    @GetMapping("/getSummary")
    String getSummary(@RequestBody String path) throws IOException {
        File original = new File("summary.txt");
        File copied = new File(path);
        FileUtils.copyFile(original, copied);
        return "Summary was saved";
    }
}